package library.controllers.MainServiceControllers;

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

    @RequestMapping("/")
    public String rentBook(@RequestBody String json) {
        rentService.mainAction(json);
        return "rentbook";
    }

    @RequestMapping("/cancel")
    public String cancelRentBook(@RequestBody String json) {
        rentService.cancelAction(json);
        return "rentbook";
    }
}
