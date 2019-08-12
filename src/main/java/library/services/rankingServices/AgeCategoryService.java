package library.services.rankingServices;

import library.enums.AgeCategory;
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
 * ranking najczęściej wypożyczanych książek w danej kategorii wiekowej
 * 3 metody-każda dla odrębnej kategorii wiekowej
 */
@Service
@Slf4j
public class AgeCategoryService {
    private final static Integer NUMBER_OF_RESULTS = 10;
    private final BookRepository bookRepository;

    @Autowired
    public AgeCategoryService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> topLoanedBooksByAgeCategory(AgeCategory ageCategory) {
        List<Book> topLoanedBooksByAgeCategory = bookRepository.topLoanedBooksByAgeCategory(ageCategory);
        Integer sizeOfList = topLoanedBooksByAgeCategory.size();
        if (sizeOfList > NUMBER_OF_RESULTS) {
            List<Book> top10LoanedBooksByAgeCategory = new ArrayList<>();
            for (int i = 0; i < NUMBER_OF_RESULTS; i++) {
                top10LoanedBooksByAgeCategory.add(topLoanedBooksByAgeCategory.get(i));
            }
            return top10LoanedBooksByAgeCategory;
        } else if (Objects.isNull(topLoanedBooksByAgeCategory)) {
            log.info("Lista jest pusta.");
            throw new ExceptionEmptyList("Brak wyników");
        }
        return topLoanedBooksByAgeCategory;
    }
}
