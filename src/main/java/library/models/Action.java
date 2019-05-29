package library.models;

import library.enums.ActionDescription;
import library.enums.StatusRekordu;
import library.users.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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