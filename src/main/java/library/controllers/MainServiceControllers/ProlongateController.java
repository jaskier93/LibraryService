package library.controllers.MainServiceControllers;

import library.services.ProlongationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/prolongate")
@PreAuthorize("hasRole('ROLE_USER')")
public class ProlongateController {

    private final ProlongationService prolongationService;

    @Autowired
    public ProlongateController(ProlongationService prolongationService) {
        this.prolongationService = prolongationService;
    }

    @RequestMapping("/")
    public String prolongate(@RequestBody String json) {
        prolongationService.mainAction(json);
        return "prolongate";
    }

    @RequestMapping("/cancel")
    public String cancelProlongate(@RequestBody String json) {
        prolongationService.cancelAction(json);
        return "prolongate";
    }
}
