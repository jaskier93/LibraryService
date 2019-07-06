package library.controllers;

import library.models.Book;
import library.models.BookState;
import library.models.LibraryCard;
import library.services.LibraryCardService;
import library.services.modelservices.BookService;
import library.services.modelservices.UserService;
import library.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {

    private UserService userService;
    private LibraryCardService libraryCardService;
    private BookService bookService;

    @Autowired
    public UserController(UserService userService, LibraryCardService libraryCardService, BookService bookService) {
        this.userService = userService;
        this.libraryCardService = libraryCardService;
        this.bookService = bookService;
    }

    @RequestMapping("/updateaccount")
    public String updateAccount(@RequestBody User user, Integer userId) {
        userService.updateUser(userId, user);
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
