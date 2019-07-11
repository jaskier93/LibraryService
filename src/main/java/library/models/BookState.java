package library.models;

import library.enums.BookStateEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "book_states")
public class BookState extends StateEntity {

    //TODO:    @Transient znaleźć adnotację, dzięki której te pole nie będziew bazie danej, transient wypełnia tylko nullami
    private final static Integer LOAN_PERIOD = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne(targetEntity = User.class)
    private User libranian;

    private LocalDate dateOfLoan = LocalDate.now();

    private LocalDate dateOfReturn = dateOfReturnBook();

    @ManyToOne(targetEntity = Book.class)
    private Book book;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BookStateEnum bookStateEnum;

    @ManyToOne
    private Action action;

    @NotNull
    private Integer status;

    private LocalDate dateOfReturnBook() {
        return LocalDate.now().plusDays(LOAN_PERIOD);
    }
}
