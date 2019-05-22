package library;

import library.enums.ActionDescription;
import library.enums.AgeCategory;
import library.enums.BookStateEnum;
import library.enums.Category;
import library.models.*;
import library.users.User;

import java.time.LocalDate;

public class TestUtils {

    public static User createUser() {
        User user = new User();
        user.setActive(true);
        user.setDateOfBirth(LocalDate.now().minusYears(20));
        user.setAdmin(false);
        user.setDateOfRegistration(LocalDate.now());
        user.setAdminDegree(0);
        user.setEmail("example@gmail.com");
        user.setLastName("XXXYYYZZZ");
        user.setSecondName("x");
        user.setName("z");
        return user;
    }

    public static BookState createBookState(Book book, Action action, BookStateEnum bookStateEnum) {
        BookState bookState = new BookState();
        bookState.setBook(book);
        bookState.setBookStateEnum(bookStateEnum);
        bookState.setDateOfReturn(LocalDate.now());
        bookState.setDateOfUpdate(LocalDate.now());
        bookState.setDateOfCreating(LocalDate.now());
        bookState.setDateOfLoan(LocalDate.now());
        bookState.setAction(action);
        bookState.setStatus(1020304050);
        return bookState;
    }

    public static Author createAuthor() {
        Author author = new Author("Andrzej", "", "SapkowskiAndrzej",
                LocalDate.of(1955, 12, 31),
                LocalDate.of(2015, 12, 31),
                LocalDate.of(2018, 12, 31), 5);
        return author;
    }

    public static Book createBook(Author author) {
        Book book = new Book("WiedźminWiedźmin", LocalDate.of(2015, 11, 22), LocalDate.of(2015, 12, 21),
                Category.ADVENTURE, AgeCategory.DOROSLI, author, 5);
        return book;
    }

    public static Action createAction(Book book, User user) {
        Action action = new Action();
        action.setActionDescription(ActionDescription.TEST);
        action.setBook(book);
        action.setUser(null); //TODO: czy user? do sprawdzenia
        return action;
    }

    public static Payment createPayment(Book book, User user) {
        Payment payment = new Payment();
        payment.setAmount(1020304050);
        payment.setActive(true);
        payment.setBook(book);
        payment.setDateOfPayment(LocalDate.now());
        payment.setUser(user);
        payment.setStatus(1020304050);
        return payment;
    }
}
