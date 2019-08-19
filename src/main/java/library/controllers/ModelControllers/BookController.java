package library.controllers.ModelControllers;

import library.converters.ActionJson;
import library.converters.JsonConverter;
import library.models.Book;
import library.repositories.BookRepository;
import library.repositories.UserRepository;
import library.services.modelservices.BookService;
import library.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/books")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class BookController {

    private final BookService bookService;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final JsonConverter jsonConverter;

    @Autowired
    public BookController(BookService bookService, BookRepository bookRepository, UserRepository userRepository, JsonConverter jsonConverter) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.jsonConverter = jsonConverter;
    }

    //Adnotacje przy parametrach: RequestBody, RequestParam, @ModelAttribute

    @RequestMapping("/add")
    public String addBook(@RequestBody Book book, @RequestBody User user) {
        bookService.addBook(book, user); //user-login bibliotekarza
        return "book";
    }

    @RequestMapping("/delete")
    public String deleteBook(@RequestBody String json) {
        ActionJson actionJson = jsonConverter.convertJsonToAction(json);
        Book book = bookRepository.getOne(actionJson.getBookId());
        User user = userRepository.getOne(actionJson.getUserId());
        bookService.deleteBook(book, user); //user-login bibliotekarza
        return "book";
    }

    //Book book - zaktualizowana książka, json-informacje o bookId -książce, którą będziemy aktualizować oraz ID bibliotekarza, który wprowadza aktualizacje
    @RequestMapping("/update")
    public String updateBook(@RequestBody Book book, @RequestBody String json) {
        ActionJson actionJson = jsonConverter.convertJsonToAction(json);
        Book bookFromBase = bookRepository.getOne(actionJson.getBookId());
        User user = userRepository.getOne(actionJson.getUserId());
        bookService.updateBook(book, bookFromBase.getId(), user); //user-login bibliotekarza
        return "book";
    }
}
