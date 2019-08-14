package library.converters;

import com.google.gson.Gson;
import library.models.Author;
import library.models.Book;
import library.models.User;
import library.repositories.AuthorRepository;
import library.repositories.BookRepository;
import library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JsonConverter {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public JsonConverter(BookRepository bookRepository, UserRepository userRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.authorRepository = authorRepository;
    }

    private Gson gson = new Gson();

    public ActionJson convertJsonToAction(String json) {
        Integer bookId = gson.fromJson(json, ActionJson.class).getBookId();
        Integer userId = gson.fromJson(json, ActionJson.class).getUserId();
        Book book = bookRepository.getOne(bookId);
        User user = userRepository.getOne(userId);
        if (json.equals("") || userId == null || bookId == null) {
            throw new NullPointerException("Nie odnaleziono akcji");
        } else if (book.equals(null) || user.equals(null))
            throw new NullPointerException("Odnaleziony obiekt (użytkownik lub książka) jest uszkodzony");
        return gson.fromJson(json, ActionJson.class);
    }

    public Book converJsonToBook(String json) {
        Integer bookId = gson.fromJson(json, SingleIdJson.class).getId();
        Book book = bookRepository.getOne(bookId);
        if (json.equals("") || bookId == null) {
            throw new NullPointerException("Nie odnaleziono książki");
        } else if (book == null) {
            throw new NullPointerException("Odnaleziony obiekt (książka) jest uszkodzony");
        }
        return book;
    }

    public User converJsonToUser(String json) {
        Integer userId = gson.fromJson(json, SingleIdJson.class).getId();
        User user = userRepository.getOne(userId);
        if (json.equals("") || userId == null) {
            throw new NullPointerException("Nie odnaleziono użytkownika");
        } else if (user.equals(null)) {
            throw new NullPointerException("Odnaleziony obiekt (użytkownik) jest uszkodzony");
        }
        return user;
    }

    public Author converJsonToAuthor(String json) {
        Integer userId = gson.fromJson(json, SingleIdJson.class).getId();
        Author author = authorRepository.getOne(userId);
        if (json.equals("") || userId == null) {
            throw new NullPointerException("Nie odnaleziono autora");
        } else if (author.equals(null)) {
            throw new NullPointerException("Odnaleziony obiekt (autor) jest uszkodzony");
        }
        return author;
    }
}
