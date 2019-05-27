package library;

import library.enums.*;
import library.models.*;
import library.users.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestUtils {

    //TODO:dodać wszędzie setowanie wartości z StateEntity

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
        Book book = new Book("WiedźminWiedźmin",
                LocalDate.of(2019, 5, 22),  //data wydania
                LocalDate.of(2019, 5, 21),  //data dodania do biblioteki
                Category.ADVENTURE, AgeCategory.DOROSLI, author, 5);
        return book;
    }

    public static Action createAction(Book book, User user) {
        Action action = new Action();
        action.setActionDescription(ActionDescription.TEST);
        action.setBook(book);
        action.setUser(null); //TODO: czy user? do sprawdzenia
        action.setCreated(LocalDateTime.now());
        action.setStatusRekordu(StatusRekordu.ACTIVE);
        action.setUpdated(LocalDateTime.now());
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
