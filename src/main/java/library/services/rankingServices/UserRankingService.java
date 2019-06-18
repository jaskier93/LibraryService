package library.services.rankingServices;

import library.repositories.UserRepository;
import library.users.User;
import lombok.RequiredArgsConstructor;
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

    public List<User> topUsersByLoansQuantity(User user) {
        List<User> topUsersByLoansQuantity = userRepository.topUsersByLoansQuantity(user);
        Integer sizeOfList = topUsersByLoansQuantity.size();
        if (sizeOfList > NUMBER_OF_RESULTS) {
            List<User> topUsersByLoansQuantity2 = new ArrayList<>();
            for (int i = 0; i < sizeOfList; i++) {
                topUsersByLoansQuantity2.add(topUsersByLoansQuantity.get(i));
            }
            return topUsersByLoansQuantity2;
        } else if (Objects.isNull(topUsersByLoansQuantity)) {
            log.info("Lista jest pusta.");
            return null;
        }
        return topUsersByLoansQuantity;
    }

    public List<User> topUsersBySumOfBooksPages(User user) {
        List<User> topUsersBySumOfBooksPages = userRepository.topUsersBySumOfBooksPages(user);
        Integer sizeOfList = topUsersBySumOfBooksPages.size();
        if (sizeOfList > NUMBER_OF_RESULTS) {
            List<User> topUsersBySumOfBooksPages2 = new ArrayList<>();
            for (int i = 0; i < sizeOfList; i++) {
                topUsersBySumOfBooksPages2.add(topUsersBySumOfBooksPages.get(i));
            }
            return topUsersBySumOfBooksPages2;
        } else if (Objects.isNull(topUsersBySumOfBooksPages)) {
            log.info("Lista jest pusta.");
            return null;
        }
        return topUsersBySumOfBooksPages;
    }
}