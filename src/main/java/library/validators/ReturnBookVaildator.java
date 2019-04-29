package library.validators;

import library.models.BookState;
import library.models.Payment;

import java.time.Duration;

public class ReturnBookVaildator {

    public void returningBookVaildator(BookState bookState) {
        //warunek sprawdza, czy między datą zwrotu książki, a datą wypożyczenia minęło 30 dni
        Long days=Duration.between(bookState.getDateOfReturn(), bookState.getDateOfLoan()).toDays();

        if ( days> 30) {
            Payment payment=new Payment();

            //konwersja longa (Duration zwraca w longu) na Integer
            // (zgodny ze zmienną amount z klasy Payment)
            Integer days2=days.intValue();

            //naliczanie kary za każdy dzień przetrzymania książki
            //za każdy dzień powyżej 30 dni, naliczana jest złotówka kary
            payment.setAmount((days2-30)*1);

        }
    }
}
