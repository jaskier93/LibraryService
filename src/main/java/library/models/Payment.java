package library.models;

import library.users.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
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

    @NotNull
    private Integer amount;

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
