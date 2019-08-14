package library.controllers.MainServiceControllers;

import library.services.ReturnBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/returnbook")
public class ReturnBookController {

    private final ReturnBookService returnBookService;

    @Autowired
    public ReturnBookController(ReturnBookService returnBookService) {
        this.returnBookService = returnBookService;
    }

    @RequestMapping("/")
    public String returnBookService(@RequestBody String json) {
        returnBookService.mainAction(json);
        return "returnbook";
    }

    @RequestMapping("/cancel")
    public String cancelreturnBook(@RequestBody String json) {
        returnBookService.cancelAction(json);
        return "returnbook";
    }
}
