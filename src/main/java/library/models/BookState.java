package library.models;

import library.enums.BookStateEnum;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Entity
@Table
public class BookState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate dateOfLoan;

    @Column
    @NotNull
    private LocalDate dateOfCreating;

    @Column
    private LocalDate dateOfUpdating;

    @Column
    private LocalDate dateOfReturn;

    @Column
    @NotNull
    private BookStateEnum bookStateEnum;

    @Column
    @NotNull
    private Action action;

}
