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

    //RM:
    // Problem: Nazwa metody wprowadza w błąd, dodatkowo złamana reguła SOLID -> osoba która chciała by użyć tej metody może być zdezorientowana - nazwa wskazuje na sprawdzenie
    //czy w bazie istnieje książka, natomiast metoda dodatkowo sprawdza stan książki.
    //Solucja: W zależności od potrzeby biznesowej - jeśli książka, która jest zniszczona nie może zostać wypożyczona to rozbiłbym te metode na IsBookExisting oraz
    // coś w stylu IsBookRentable - wtedy klient wołający te metodę ma jasną sprawę - może sprawdzić czy książka w ogóle istnieje lub sprawdzić czy ją się da wypożyczyć.
    // Jeśli była by potrzeba to stworzyłbym jeszcze IsBookBroken ale tutaj warto pamiętać o YAGNI.


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
