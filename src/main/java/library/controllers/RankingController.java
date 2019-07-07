package library.controllers;

import library.enums.AgeCategory;
import library.enums.Category;
import library.models.Book;
import library.services.rankingServices.*;
import library.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RankingController {
    private AgeCategoryService ageCategoryService;
    private CategoryRankingService categoryRankingService;
    private AuthorRankingService authorRankingService;
    private BookRankingService bookRankingService;
    private UserRankingService userRankingService;

    @Autowired
    public RankingController(AgeCategoryService ageCategoryService, CategoryRankingService categoryRankingService, AuthorRankingService authorRankingService,
                             BookRankingService bookRankingService, UserRankingService userRankingService) {
        this.ageCategoryService = ageCategoryService;
        this.categoryRankingService = categoryRankingService;
        this.authorRankingService = authorRankingService;
        this.bookRankingService = bookRankingService;
        this.userRankingService = userRankingService;
    }

    @RequestMapping("/ageCategoryBookRanking")
    public String ageCategoryBookRanking(@RequestParam AgeCategory ageCategory, ModelMap modelMap) {
        List<Book> topLoanedBooksByAgeCategory = ageCategoryService.topLoanedBooksByAgeCategory(ageCategory);
        modelMap.put("topLoanedBooks", topLoanedBooksByAgeCategory);
        return "ranking";
    }

    @RequestMapping("/categoryBookRanking")
    public String categoryBookRanking(@RequestParam Category category, ModelMap modelMap) {
        List<Book> topLoanedBooksByCategory = categoryRankingService.topLoanedBooksByCategory(category);
        modelMap.put("topLoanedBooksByCategory", topLoanedBooksByCategory);
        return "ranking";
    }

    @RequestMapping("/topLoanedBooksBy")
    public String topLoanedBooks(ModelMap modelMap) {
        List<Book> topLoanedBooks = bookRankingService.topLoanedBooks();
        modelMap.put("topLoanedBooks", topLoanedBooks);
        return "ranking";
    }

    @RequestMapping("/topUsersByLoansQuantity")
    public String topUsersByLoansQuantity(ModelMap modelMap) {
        List<User> topUsersByLoansQuantity = userRankingService.topUsersByLoansQuantity();
        modelMap.put("topUsersByLoansQuantity", topUsersByLoansQuantity);
        return "ranking";
    }

    @RequestMapping("/topUsersBySumOfBooksPages")
    public String topUsersBySumOfBooksPages(ModelMap modelMap) {
        List<User> topUsersBySumOfBooksPages = userRankingService.topUsersBySumOfBooksPages();
        modelMap.put("topUsersBySumOfBooksPages", topUsersBySumOfBooksPages);
        return "ranking";
    }
}
