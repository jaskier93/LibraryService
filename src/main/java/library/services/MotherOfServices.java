package library.services;

import library.enums.ActionDescription;
import library.models.Book;
import library.users.User;
import library.validators.AbstractValidator;
import library.validators.ZbiorczyWalidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public abstract class MotherOfServices {

    private final ZbiorczyWalidator zbiorczyWalidator;

    public abstract void DoSomethingWithBook(User user, Book book);

    public abstract void cancel(User user, Book book);

    public abstract List<AbstractValidator> getValidators();

    public abstract List<ActionDescription> allowedActions();


    private void method(User user, Book book) {
        DoSomethingWithBook(user, book);
        zbiorczyWalidator.checkIt(getValidators(), user);
//        TODO: walidacja;

    }
}
