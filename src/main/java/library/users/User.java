package library.users;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class User extends Person {


    @Column
    @NotNull
    private LocalDate dateOfBirth;

    @Column
    @NotNull
    private LocalDate dateOfAdding;

    @Column
    private String description;

    @Column
    @NotNull
    private boolean isActive;
}
