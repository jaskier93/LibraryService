package library.services;

import library.models.Book;
import library.users.User;
import org.springframework.stereotype.Service;

@Service
public abstract class MotherOfServices {

    public abstract void DoSomethingWithBook(User user, Book book);

    public abstract void cancel(User user, Book book);

    public abstract void corection(User user, Book book);


    private void method(User user, Book book) {
        DoSomethingWithBook(user, book);
//        TODO: walidacja;

    }
}
