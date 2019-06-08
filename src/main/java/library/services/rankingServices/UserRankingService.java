package library.services.rankingServices;

import library.repositories.UserRepository;
import library.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * wyświetlanie rankingu użytkowników-np najwięcej wypożyczeń, najwięcej przeczyanych stron (przy dodaniu pola w książce)
 */
@Service
@RequiredArgsConstructor
public class UserRankingService {

    private final UserRepository userRepository;

    public List<User> showUserRanking(){
        //TODO: lista useró z największą liczbą wypożyczeń/przeczytań stron/inne rankingi
        return userRepository.findAll();
    }
}
