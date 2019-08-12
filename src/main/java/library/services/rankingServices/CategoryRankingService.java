package library.services.rankingServices;

import library.enums.Category;
import library.models.Book;
import library.repositories.BookRepository;
import library.exceptions.ExceptionEmptyList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * wyświetlanie najpopularniejszym książek danej kategorii
 */

@Service
@Slf4j
public class CategoryRankingService {

    private final static Integer NUMBER_OF_RESULTS = 10;
    private final BookRepository bookRepository;

    @Autowired
    public CategoryRankingService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> topLoanedBooksByCategory(Category category) {
        List<Book> topLoanedBooksByCategory = bookRepository.topLoanedBooksByCategory(category);
        Integer sizeOfList = topLoanedBooksByCategory.size();
        if (sizeOfList > NUMBER_OF_RESULTS) {
            List<Book> top10LoanedBooksByCategory = new ArrayList<>();
            for (int i = 0; i < NUMBER_OF_RESULTS; i++) {
                top10LoanedBooksByCategory.add(topLoanedBooksByCategory.get(i));
            }
            return top10LoanedBooksByCategory;
        } else if
        (Objects.isNull(topLoanedBooksByCategory)) {
            log.info("Lista jest pusta.");
            throw new ExceptionEmptyList("Brak wyników");
        }
        return topLoanedBooksByCategory;
    }
}
