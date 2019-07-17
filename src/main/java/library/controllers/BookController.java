package library.controllers;

import library.converters.ActionJson;
import library.converters.JsonConverter;
import library.models.Book;
import library.repositories.BookRepository;
import library.repositories.UserRepository;
import library.services.modelservices.BookService;
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
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private JsonConverter jsonConverter;

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
        ActionJson actionJson = jsonConverter.convertJsonToActionJson(json);
        Book book = bookRepository.getOne(actionJson.getBookId());
        User user = userRepository.getOne(actionJson.getUserId());
        bookService.deleteBook(book, user); //user-login bibliotekarza
        return "book";
    }

    //Book book - zaktualizowan książka, json-informacje o bookId -książce, którą będziemy aktualizować oraz ID bibliotekarza, który wprowadza aktualizacje
    @RequestMapping("/update")
    public String updateBook(@RequestBody Book book, @RequestBody String json) {
        ActionJson actionJson = jsonConverter.convertJsonToActionJson(json);
        Book bookFromBase = bookRepository.getOne(actionJson.getBookId());
        User user = userRepository.getOne(actionJson.getUserId());
        bookService.updateBook(book, bookFromBase.getId(), user); //user-login bibliotekarza
        return "book";
    }

    @RequestMapping("/sortedByReleaseDate")
    public String sortedBooksByReleaseDate(ModelMap modelMap) {
        List<Book> sortedBooksByReleaseDate = bookService.sortedBooksByReleaseDate();
        modelMap.put("sortedBooksByReleaseDate", sortedBooksByReleaseDate);
        return "book";
    }

    @RequestMapping("/sortedByAddingDate")
    public String sortedBooksByAddingDate(ModelMap modelMap) {
        List<Book> sortedBooksByAddingDate = bookService.sortedBooksByAddingDate();
        modelMap.put("sortedBooksByAddingDate", sortedBooksByAddingDate);
        return "book";
    }

    @RequestMapping("/releasedInPeriod")
    public String booksReleasedInPeriod(ModelMap modelMap) {
        List<Book> booksReleasedInPeriod = bookService.booksReleasedInPeriod();
        modelMap.put("booksReleasedInPeriod", booksReleasedInPeriod);
        return "book";
    }

    @RequestMapping("/addedInPeriod")
    public String booksAddedInPeriod(ModelMap modelMap) {
        List<Book> booksAddedInPeriod = bookService.booksAddedInPeriod();
        modelMap.put("booksAddedInPeriod", booksAddedInPeriod);
        return "book";
    }

}
