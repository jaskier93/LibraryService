package library.services;

import library.models.Author;
import library.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author addAuthor(Author author) {
        author.setLastName(author.getLastName());
        author.setCreated(author.getCreated());
        author.setDateOfBirth(author.getDateOfBirth());
        author.setDateOfDeath(author.getDateOfDeath());
        author.setName(author.getName());
        author.setStatus(author.getStatus());
        author.setSecondName(author.getSecondName());  //opcjonalne
        return authorRepository.save(author);
    }

    public Author updateAuthor(Author author) {
        author.setLastName(author.getLastName());
        author.setCreated(author.getCreated());
        author.setDateOfBirth(author.getDateOfBirth());
        author.setDateOfDeath(author.getDateOfDeath());
        author.setName(author.getName());
        author.setStatus(author.getStatus());
        author.setSecondName(author.getSecondName());  //opcjonalne
        return authorRepository.save(author);
    }
}
