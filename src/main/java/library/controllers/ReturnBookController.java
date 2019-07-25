package library.controllers;

import library.converters.ActionJson;
import library.converters.JsonConverter;
import library.models.Book;
import library.models.User;
import library.repositories.BookRepository;
import library.repositories.UserRepository;
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
    private final JsonConverter jsonConverter;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @RequestMapping("/")
    public String returnBookService(@RequestBody String json) {
        ActionJson actionJson = jsonConverter.convertJsonToActionJson(json);
        Book book = bookRepository.getOne(actionJson.getBookId());
        User user = userRepository.getOne(actionJson.getUserId());
        returnBookService.mainAction(user, book);
        return "returnbook";
    }

    @RequestMapping("/cancel")
    public String cancelreturnBook(@RequestBody String json) {
        ActionJson actionJson = jsonConverter.convertJsonToActionJson(json);
        Book book = bookRepository.getOne(actionJson.getBookId());
        User user = userRepository.getOne(actionJson.getUserId());
        returnBookService.cancelAction(user, book);
        return "returnbook";
    }
}
