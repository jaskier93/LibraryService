package library.validators;

import library.models.BookState;
import library.models.Payment;

import java.time.Duration;

public class ReturnBookVaildator {
    //mnożnik dziennej opłaty (kary) za przetrzymanie książki, obecnie =1zł
    final Integer DAILY_MULTIPLIER = 1;

    //nazwa do zmiany, zmienna odpowiada za wartość kary pieniężnej, po której np. użytownik nie może wypożyczyć następnej książki
    final Integer SOME_PENALTY_AMOUNT = 10;

    public void returningBookVaildator(BookState bookState) {


        //warunek sprawdza, czy między datą zwrotu książki, a datą wypożyczenia minęło 30 dni
        Long days = Duration.between(bookState.getDateOfReturn(), bookState.getDateOfLoan()).toDays();

        if (days > 30) {
            Payment payment = new Payment();

            //konwersja longa (Duration zwraca w longu) na Integer
            // (zgodny ze zmienną amount z klasy Payment)
            Integer days2 = days.intValue();

            //naliczanie kary za każdy dzień przetrzymania książki
            //za każdy dzień powyżej 30 dni, naliczana jest złotówka kary
            payment.setAmount((days2 - 30) * DAILY_MULTIPLIER);

        }
    }

    public boolean isPaymentUnderTen(Payment payment) {
        ;
        if (payment.getAmount() >= SOME_PENALTY_AMOUNT) {
            return false;
        }
        return true;

    }


}
