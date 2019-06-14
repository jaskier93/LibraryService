package library.services.modelservices;

import library.enums.StatusRekordu;
import library.models.Action;
import library.models.Book;
import library.models.BookState;
import library.models.Payment;
import library.repositories.PaymentRepository;
import library.services.exceptions.ExceptionEmptyList;
import library.users.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private static final Integer PENALTY_AMOUNT = 20;     //kara 20zł za zniszczenie książki
    private static final Integer DAILY_AMOUNT = 1; //dzienna stawka opłaty za przetrzymanie książki
    private final PaymentRepository paymentRepository;

    public Payment destroyedBookPayment(BookState bookState) {
        Payment payment = new Payment();
        payment.setCreated(LocalDateTime.now());
        payment.setUpdated(LocalDateTime.now());
        payment.setStatusRekordu(StatusRekordu.ACTIVE);
        payment.setAction(bookState.getAction());
        payment.setBookState(bookState);
        payment.setAmount(PENALTY_AMOUNT);
        payment.setStatus(0);
        payment.setActive(true);
        payment.setBook(bookState.getBook());
        return paymentRepository.save(payment);
    }

    public Payment expiredLoan(BookState bookState) {
        Payment payment = new Payment();
        payment.setCreated(LocalDateTime.now());
        payment.setUpdated(LocalDateTime.now());
        payment.setStatusRekordu(StatusRekordu.ACTIVE);
        payment.setAction(bookState.getAction());
        payment.setBookState(bookState);
        payment.setStatus(0);
        payment.setActive(true);
        payment.setBook(bookState.getBook());

        //TODO: ewentualnie do poprawy
        Period period = Period.between(payment.getBookState().getUpdated().toLocalDate(), LocalDate.now());
        Integer daysAfterReturnDate = period.getDays();
        payment.setAmount(daysAfterReturnDate * DAILY_AMOUNT);
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Integer paymentId) {
        Payment payment = paymentRepository.getOne(paymentId);
        payment.setUpdated(LocalDateTime.now());
        payment.setActive(false); // =płatność opłacona
        return paymentRepository.save(payment);
    }

    public Integer sumActivePaymentsForOneUser(User user) {
        Integer sumActivePaymentsForOneUser = paymentRepository.sumActivePaymentsForOneUser(user);
        if (sumActivePaymentsForOneUser == null) {
            sumActivePaymentsForOneUser = 0;
            //ewentualnie można rzucić jakiegoś loga lub NullPointerException, ale czy jest sens?
        }
        return sumActivePaymentsForOneUser;
    }

    public Integer sumPaymentsForOneUser(User user) {
        Integer sumPaymentsForOneUser = paymentRepository.sumPaymentsForOneUser(user);
        if (sumPaymentsForOneUser == null) {
            sumPaymentsForOneUser = 0;
            //ewentualnie można rzucić jakiegoś loga lub NullPointerException, ale czy jest sens?
        }
        return sumPaymentsForOneUser;
    }
}
