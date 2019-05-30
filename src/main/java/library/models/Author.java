package library.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "Author")
@EqualsAndHashCode(callSuper = false)
public class Author extends StateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    private String secondName;

    @NotNull
    private String lastName;

    private LocalDate dateOfBirth;

    private LocalDate dateOfDeath;

    @NotNull
    private Integer status;
}
