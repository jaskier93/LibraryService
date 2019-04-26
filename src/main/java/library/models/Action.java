package library.models;

import library.users.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotNull
    private String description;

    @Column
    @NotNull
    private Book book;

    @Column
    @NotNull
    private User user;
}
