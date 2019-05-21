package library.validators;

import library.models.Author;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class IsDeathDateCorrect {

    /**
     * ewentualnie przy założeniu, że żaden autor książki nie umarł poniżej np. 18 roku życia, wstawić jako
     * warunek różnicę między datami, który musi wynosić powyżej 18 lat
     * drugi warunek ma sprawdzać, czy nie jest wpisana data z przyszłości
     */
    public boolean validator(Author author) {
        return (author.getDateOfDeath().isAfter(author.getDateOfBirth())
                && author.getDateOfDeath().isBefore(LocalDate.now().plusDays(1)));
    }
}
