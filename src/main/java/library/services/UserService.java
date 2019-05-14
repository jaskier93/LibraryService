package library.services;

import library.repositories.UserRepository;
import library.users.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User user) {
        user.setDateOfRegistration(LocalDate.now());
        user.setSecondName(user.getSecondName()); //opcjonalne
        user.setLastName(user.getLastName());
        user.setName(user.getName());
        user.setAdminDegree(user.getAdminDegree());
        user.setActive(user.isActive());
        user.setAdmin(user.isAdmin());
        user.setDateOfBirth(user.getDateOfBirth());
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        if (userRepository.findUserByLastName(user.getLastName()).isEmpty()) {
            log.info("Nie ma takiego użytkownika");
        } else {

            user.setDateOfRegistration(LocalDate.now()); //opcjonalne-ewentualnie dodać datę zaktualizowania danych
            user.setSecondName(user.getSecondName()); //opcjonalne
            user.setLastName(user.getLastName());
            user.setName(user.getName());
            user.setAdminDegree(user.getAdminDegree());
            user.setActive(user.isActive());
            user.setAdmin(user.isAdmin());
            user.setDateOfBirth(user.getDateOfBirth());
        }
        return userRepository.save(user);
    }

    public User banUser(User user) {
        user.setActive(false);
        return userRepository.save(user);
    }
}
