package library.models;

import library.enums.BookStateEnum;
import library.users.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "book_states")
public class BookState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(targetEntity = User.class)
    private User user;

    private LocalDate dateOfLoan;

    @NotNull
    private LocalDate dateOfCreating;

    private LocalDate dateOfUpdating;

    private LocalDate dateOfReturn;

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
