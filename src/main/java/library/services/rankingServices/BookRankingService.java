package library.services.rankingServices;

import library.enums.AgeCategory;
import library.enums.Category;
import library.models.Book;
import library.repositories.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * wyświetlanie najczęściej wypożyczanych książek:
 * -ogólnie
 * -w danym tygodniu/miesiącu/roku
 */

@Service
@Slf4j
public class BookRankingService {
    private final static Integer NUMBER_OF_RESULTS = 10;
    private final BookRepository bookRepository;

    @Autowired
    public BookRankingService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> topLoanedBooksByAgeCategory(AgeCategory ageCategory) {
        List<Book> topLoanedBooks = bookRepository.topLoanedBooks();
        Integer sizeOfList = topLoanedBooks.size();
        if (sizeOfList > NUMBER_OF_RESULTS) {
            List<Book> topLoanedBooks2 = new ArrayList<>();
            for (int i = 0; i < sizeOfList; i++) {
                topLoanedBooks2.add(topLoanedBooks.get(i));
            }
            return topLoanedBooks2;
        } else if (Objects.isNull(topLoanedBooks)) {
            log.info("Lista jest pusta.");
            return null;
        }
        return topLoanedBooks;
    }


}
