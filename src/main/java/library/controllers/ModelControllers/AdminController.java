package library.controllers.ModelControllers;

import library.converters.JsonConverter;
import library.repositories.UserRepository;
import library.services.modelservices.UserService;
import library.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JsonConverter jsonConverter;

    @Autowired
    public AdminController(UserService userService, UserRepository userRepository, JsonConverter jsonConverter) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.jsonConverter = jsonConverter;
    }

    @RequestMapping("/ban")
    public String banUser(String json) {
        User user = jsonConverter.converJsonToUser(json);
        userService.banUser(user);
        userRepository.save(user);
        return "admin";
    }

    @RequestMapping("/unban")
    public String unBanUser(String json) {
        User user = jsonConverter.converJsonToUser(json);
        userService.unbanUser(user);
        userRepository.save(user);
        return "admin";
    }

    @RequestMapping("/giveadmin")
    public String giveUserAdmin(String json) {
        User user = jsonConverter.converJsonToUser(json);
        userService.giveUserAdmin(user);
        userRepository.save(user);
        return "admin";
    }
}
