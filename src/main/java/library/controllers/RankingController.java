package library.controllers;

import library.enums.AgeCategory;
import library.enums.Category;
import library.models.Author;
import library.models.Book;
import library.services.rankingServices.*;
import library.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/ranking")
public class RankingController {

    private final AgeCategoryService ageCategoryService;
    private final CategoryRankingService categoryRankingService;
    private final AuthorRankingService authorRankingService;
    private final BookRankingService bookRankingService;
    private final UserRankingService userRankingService;

    @Autowired
    public RankingController(AgeCategoryService ageCategoryService, CategoryRankingService categoryRankingService, AuthorRankingService authorRankingService,
                             BookRankingService bookRankingService, UserRankingService userRankingService) {
        this.ageCategoryService = ageCategoryService;
        this.categoryRankingService = categoryRankingService;
        this.authorRankingService = authorRankingService;
        this.bookRankingService = bookRankingService;
        this.userRankingService = userRankingService;
    }

    @RequestMapping("/ageCategoryBooks")
    public String ageCategoryBookRanking(@RequestParam AgeCategory ageCategory, ModelMap modelMap) {
        List<Book> topLoanedBooksByAgeCategory = ageCategoryService.topLoanedBooksByAgeCategory(ageCategory);
        modelMap.put("topLoanedBooks", topLoanedBooksByAgeCategory);
        return "ranking";
    }

    @RequestMapping("/categoryBooks")
    public String categoryBookRanking(@RequestParam Category category, ModelMap modelMap) {
        List<Book> topLoanedBooksByCategory = categoryRankingService.topLoanedBooksByCategory(category);
        modelMap.put("topLoanedBooksByCategory", topLoanedBooksByCategory);
        return "ranking";
    }

    @RequestMapping("/topLoanedBooks")
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

    @RequestMapping("/topAuthorsByLoansQuantity")
    public String topAuthorsByLoansQuantity(ModelMap modelMap) {
        List<Author> topAuthorsByLoansQuantity = authorRankingService.topAuthorsByLoansQuantity();
        modelMap.put("topAuthorsByLoansQuantity", topAuthorsByLoansQuantity);
        return "ranking";
    }

    @RequestMapping("/topCategoryAuthorsByLoansQuantity")
    public String topCategoryAuthorsByLoansQuantity(@RequestParam Category category, ModelMap modelMap) {
        List<Author> topCategoryAuthorsByLoansQuantity = authorRankingService.topCategoryAuthorsByLoansQuantity(category);
        modelMap.put("topCategoryAuthorsByLoansQuantity", topCategoryAuthorsByLoansQuantity);
        return "ranking";
    }

    @RequestMapping("/topAgeCategoryAuthorsByLoansQuantity")
    public String topAgeCategoryAuthorsByLoansQuantity(@RequestParam AgeCategory ageCategory, ModelMap modelMap) {
        List<Author> topAgeCategoryAuthorsByLoansQuantity = authorRankingService.topAgeCategoryAuthorsByLoansQuantity(ageCategory);
        modelMap.put("topAgeCategoryAuthorsByLoansQuantity", topAgeCategoryAuthorsByLoansQuantity);
        return "ranking";
    }
}
