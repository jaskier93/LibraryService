package library.models;

import library.users.Person;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "Actions")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String actionDescription;

    @ManyToOne
    private Book book;

    @ManyToOne
    private Person user;
}