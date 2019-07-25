package library.controllers;

import library.converters.ActionJson;
import library.converters.JsonConverter;
import library.models.Book;
import library.models.User;
import library.repositories.BookRepository;
import library.repositories.UserRepository;
import library.services.ProlongationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/prolongate")
public class ProlongateController {

    private final ProlongationService prolongationService;
    private final JsonConverter jsonConverter;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @RequestMapping("/")
    public String prolongate(@RequestBody String json) {
        ActionJson actionJson = jsonConverter.convertJsonToActionJson(json);
        Book book = bookRepository.getOne(actionJson.getBookId());
        User user = userRepository.getOne(actionJson.getUserId());
        prolongationService.mainAction(user, book);
        return "prolongate";
    }

    @RequestMapping("/cancel")
    public String cancelProlongate(@RequestBody String json) {
        ActionJson actionJson = jsonConverter.convertJsonToActionJson(json);
        Book book = bookRepository.getOne(actionJson.getBookId());
        User user = userRepository.getOne(actionJson.getUserId());
        prolongationService.cancelAction(user, book);
        return "prolongate";
    }


}
