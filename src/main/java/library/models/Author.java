package library.models;

import library.models.Book;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotNull
    private String name;
    private String secondName;

    @Column
    @NotNull
    private String lastName;

    //czy te daty przydadzą się?

    @Column
    private LocalDate dateOfBirth;

    @Column
    private LocalDate dateOfDeath;

    @Column
  //  @NotNull
    private LocalDate dateOfAdding;

    @Column
    //dodać adnotację manytomany lub manytoone
    private Set<Book> bookSet=new HashSet<>();

    @Column
    @NotNull
    private Integer status;

    @Builder //póki co bez Book book
    public Author(@NotNull String name, String secondName, @NotNull String lastName, LocalDate dateOfBirth, LocalDate dateOfDeath,
                   LocalDate dateOfAdding, @NotNull Integer status) {
        this.name = name;
        this.secondName = secondName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.dateOfAdding = dateOfAdding;
        this.status = status;
    }
}
