package library.services.modelservices;

import library.enums.BookStateEnum;
import library.enums.StatusRekordu;
import library.models.Action;
import library.models.BookState;
import library.repositories.BookStateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
        bookState.setDateOfReturn(LocalDate.now().plusDays(30)); //nowa data zwrotu
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
        bookState.setDateOfReturn(null);
        bookState.setUpdated(LocalDateTime.now());
        bookState.setStatusRekordu(StatusRekordu.ACTIVE);
        return bookStateRepository.save(bookState);
    }

}
