package library.models;

import library.enums.AgeCategory;
import library.enums.Category;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

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
    @NotNull
    private BookState bookState;

    @Column
    @NotNull
    private Author author;

    @Column
    @NotNull
    private Integer status;

    @Builder //zmiana autora/dopasowanie, żeby wymagało tylko ID, podobnie z kategorią
    public Book(@NotNull String title, LocalDate releaseDate, @NotNull LocalDate addingDate, @NotNull Category category, @NotNull AgeCategory ageCategory,
                @NotNull  BookState bookState,  @NotNull Author author, @NotNull Integer status) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.addingDate = addingDate;
        this.category = category;
        this.ageCategory = ageCategory;
        this.author = author;
        this.status = status;
    }
}
