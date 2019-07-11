package library.controllers;

import library.models.Book;
import library.services.modelservices.BookService;
import library.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;

    }

    //Adnotacje przy parametrach: RequestBody, RequestParam, @ModelAttribute

    @RequestMapping("/addbook")
    public String addBook(@RequestBody Book book, User user) {
        bookService.addBook(book, user); //user-login bibliotekarza
        return "book";
    }

    @RequestMapping("/deletebook")
    public String deleteBook(@RequestBody Book book, User user) {
        bookService.deleteBook(book, user); //user-login bibliotekarza
        return "book";
    }

    @RequestMapping("/updatebook")
    public String updateBook(@RequestBody Book book, Integer userId, User user) {
        bookService.updateBook(book, userId, user); //user-login bibliotekarza
        return "book";
    }

    @RequestMapping("/sortedBooksByReleaseDate")
    public String sortedBooksByReleaseDate(ModelMap modelMap) {
        List<Book> sortedBooksByReleaseDate = bookService.sortedBooksByReleaseDate();
        modelMap.put("sortedBooksByReleaseDate", sortedBooksByReleaseDate);
        return "book";
    }

    @RequestMapping("/sortedBooksByAddingDate")
    public String sortedBooksByAddingDate(ModelMap modelMap) {
        List<Book> sortedBooksByAddingDate = bookService.sortedBooksByAddingDate();
        modelMap.put("sortedBooksByAddingDate", sortedBooksByAddingDate);
        return "book";
    }

    @RequestMapping("/booksReleasedInPeriod")
    public String booksReleasedInPeriod(ModelMap modelMap) {
        List<Book> booksReleasedInPeriod = bookService.booksReleasedInPeriod();
        modelMap.put("booksReleasedInPeriod", booksReleasedInPeriod);
        return "book";
    }

    @RequestMapping("/booksAddedInPeriod")
    public String booksAddedInPeriod(ModelMap modelMap) {
        List<Book> booksAddedInPeriod = bookService.booksAddedInPeriod();
        modelMap.put("booksAddedInPeriod", booksAddedInPeriod);
        return "book";
    }

}
