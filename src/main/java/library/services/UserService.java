package library.services;

import library.repositories.UserRepository;
import library.users.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * metoda może zwracać Stringa, który byłby treścią maila z informacjami początkowymi dla nowego użytkownika
     */
    public User addUser(User user) {
        user.setSecondName(user.getSecondName()); //opcjonalne
        user.setLastName(user.getLastName());
        user.setEmail(user.getEmail());
        user.setName(user.getName());
        user.setAdminDegree(user.getAdminDegree());
        user.setActive(user.isActive());
        user.setAdmin(user.isAdmin());
        user.setDateOfBirth(user.getDateOfBirth());
        return userRepository.save(user);
    }

    //TODO: do poprawki
    public void updateUser(Integer userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            log.info("Nie ma takiego użytkownika");
        } else {
            user.setSecondName(user.getSecondName()); //opcjonalne
            user.setLastName(user.getLastName());
            user.setName(user.getName());
            user.setEmail(user.getEmail());
            user.setAdminDegree(user.getAdminDegree());
            user.setActive(user.isActive()); //ta zmienna raczej nie będzie zmieniana
            user.setAdmin(user.isAdmin()); //ta raczej też
            user.setDateOfBirth(user.getDateOfBirth());
            userRepository.save(user);
        }
    }

    /**
     * zwraca listę dorosłych użytkowników
     * może się przydać np. przy wysyłaniu maila o nowościach dla dorosłych
     */
    public List<User> adultUsers() {
        return userRepository.findAdultUsers(LocalDate.now().minusYears(18));
    }

    public User banUser(User user) {
        user.setActive(false);
        return userRepository.save(user);
    }
}
