package library.controllers.ModelControllers;

import library.models.Book;
import library.services.modelservices.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/sortedbooks")
@Controller
public class SortedBooksController {

    private final BookService bookService;

    @Autowired
    public SortedBooksController(BookService bookService) {
        this.bookService = bookService;
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
