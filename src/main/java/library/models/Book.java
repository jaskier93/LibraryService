package library.models;

import library.converters.CategoryConverter;
import library.enums.AgeCategory;
import library.enums.Category;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "Books")
@RequiredArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String title;

    private LocalDate releaseDate; //data wydania książki

    @NotNull
    private LocalDate addingDate; //created date

    @Convert(converter = CategoryConverter.class)
    private Category category;

    @Enumerated(EnumType.STRING)
    private AgeCategory ageCategory;

    @NotNull
    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Author author;

    @NotNull
    private Integer status;

    @Builder
    public Book(@NotNull String title, LocalDate releaseDate, @NotNull LocalDate addingDate,
                Category category, AgeCategory ageCategory, @NotNull Author author, @NotNull Integer status) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.addingDate = addingDate;
        this.category = category;
        this.ageCategory = ageCategory;
        this.author = author;
        this.status = status;
    }
}
