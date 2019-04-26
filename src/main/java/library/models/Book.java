package library.models;

import library.enums.AgeCategory;
import library.enums.Category;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

@Entity
@Data
@Table
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotNull
    private String title;

    @Column
    private LocalDate releaseDate; //data wydania książki

    @Column
    @NotNull
    private LocalDate addingDate; //created date

    @Column
    @NotNull
    private Category category;

    @Column
    @NotNull
    private AgeCategory ageCategory;

    @Column
    private Set <Author> authorSet=new HashSet<>();
}
