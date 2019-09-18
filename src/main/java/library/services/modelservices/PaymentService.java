package library.services.modelservices;

import library.enums.StatusRekordu;
import library.models.BookState;
import library.models.Payment;
import library.repositories.PaymentRepository;
import library.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Slf4j
@Service
public class PaymentService {

    private static final Integer PENALTY_AMOUNT = 20;     //kara 20zł za zniszczenie książki
    private static final Integer DAILY_AMOUNT = 1; //dzienna stawka opłaty za przetrzymanie książki
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment destroyedBookPayment(BookState bookState) {
        Payment payment = createPayment(bookState, PENALTY_AMOUNT);
        return paymentRepository.save(payment);
    }

    public Payment expiredLoan(BookState bookState) {
        Payment payment = createPayment(bookState, PENALTY_AMOUNT);

        //TODO: ewentualnie do poprawy
        Period period = Period.between(payment.getBookState().getUpdated().toLocalDate(), LocalDate.now());
        Integer daysAfterReturnDate = period.getDays();

        payment.setAmount(daysAfterReturnDate * DAILY_AMOUNT);
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Integer paymentId) {
        Payment payment = paymentRepository.getOne(paymentId);
        payment.setUpdated(LocalDateTime.now());
        payment.setActive(false); // płatność opłacona
        return paymentRepository.save(payment);
    }

    public Integer sumActivePaymentsForOneUser(User user) {
        Integer sumActivePaymentsForOneUser = paymentRepository.sumActivePaymentsForOneUser(user);
        if (sumActivePaymentsForOneUser == null) {
            sumActivePaymentsForOneUser = 0;
        }
        return sumActivePaymentsForOneUser;
    }

    public Integer sumPaymentsForOneUser(User user) {
        Integer sumPaymentsForOneUser = paymentRepository.sumPaymentsForOneUser(user);
        if (sumPaymentsForOneUser == null) {
            sumPaymentsForOneUser = 0;
        }
        return sumPaymentsForOneUser;
    }

    private Payment createPayment(BookState bookState, Integer amount){
        Payment payment = new Payment();
        payment.setCreated(LocalDateTime.now());
        payment.setUpdated(LocalDateTime.now());
        payment.setStatusRekordu(StatusRekordu.ACTIVE);
        payment.setAction(bookState.getAction());
        payment.setBookState(bookState);
        payment.setStatus(0);
        payment.setActive(true);
        payment.setBook(bookState.getBook());
        payment.setAmount(amount);

        return payment;
    }
}
