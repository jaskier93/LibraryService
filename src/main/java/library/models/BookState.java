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

    private final static Integer LOAN_PERIOD = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne(targetEntity = User.class)
    private User libranian;

    private LocalDate dateFrom = LocalDate.now();

    private LocalDate dateTo = LocalDate.MAX;

    @ManyToOne(targetEntity = Book.class)
    private Book book;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BookStateEnum bookStateEnum;

    @ManyToOne
    private Action action;

    @NotNull
    private Integer status;
}
