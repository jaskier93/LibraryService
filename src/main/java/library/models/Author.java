package library.models;

import library.enums.StatusRekordu;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
//@RequiredArgsConstructor
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

    public Author(@NotNull LocalDateTime created, LocalDateTime updated, @NotNull StatusRekordu statusRekordu) {
        super(created, updated, statusRekordu);
    }
}
