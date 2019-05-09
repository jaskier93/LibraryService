package library.users;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import lombok.Data;

import javax.persistence.*;


@Entity
@Table
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String name;

    private String secondName;

    @NotNull
    private String lastName;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private LocalDate dateOfRegistration;

    private Integer adminDegree;

    @NotNull
    private boolean isActive;

    @NotNull
    private boolean isAdmin;
}
