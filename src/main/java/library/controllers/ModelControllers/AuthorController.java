package library.controllers.ModelControllers;

import library.converters.JsonConverter;
import library.models.Author;
import library.repositories.AuthorRepository;
import library.services.modelservices.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorRepository authorRepository;
    private final JsonConverter jsonConverter;

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
    public String deleteteAuthor(@RequestBody Author author, String json) {
        Integer authorId = jsonConverter.converJsonToAuthor(json).getId();
        authorService.deleteAuthor(authorId);
        return "author";
    }


}
