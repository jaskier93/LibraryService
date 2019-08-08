package library.models;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LibraryCard {
    private Integer loanedBooks;
    private Integer destroyedBooks;
    private Integer unpaidPaymentsQuantity;
    private Integer unpaidPaymentsSum;
    private Integer allPaymentsQuantity;
    private Integer allPaymentsSum;
    private Integer expiredReturnsOfBooks;
    private Integer sumAllBooksPages;
    private Book latestLoanedBook;
    //TODO: łączna ilość wypożyczeń, suma zwrotów po terminie, ulubiona kategoria książek, ulubiony autor etc
}
