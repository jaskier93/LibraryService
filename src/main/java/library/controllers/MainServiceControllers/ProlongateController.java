package library.controllers.MainServiceControllers;

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
