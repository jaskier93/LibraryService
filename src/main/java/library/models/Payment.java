package library.models;

import library.users.Person;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne
    private Person user;

    @NotNull
    @OneToOne
    private Book book;

    private @NotNull Integer amount;

    @NotNull
    @ManyToOne
    private BookState bookState;

    @NotNull
    private boolean isActive;

    @ManyToOne
    private Action action;

    @NotNull
    private Integer status;

}
