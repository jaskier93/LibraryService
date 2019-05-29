package library.models;

import library.enums.BookStateEnum;
import library.enums.StatusRekordu;
import library.users.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

//@NoArgsConstructor
//@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "book_states")
public class BookState extends StateEntity{

    //TODO:    @Transient znaleźć adnotację, dzięki której te pole nie będziew bazie danej, transient wypełnia tylko nullami
    private final static Integer LOAN_PERIOD = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(targetEntity = User.class)
    private User user;

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

    public BookState(@NotNull LocalDateTime created, LocalDateTime updated, @NotNull StatusRekordu statusRekordu) {
        super(created, updated, statusRekordu);
    }

    private LocalDate dateOfReturnBook() {
        return LocalDate.now().plusDays(LOAN_PERIOD);
    }
}
