package library.services;

import library.enums.BookStateEnum;
import library.models.Book;
import library.models.BookState;
import library.repositories.BookStateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * serwis ma sprawdzać czy książka znajduje się w bibliotece-tzn czy nie jest zniszczona
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class BookStoreService {

    private final BookStateRepository bookStateRepository;

    private Book isBookInLibary(Integer bookId) {
        BookState bookState = bookStateRepository.findBookStateByBook(bookId);
        Book book = bookState.getBook();
        if (bookState == null) {
            log.info("Nie znalezionego książki o podanym ID");
        }
        if (bookState.getBookStateEnum() == BookStateEnum.ZNISZCZONA) {
            log.info("Ta książka jest zniszczona");
        }
        return book;
    }
}
