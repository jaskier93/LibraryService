package library.models;

import library.users.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "payments")
@EqualsAndHashCode(callSuper = false)
//@RequiredArgsConstructor
public class Payment extends StateEntity {

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

    @NotNull
    private Integer status;
}
