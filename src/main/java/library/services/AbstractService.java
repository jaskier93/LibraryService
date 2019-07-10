package library.services;

import library.enums.ActionDescription;
import library.models.Book;
import library.users.User;
import library.validators.mainValidators.AbstractValidator;
import library.validators.ZbiorczyWalidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public abstract class AbstractService {

    private final ZbiorczyWalidator zbiorczyWalidator;

    public abstract void doSomethingWithBook(User user, Book book);

    public abstract void cancel(User user, Book book); //ewentualnie id -dla różnych serwisów mogą być potrzebne różne parametry, a można je zawsze wyciągnąć za pomocą ID

    public abstract List<AbstractValidator> getValidators();

    public abstract List<ActionDescription> allowedActions();


    private void method(User user, Book book) {
        doSomethingWithBook(user, book);
        zbiorczyWalidator.checkIt(getValidators(), user);
//        TODO: walidacja;

    }
}
