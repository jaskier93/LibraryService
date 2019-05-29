package library.models;


import library.enums.StatusRekordu;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
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

    public StateEntity(@NotNull LocalDateTime created, LocalDateTime updated, @NotNull StatusRekordu statusRekordu) {
        this.created = LocalDateTime.now();
        this.updated = null;
        this.statusRekordu = StatusRekordu.ACTIVE;
    }

    /* public StateEntity() {
        this.created = LocalDateTime.now();
        this.statusRekordu = StatusRekordu.ACTIVE;
    }*/
}
