package library.users;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table
public class Admin extends Person {
    @Column
    @NotNull
    private boolean permisionDegree;
}
