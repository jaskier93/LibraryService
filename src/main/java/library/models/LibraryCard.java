package library.models;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LibraryCard{
    private Integer loanedBooks;
    private Integer destroyedBooks;
    private Integer unpaidPaymentsQuantity;
    private Integer unpaidPaymentsSum;
    private Integer allPaymentsQuantity;
    private Integer allPaymentsSum;
    private Integer expiredReturnsOfBooks;
}
