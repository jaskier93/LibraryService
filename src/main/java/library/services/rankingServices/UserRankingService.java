package library.services.rankingServices;

import library.repositories.UserRepository;
import library.exceptions.ExceptionEmptyList;
import library.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * wyświetlanie rankingu użytkowników-np najwięcej wypożyczeń, najwięcej przeczyanych stron (przy dodaniu pola w książce)
 */
@Service
@Slf4j
public class UserRankingService {

    private final static Integer NUMBER_OF_RESULTS = 10;
    private final UserRepository userRepository;

    @Autowired
    public UserRankingService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> topUsersByLoansQuantity() {
        List<User> topUsersByLoansQuantity = userRepository.topUsersByLoansQuantity();
        Integer sizeOfList = topUsersByLoansQuantity.size();
        if (sizeOfList > NUMBER_OF_RESULTS) {
            List<User> top10UsersByLoansQuantity = new ArrayList<>();
            for (int i = 0; i < NUMBER_OF_RESULTS; i++) {
                top10UsersByLoansQuantity.add(topUsersByLoansQuantity.get(i));
            }
            return top10UsersByLoansQuantity;
        } else if (Objects.isNull(topUsersByLoansQuantity)) {
            log.info("Lista jest pusta.");
            throw new ExceptionEmptyList("Brak wyników");
        }
        return topUsersByLoansQuantity;
    }

    public List<User> topUsersBySumOfBooksPages() {
        List<User> topUsersBySumOfBooksPages = userRepository.topUsersBySumOfBooksPages();
        Integer sizeOfList = topUsersBySumOfBooksPages.size();
        if (sizeOfList > NUMBER_OF_RESULTS) {
            List<User> top10UsersBySumOfBooksPages = new ArrayList<>();
            for (int i = 0; i < NUMBER_OF_RESULTS; i++) {
                top10UsersBySumOfBooksPages.add(topUsersBySumOfBooksPages.get(i));
            }
            return top10UsersBySumOfBooksPages;
        } else if (Objects.isNull(topUsersBySumOfBooksPages)) {
            log.info("Lista jest pusta.");
            throw new ExceptionEmptyList("Brak wyników");
        }
        return topUsersBySumOfBooksPages;
    }
}