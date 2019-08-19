package library.controllers.MainServiceControllers;

import library.services.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rentbook")
@PreAuthorize("hasRole('ROLE_USER')")
public class RentController {

    private final RentService rentService;

    @Autowired
    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

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
