package library.validators;

import library.models.BookState;
import library.models.Payment;
import library.repositories.PaymentRepository;
import library.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class ReturnBookVaildator {

    private PaymentRepository paymentRepository;

    @Autowired
    public ReturnBookVaildator(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    //mnożnik dziennej opłaty (kary) za przetrzymanie książki, obecnie =1zł
    private final Integer DAILY_MULTIPLIER = 1;

    /* nazwa do zmiany, zmienna odpowiada za wartość kary pieniężnej,
      po której np. użytownik nie może wypożyczyć następnej książki */
    private final Integer SOME_PENALTY_AMOUNT = 10;

    public void returningBookVaildator(BookState bookState) {
        //warunek sprawdza, czy między datą zwrotu książki, a datą wypożyczenia minęło 30 dni
        Long days = Duration.between(bookState.getDateOfReturn(), bookState.getDateOfLoan()).toDays();

        if (days > 30) {
            Payment payment = new Payment();

            /*konwersja longa (Duration zwraca w longu) na Integer
              (zgodny ze zmienną amount z klasy Payment)  */
            Integer days2 = days.intValue();

            /*naliczanie kary za każdy dzień przetrzymania książki
              za każdy dzień powyżej 30 dni, naliczana jest złotówka kary
              payment.set... oraz new Payment(); będą przeniesione do serwisu*/
            payment.setAmount((days2 - 30) * DAILY_MULTIPLIER);
            paymentRepository.save(payment);
        }
    }
    /*metoda sprawdzająca, czy dana płatność jest powyżej ustalonej kwoty
      jeśli użytkownik wygeneruje płatność np. powyżej 100zł, nie płaci przez długi okres kary,
      należy sprawę zgłosić odpowiednim organom, a konto zawiesić   */
    public boolean isPaymentAboveAmount(Payment payment) {
        return (payment.getAmount() >= SOME_PENALTY_AMOUNT);
    }


}
