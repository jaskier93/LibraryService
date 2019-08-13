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

    private boolean isBookInLibary(Integer bookId) {
        BookState bookState = bookStateRepository.findBookStateByBook(bookId);
        if (bookState == null) {
            log.info("Nie znalezionego książki o podanym ID");
            return false;
        }
        if (bookState.getBookStateEnum() == BookStateEnum.ZNISZCZONA) {
            log.info("Ta książka jest zniszczona");
            return false;
        }
        return true;
    }
}
