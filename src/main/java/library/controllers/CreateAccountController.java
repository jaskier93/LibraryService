package library.controllers;

import library.services.modelservices.UserService;
import library.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CreateAccountController {

    private UserService userService;

    @Autowired
    public CreateAccountController(UserService userService) {
        this.userService = userService;
    }

    /**
     * return zwraca Stringa, który jest nazwą pliku html
     * do dodania ewentualnie jakaś autoryzacja poprzez zalogowanie się (przy update danych)
     */

    @RequestMapping("/createaccount")
    public String createAccount(@RequestBody User user) {
        userService.addUser(user);
        //TODO wysłanie maila powitalnego z informacjami
        return "account";
    }
}
