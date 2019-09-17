package library.controllers.ModelControllers;

import library.converters.JsonConverter;
import library.models.Author;
import library.services.modelservices.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/author")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AuthorController {

    private final AuthorService authorService;
    private final JsonConverter jsonConverter;

    @Autowired
    public AuthorController(AuthorService authorService, JsonConverter jsonConverter) {
        this.authorService = authorService;
        this.jsonConverter = jsonConverter;
    }

    @RequestMapping("/add")
    public String addAuthor(@RequestBody Author author) {
        authorService.addAuthor(author);
        return "author";
    }

    @RequestMapping("/update")
    public String updateAuthor(@RequestBody Author author, String json) {
        Integer authorId = jsonConverter.converJsonToAuthor(json).getId();
        authorService.updateAuthor(authorId, author);
        return "author";
    }

    //ewentualnie w razie potrzeby można utworzyć nowy ActionDescription - usunięcie autora, a w parametrze dodawać login bibliotekarza
    @RequestMapping("/delete")
    public String deleteteAuthor(@RequestBody String json) {
        Integer authorId = jsonConverter.converJsonToAuthor(json).getId();
        authorService.deleteAuthor(authorId);
        return "author";
    }


}
