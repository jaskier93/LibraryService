package library;

import library.enums.*;
import library.models.*;
import library.models.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestUtils {

    public static User createUser() {
        User user = new User();
        user.setActive(true);
        user.setDateOfBirth(LocalDate.now().minusYears(20));
        user.setAdmin(false);
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());
        user.setStatusRekordu(StatusRekordu.TEST);
        user.setAdminDegree(0);
        user.setEmail("example@gmail.com");
        user.setLastName("XXXYYYZZZ");
        user.setLogin("exampleLogin");
        user.setPassword("examplePassword123");
        user.setSecondName("x");
        user.setName("z");
        return user;
    }

    public static BookState createBookState(Action action, BookStateEnum bookStateEnum) {
        BookState bookState = new BookState();
        bookState.setBookStateEnum(bookStateEnum);
        bookState.setDateTo(LocalDate.now());
        bookState.setCreated(LocalDateTime.now());
        bookState.setUpdated(LocalDateTime.now());
        bookState.setStatusRekordu(StatusRekordu.TEST);
        bookState.setDateFrom(LocalDate.now().minusDays(50));
        bookState.setAction(action);
        bookState.setBook(action.getBook());
        bookState.setLibranian(null);
        bookState.setStatus(1020304050);
        return bookState;
    }

    public static Author createAuthor() {
        Author author = new Author();
        author.setName("Andrzej");
        author.setLastName("SapkowskiAndrzej");
        author.setDateOfBirth(LocalDate.of(1948, 6, 21));
        author.setDateOfDeath(LocalDate.of(2008, 4, 25)); //przykładowa data
        author.setCreated(LocalDateTime.now());
        author.setUpdated(LocalDateTime.now());
        author.setUpdated(null);
        author.setStatusRekordu(StatusRekordu.TEST);
        author.setStatus(5);
        return author;
    }

    public static Book createBook(Author author) {
        Book book = new Book();
        book.setTitle("WiedźminWiedźmin");
        book.setAuthor(author);
        book.setReleaseDate(LocalDate.of(2019, 5, 2));
        book.setCreated(LocalDateTime.of(2019, 5, 22, 15, 40));
        book.setStatusRekordu(StatusRekordu.TEST);
        book.setAgeCategory(AgeCategory.DOROSLI);
        book.setUpdated(null);
        book.setPages(500);
        book.setCategory(Category.ADVENTURE);
        book.setStatus(5);
        return book;
    }

    public static Action createAction(Book book, User user) {
        Action action = new Action();
        action.setActionDescription(ActionDescription.TEST);
        action.setBook(book);
        action.setUser(user);
        action.setCreated(LocalDateTime.now());
        action.setStatusRekordu(StatusRekordu.TEST);
        action.setUpdated(LocalDateTime.now());
        return action;
    }

    public static Action createAction(ActionDescription actionDescription, Book book, User user) {
        Action action = new Action();
        action.setActionDescription(actionDescription);
        action.setBook(book);
        action.setUser(user);
        action.setCreated(LocalDateTime.now());
        action.setStatusRekordu(StatusRekordu.TEST);
        action.setUpdated(LocalDateTime.now());
        return action;
    }

    public static Payment createPayment(Action action) {
        Payment payment = new Payment();
        payment.setAmount(1020304050);
        payment.setActive(true);
        payment.setBook(action.getBook());
        payment.setCreated(LocalDateTime.now());
        payment.setStatusRekordu(StatusRekordu.TEST);
        payment.setUpdated(LocalDateTime.now());
        payment.setStatus(1020304050);
        return payment;
    }
}
