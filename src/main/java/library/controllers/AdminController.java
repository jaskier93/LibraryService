package library.controllers;

import library.services.modelservices.UserService;
import library.users.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    private UserService userService;

    @RequestMapping("/banuser")
    public String banUser(User user) {
        userService.banUser(user);
        return "admin";
    }

    @RequestMapping("/unbanuser")
    public String unBanUser(User user) {
        userService.unbanUser(user);
        return "admin";
    }

    @RequestMapping("/giveuseradmin")
    public String giveUserAdmin(User user) {
        userService.giveUserAdmin(user);
        return "admin";
    }
}
