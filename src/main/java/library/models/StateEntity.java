package library.models;


import library.enums.StatusRekordu;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class StateEntity {

    private LocalDateTime created;

    private LocalDateTime updated;

    @Enumerated(EnumType.STRING)
    private StatusRekordu statusRekordu;

    public StateEntity() {
        this.created = LocalDateTime.now();;
        this.statusRekordu = StatusRekordu.ACTIVE;
    }
}
