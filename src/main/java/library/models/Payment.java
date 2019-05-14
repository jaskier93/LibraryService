package library.models;

import library.users.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "payments")
//@RequiredArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @OneToOne
    private Book book;

    @NotNull
    private Integer amount;

    @NotNull
    @ManyToOne
    private BookState bookState;

    //zmienna pokazująca czy zmienna jest już zapłacona (false) lub czy nadal trzeba ją opłacić=true
    @NotNull
    private boolean isActive;

    @ManyToOne
    private Action action;

    //data nadania lub opłacenia płatności
    @NotNull
    private LocalDate dateOfPayment;

    @NotNull
    private Integer status;

 /*   @Builder
    public Payment(@NotNull User user, @NotNull Book book, @NotNull Integer amount, @NotNull BookState bookState,
                   @NotNull boolean isActive, Action action, @NotNull LocalDate dateOfPayment, @NotNull Integer status) {
        this.user = user;
        this.book = book;
        this.amount = amount;
        this.bookState = bookState;
        this.isActive = isActive;
        this.action = action;
        this.dateOfPayment = dateOfPayment;
        this.status = status;
    }*/
}
