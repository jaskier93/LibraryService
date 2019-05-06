package library.users;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String secondName;

    @NotNull
    private String lastName;

    @NotNull
    private boolean permissionDegree;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private LocalDate created;

    private String description;

    @NotNull
    private boolean isActive;

    @NotNull
    private boolean isAdmin;

    public Person(@NotNull boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
