package library.models;

import library.models.Book;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@RequiredArgsConstructor
@Data
@Table(name = "Author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    private String secondName;

    @NotNull
    private String lastName;

    //czy te daty przydadzą się?

    private LocalDate dateOfBirth;

    private LocalDate dateOfDeath;

    @NotNull
    private LocalDate created;


    @NotNull
    private Integer status;

    @Builder //póki co bez Book book
    public Author(@NotNull String name, String secondName, @NotNull String lastName, LocalDate dateOfBirth, LocalDate dateOfDeath,
                  @NotNull LocalDate created, @NotNull Integer status) {
        this.name = name;
        this.secondName = secondName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.created = created;
        this.status = status;
    }
}
