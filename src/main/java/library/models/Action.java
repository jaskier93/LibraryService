package library.models;

import library.enums.ActionDescription;
import library.users.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "Actions")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     *po stronie kontrolera/htmla można dodać listę podpowiedzi typu: zniszczenie, nowość, wypożyczenie, zwrot
     * i możliwość dodadania po prostu własnego opisu
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private ActionDescription actionDescription;

    @ManyToOne
    private Book book;

    @ManyToOne
    private User user;
}