package library.services.rankingServices;

import library.enums.AgeCategory;
import library.enums.Category;
import library.models.Author;
import library.repositories.AuthorRepository;
import library.services.exceptions.ExceptionEmptyList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * wyświetlanie najpopularniejszych (najczęściej wypożyczane książki danego autora) autorów
 */

@Service
@Slf4j
public class AuthorRankingService {
    private final static Integer NUMBER_OF_RESULTS = 10;
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorRankingService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> topAgeCategoryAuthorsByLoansQuantity(AgeCategory ageCategory) {
        List<Author> topAgeCategoryAuthorsByLoansQuantity = authorRepository.topAgeCategoryAuthorsByLoansQuantity(ageCategory);
        Integer sizeOfList = topAgeCategoryAuthorsByLoansQuantity.size();
        if (sizeOfList > NUMBER_OF_RESULTS) {
            List<Author> top10LoanedBooksByAgeCategory = new ArrayList<>();
            for (int i = 0; i < NUMBER_OF_RESULTS; i++) {
                top10LoanedBooksByAgeCategory.add(topAgeCategoryAuthorsByLoansQuantity.get(i));
            }
            return top10LoanedBooksByAgeCategory;
        } else if (Objects.isNull(topAgeCategoryAuthorsByLoansQuantity)) {
            log.info("Lista jest pusta.");
            throw new ExceptionEmptyList("Brak wyników");
        }
        return topAgeCategoryAuthorsByLoansQuantity;
    }

    public List<Author> topCategoryAuthorsByLoansQuantity(Category category) {
        List<Author> topCategoryAuthorsByLoansQuantity = authorRepository.topCategoryAuthorsByLoansQuantity(category);
        Integer sizeOfList = topCategoryAuthorsByLoansQuantity.size();
        if (sizeOfList > NUMBER_OF_RESULTS) {
            List<Author> top10LoanedBooksByAgeCategory = new ArrayList<>();
            for (int i = 0; i < NUMBER_OF_RESULTS; i++) {
                top10LoanedBooksByAgeCategory.add(topCategoryAuthorsByLoansQuantity.get(i));
            }
            return top10LoanedBooksByAgeCategory;
        } else if (Objects.isNull(topCategoryAuthorsByLoansQuantity)) {
            log.info("Lista jest pusta.");
            throw new ExceptionEmptyList("Brak wyników");
        }
        return topCategoryAuthorsByLoansQuantity;
    }

    public List<Author> topAuthorsByLoansQuantity() {
        List<Author> topAuthorsByLoansQuantity = authorRepository.topAuthorsByLoansQuantity();
        Integer sizeOfList = topAuthorsByLoansQuantity.size();
        if (sizeOfList > NUMBER_OF_RESULTS) {
            List<Author> top10LoanedBooksByAgeCategory = new ArrayList<>();
            for (int i = 0; i < NUMBER_OF_RESULTS; i++) {
                top10LoanedBooksByAgeCategory.add(topAuthorsByLoansQuantity.get(i));
            }
            return top10LoanedBooksByAgeCategory;
        } else if (Objects.isNull(topAuthorsByLoansQuantity)) {
            log.info("Lista jest pusta.");
            throw new ExceptionEmptyList("Brak wyników");
        }
        return topAuthorsByLoansQuantity;
    }

    public List<Author> topAuthorsBySumOfBookPages() {
        List<Author> topAuthorsBySumOfBookPages = authorRepository.topAuthorsBySumOfBookPages();
        Integer sizeOfList = topAuthorsBySumOfBookPages.size();
        if (sizeOfList > NUMBER_OF_RESULTS) {
            List<Author> top10LoanedBooksByAgeCategory = new ArrayList<>();
            for (int i = 0; i < NUMBER_OF_RESULTS; i++) {
                top10LoanedBooksByAgeCategory.add(topAuthorsBySumOfBookPages.get(i));
            }
            return top10LoanedBooksByAgeCategory;
        } else if (Objects.isNull(topAuthorsBySumOfBookPages)) {
            log.info("Lista jest pusta.");
            throw new ExceptionEmptyList("Brak wyników");
        }
        return topAuthorsBySumOfBookPages;
    }
}
