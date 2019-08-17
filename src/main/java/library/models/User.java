package library.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import library.models.StateEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends StateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(unique = true)
    private String login;

    //dodać walidator/interfejs do sprawdzania złożoności hasła, które powinno np zaczynać się z wielkiej litery i zawierać cyfrę/znak specjalny
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
