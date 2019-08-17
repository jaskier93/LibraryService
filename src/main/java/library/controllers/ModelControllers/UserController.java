package library.controllers.ModelControllers;

import library.converters.JsonConverter;
import library.models.BookState;
import library.models.LibraryCard;
import library.services.LibraryCardService;
import library.services.modelservices.BookService;
import library.services.modelservices.UserService;
import library.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final LibraryCardService libraryCardService;
    private final BookService bookService;
    private final JsonConverter jsonConverter;

    @Autowired
    public UserController(UserService userService, LibraryCardService libraryCardService, BookService bookService, JsonConverter jsonConverter) {
        this.userService = userService;
        this.libraryCardService = libraryCardService;
        this.bookService = bookService;
        this.jsonConverter = jsonConverter;
    }

    @RequestMapping("/updateaccount")
    public String updateAccount(@RequestBody User user, String json) {
        Integer userId = jsonConverter.converJsonToUser(json).getId();
        userService.updateUser(userId, user);
        return "account";
    }

    @RequestMapping("/changepassword")
    public String changepassword(User user, @RequestParam String newPassword) {
        user.setPassword(newPassword);
        //TODO:walidacja nowego hasła
        userService.updateUser(user.getId(), user);
        return "account";
    }

    @RequestMapping("/librarycard")
    public String showLibraryCard(User user, ModelMap modelMap) {
        LibraryCard libraryCard = libraryCardService.showLibraryCard(user);
        modelMap.put("libraryCard", libraryCard);
        return "account";
    }

    @RequestMapping("/showloanhistory")
    public String showLoanHistory(User user, ModelMap modelMap) {
        List<BookState> loanHistory = bookService.showLoanHistory(user);
        modelMap.put("showLoanHistory", loanHistory);
        return "account";
    }
}
