package library.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "payments")
@EqualsAndHashCode(callSuper = false)
public class Payment extends StateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @OneToOne
    private Book book;

    @NotNull
    private Integer amount;

    @NotNull
    @ManyToOne
    private BookState bookState;

    @NotNull
    private boolean isActive;     //zapłacone (false) / do opłacenia(true)

    @ManyToOne
    private Action action;

    @NotNull
    private Integer status;
}
