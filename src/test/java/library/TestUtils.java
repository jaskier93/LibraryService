package library;

import library.enums.BookStateEnum;
import library.models.Action;
import library.models.Book;
import library.models.BookState;
import library.users.Person;
import library.users.User;

import java.time.LocalDate;

public class TestUtils {

    public static User createPerson(){
        User user = new User();
        user.setActive(true);
        user.setAdmin(true);
        user.setCreated(LocalDate.now());
        user.setDateOfBirth(LocalDate.now());
        user.setDescription("x");
        user.setLastName("y");
        user.setName("z");
        user.setPermissionDegree(true);
        return user;
    }

    public static BookState createBookState(Book book, Action action, BookStateEnum bookStateEnum){
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
}
