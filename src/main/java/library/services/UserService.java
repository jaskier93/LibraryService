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

    public void updateUser(Integer userId, User user) {
        User userFromBase = userRepository.findUserById(userId);
        if (userFromBase == null) {
            log.info("Nie ma takiego użytkownika");
        } else {
            if (!user.getSecondName().isEmpty()) {
                userFromBase.setSecondName(user.getSecondName());
            }
            if (!user.getLastName().isEmpty()) {
                userFromBase.setLastName(user.getLastName());
            }
            if (!user.getName().isEmpty()) {
                userFromBase.setName(user.getName());
            }
            if (!user.getEmail().isEmpty()) {
                userFromBase.setEmail(user.getEmail());
            }
            if (!user.getAdminDegree().equals(userFromBase.getAdminDegree())) {
                userFromBase.setAdminDegree(user.getAdminDegree());
            }
            if (!user.getDateOfRegistration().isEqual(userFromBase.getDateOfBirth())) {
                userFromBase.setDateOfBirth(user.getDateOfBirth());
            }
            userRepository.save(userFromBase);
        }
    }

    /**
     * zwraca listę dorosłych użytkowników
     * może się przydać np. przy wysyłaniu maila o nowościach dla dorosłych
     */
    public List<User> adultUsers() {
        return userRepository.findAdultUsers(LocalDate.now().minusYears(18));
    }

    /**
     * można ewentualnie pomyśleć o tym, żeby kasować zbanowanego usera np po roku,
     * a w przypadku, gdy np. odkupi zniszczoną książkę-można zdjąć bana
     */
    public User banUser(User user) {
        user.setActive(false);
        return userRepository.save(user);
    }

    public User unbanUser(User user) {
        user.setActive(true);
        return userRepository.save(user);
    }

    //nadanie użytkownikowi uprawnień administratora
    public User giveUserAdmin(User user) {
        user = userRepository.getOne(user.getId());
        user.setAdmin(true);
        return userRepository.save(user);
    }
}
