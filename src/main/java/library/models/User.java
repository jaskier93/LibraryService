package library.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends StateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(unique = true)
    private String login;

    //TODO: dodać walidator/interfejs do sprawdzania złożoności hasła, które powinno np zaczynać się z wielkiej litery i zawierać cyfrę/znak specjalny
    @NotNull
    private String password;

    @NotNull
    private String name;

    private String secondName;

    @NotNull
    private String lastName;

    @NotNull
    private LocalDate dateOfBirth;

    private Integer adminDegree;

    @NotNull
    private boolean isActive;

    @Email
    @NotNull
    private String email;

    @NotNull
    private boolean isAdmin;
}
