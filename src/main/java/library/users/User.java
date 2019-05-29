package library.users;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

import library.enums.StatusRekordu;
import library.models.StateEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;


@Entity
@Table
@Data
//@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends StateEntity {

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

    //DateOfRegistration=created (StateEntity)

    private Integer adminDegree;

    @NotNull
    private boolean isActive;

    @Email
    @NotNull
    private String email;

    @NotNull
    private boolean isAdmin;

    public User(@NotNull LocalDateTime created, LocalDateTime updated, @NotNull StatusRekordu statusRekordu) {
        super(created, updated, statusRekordu);
    }
}
