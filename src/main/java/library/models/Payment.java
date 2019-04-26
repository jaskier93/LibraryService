package library.models;

import library.users.User;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotNull
    private User user;

    @Column
    @NotNull
    private Book book;

    @Column
    @NotNull
    private Integer amount;

    @Column
    @NotNull
    private BookState bookState;

    @Column
    @NotNull
    private boolean isActive;

    @Column
    @NotNull
    private Action action;

    @Column
    @NotNull
    private Integer status;

}
