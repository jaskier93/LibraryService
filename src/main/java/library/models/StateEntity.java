package library.models;


import library.enums.StatusRekordu;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class StateEntity {

    @NotNull
    private LocalDateTime created = LocalDateTime.now();

    private LocalDateTime updated;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusRekordu statusRekordu;

    @PrePersist
    private void StateEntityMethod() {
        if (statusRekordu == null) {
            statusRekordu = StatusRekordu.ACTIVE;
        }
        if (created == null) {
            created = LocalDateTime.now();
        }
    }
}
