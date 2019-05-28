package library.models;

import library.converters.CategoryConverter;
import library.enums.AgeCategory;
import library.enums.Category;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "Books")
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class Book extends StateEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String title;

    private LocalDate releaseDate; //data wydania książki

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
}
