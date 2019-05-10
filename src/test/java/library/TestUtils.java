package library;

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
        user.setDateOfBirth(LocalDate.now());
        user.setAdmin(false);
        user.setDateOfRegistration(LocalDate.now());
        user.setAdminDegree(0);
        user.setLastName("y");
        user.setSecondName("x");
        user.setName("z");

        return user;
    }

    public static BookState createBookState(Book book, Action action, BookStateEnum bookStateEnum) {
        BookState bookState = new BookState();
        bookState.setBook(book);
        bookState.setBookStateEnum(bookStateEnum);
        bookState.setDateOfReturn(LocalDate.now());
        bookState.setDateOfUpdating(LocalDate.now());
        bookState.setDateOfCreating(LocalDate.now());
        bookState.setDateOfLoan(LocalDate.now());
        bookState.setAction(action);
        bookState.setStatus(0);
        return bookState;
    }

    public static Author createAuthor() {
        Author author = new Author("Andrzej", "", "Sapkowski", LocalDate.of(2015, 12, 31),
                LocalDate.of(2015, 12, 31), LocalDate.of(2015, 12, 31), 5);
        return author;
    }

    public static Book createBook() {
        Author author = TestUtils.createAuthor();
        Book book = new Book("Wiedźmin", LocalDate.of(2015, 11, 22), LocalDate.of(2015, 12, 21),
                Category.ADVENTURE, AgeCategory.DOROŚLI, author, 5);
        return book;
    }

    public static Action createAction(Book book, User user) {
        Action action = new Action();
        action.setActionDescription("x");
        action.setBook(book);
        action.setUser(null);
        return action;
    }

    public static Payment createPayment(Book book, User user) {
        Payment payment = new Payment();
        payment.setAmount(10);
        payment.setActive(true);
        payment.setBook(book);
        payment.setDateOfPayment(LocalDate.now());
        payment.setUser(user);
        payment.setStatus(0);
        return payment;
    }
}