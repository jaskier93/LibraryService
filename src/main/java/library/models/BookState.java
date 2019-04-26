package library.models;

import library.enums.BookStateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @Column
    @NotNull
    private Integer status;



}
