package library.models;

import library.enums.ActionDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "Actions")
@EqualsAndHashCode(callSuper = false)
public class Action extends StateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ActionDescription actionDescription;

    @ManyToOne
    private Book book;

    @ManyToOne
    private User user;
}