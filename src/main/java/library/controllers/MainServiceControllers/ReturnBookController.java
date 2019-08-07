package library.controllers.MainServiceControllers;

import library.converters.JsonConverter;
import library.services.ReturnBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/returnbook")
public class ReturnBookController {

    private final ReturnBookService returnBookService;

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
