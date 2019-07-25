package library.controllers;

import library.converters.ActionJson;
import library.converters.JsonConverter;
import library.models.Book;
import library.models.User;
import library.repositories.BookRepository;
import library.repositories.UserRepository;
import library.services.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rentbook")
public class RentController {

    private final RentService rentService;
    private final JsonConverter jsonConverter;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @RequestMapping("/")
    public String rentBook(@RequestBody String json) {
        ActionJson actionJson = jsonConverter.convertJsonToActionJson(json);
        Book book = bookRepository.getOne(actionJson.getBookId());
        User user = userRepository.getOne(actionJson.getUserId());
        rentService.mainAction(user, book);
        return "rentbook";
    }

    @RequestMapping("/cancel")
    public String cancelRentBook(@RequestBody String json) {
        ActionJson actionJson = jsonConverter.convertJsonToActionJson(json);
        Book book = bookRepository.getOne(actionJson.getBookId());
        User user = userRepository.getOne(actionJson.getUserId());
        rentService.cancelAction(user, book);
        return "rentbook";
    }
}
