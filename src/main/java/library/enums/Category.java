package library.enums;

import library.exceptions.NoObjectException;
import lombok.Getter;

import java.util.Arrays;

public enum Category {
    ADVENTURE(1),
    BIOGRAPHY(2),
    CRIME(3),
    DICTIONARY(4),
    DRAMA(5),
    FANTASY(6),
    GUIDE(7),
    POETRY(8),
    ROMANCE(9),
    SCIENCE(10),
    SCIENCEFICTION(11),
    THRILLER(12);

    @Getter
    private Integer number;

    Category(Integer number) {
        this.number = number;
    }
}