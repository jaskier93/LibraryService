package library.models;

import library.models.Book;
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
    @NotNull
    private LocalDateTime dateOfAdding;

    @Column
    //dodać adnotację manytomany lub manytoone
    private Set <Book> bookSet=new HashSet<>();
}
