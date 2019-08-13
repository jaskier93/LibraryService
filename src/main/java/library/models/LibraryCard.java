package library.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
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
}
