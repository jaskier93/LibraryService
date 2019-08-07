package library.services.modelservices;

import library.enums.BookStateEnum;
import library.enums.StatusRekordu;
import library.models.Action;
import library.models.Book;
import library.models.BookState;
import library.repositories.BookStateRepository;
import library.services.exceptions.ExceptionEmptyList;
import library.services.exceptions.TooManyResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookStateService {

    private final BookStateRepository bookStateRepository;

    //własciwie może być używane w przypadku wypożyczania, ważne, żeby ustawiać pole Updated jako null
    public BookState prolongation(Action action) {
        BookState bookState = new BookState();
        bookState.setBook(action.getBook());
        bookState.setBookStateEnum(BookStateEnum.WYPOZYCZONA);
        bookState.setAction(action);
        bookState.setDateTo(LocalDate.now().plusDays(30)); //nowa data zwrotu
        bookState.setUpdated(LocalDateTime.now());
        bookState.setStatusRekordu(StatusRekordu.ACTIVE);
        return bookStateRepository.save(bookState);
    }

    public BookState returnBook(Action action) {
        BookState bookState = new BookState();
        bookState.setBook(action.getBook());
        bookState.setBookStateEnum(BookStateEnum.ZWROCONA);
        bookState.setAction(action);
        bookState.setUpdated(LocalDateTime.now());
        bookState.setStatusRekordu(StatusRekordu.ACTIVE);
        return bookStateRepository.save(bookState);
    }

    public BookState destroyBook(Action action) {
        BookState bookState = new BookState();
        bookState.setBook(action.getBook());
        bookState.setBookStateEnum(BookStateEnum.ZNISZCZONA);
        bookState.setAction(action);
        bookState.setUpdated(LocalDateTime.now());
        bookState.setStatusRekordu(StatusRekordu.ACTIVE);
        return bookStateRepository.save(bookState);
    }

    public BookState newBook(Action action) {
        BookState bookState = new BookState();
        bookState.setBook(action.getBook());
        bookState.setBookStateEnum(BookStateEnum.NOWA);
        bookState.setAction(action);
        bookState.setDateTo(null);
        bookState.setUpdated(LocalDateTime.now());
        bookState.setStatusRekordu(StatusRekordu.ACTIVE);
        return bookStateRepository.save(bookState);
    }

    private BookState createBookState(Action action, BookStateEnum bsEnum){
        BookState bookState = new BookState();
        bookState.setBook(action.getBook());
        bookState.setBookStateEnum(bsEnum);
        bookState.setAction(action);
        bookState.setUpdated(LocalDateTime.now());
        bookState.setStatusRekordu(StatusRekordu.ACTIVE);
        return bookStateRepository.save(bookState);
    }

    public BookState closeLastBookStateAndCreateOneWith(Book book, Action action, BookStateEnum bookStateEnum){
        findBookStateAndCloseHim(book);
        BookState newObligatoryBookState = createBookState(action, bookStateEnum);
        return newObligatoryBookState;
    }

    private void findBookStateAndCloseHim(Book book){
        BookState bookStateToClose = findObligatoryBookState(book);
        bookStateToClose.setDateTo(LocalDate.now());
        bookStateRepository.save(bookStateToClose);
    }

    private BookState findObligatoryBookState(Book book){
        List<BookState> bookStateList = bookStateRepository.findApplicableBookState(book);
        if(bookStateList.isEmpty() || bookStateList == null){ // to można też inaczej zapoisać
            throw new ExceptionEmptyList("There is not existing bookState for book: " + book.getId());
        } else if (bookStateList.size() > 1){
            String msg = String.format("There is an error. For book id %d exists more than one obligatory bookstate", book.getId());
            throw new TooManyResultsException(msg);
        }

        return bookStateList.stream().findFirst().get();
    }

    /**
     * Setting the last bookState as history
     * @param book
     */
    private void findBookStateAndTakeHimDown(Book book){
        BookState bookStateToClose = findObligatoryBookState(book);
        bookStateToClose.setStatusRekordu(StatusRekordu.HISTORY);
        bookStateRepository.save(bookStateToClose);
    }

}
