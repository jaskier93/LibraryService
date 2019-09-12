package library.security;

import library.exceptions.LoginExistsException;
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

    public User registerNewUser(User givenUser) {
        if (loginExists(givenUser.getLogin())) {
            throw new LoginExistsException(givenUser.getLogin());
        }
        User user = new User();
        user.setLogin(givenUser.getLogin());
        user.setPassword(passwordEncoder.encode(givenUser.getPassword()));

        return userRepository.save(user);
    }

    private boolean loginExists(String login) {
        return userRepository.findUserByLogin(login) != null;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
