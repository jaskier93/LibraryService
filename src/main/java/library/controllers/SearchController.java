package library.controllers;

import library.models.Author;
import library.models.Book;
import library.repositories.AuthorRepository;
import library.repositories.BookRepository;
import library.repositories.UserRepository;
import library.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private UserRepository userRepository;

    @Autowired
    public SearchController(BookRepository bookRepository, AuthorRepository authorRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.userRepository = userRepository;
    }


    @RequestMapping("/findBookByTitle")
    public String findBookByTitle(@RequestParam String title, ModelMap modelMap) {
        List<Book> bookList = bookRepository.findBookByTitle(title);
        modelMap.put("bookList", bookList);
        return "search";
    }

    @RequestMapping("/findAuthorByLastName")
    public String findAuthorByLastName(@RequestParam String lastName, ModelMap modelMap) {
        List<Author> authorList = authorRepository.findAuthorsByLastName(lastName);
        modelMap.put("authorList", authorList);
        return "search";
    }

    @RequestMapping("/findUserByLastName")
    public String findUserByLastName(@RequestParam String lastName, ModelMap modelMap) {
        List<User> userList = userRepository.findUserByLastName(lastName);
        modelMap.put("userList", userList);
        return "search";
    }
}
