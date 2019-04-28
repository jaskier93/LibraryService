package library.users;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

public class Admin extends Person {

    public Admin() {
        super(true);
    }
}
