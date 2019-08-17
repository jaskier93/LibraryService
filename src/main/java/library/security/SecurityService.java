package library.security;

import library.models.User;
import library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerNewUser(CreateUserCommand command) {
        if (loginExists(command.getLogin())) {
            throw new LoginExistsException(command.getLogin());
        }
        User user = new User();
        user.setLogin(command.getLogin());
        user.setPassword(passwordEncoder.encode(command.getPassword()));

        return userRepository.save(user);
    }

    private boolean loginExists(String login) {
        return userRepository.findUserByLogin(login) != null;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
