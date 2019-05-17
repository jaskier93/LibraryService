package library.services;

import library.models.Author;
import library.repositories.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthorService {

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(Author author) {

        if (authorRepository.findAuthorsByLastName(author.getLastName()).isEmpty()) {
            log.info("Nie odnaleziono takie autora");
        } else {
            authorRepository.findAuthorsByLastName(author.getLastName());
            author.setLastName(author.getLastName());
            author.setCreated(author.getCreated());
            author.setDateOfBirth(author.getDateOfBirth());
            author.setDateOfDeath(author.getDateOfDeath());
            author.setName(author.getName());
            author.setStatus(author.getStatus());
            author.setSecondName(author.getSecondName());
            authorRepository.save(author);
        }
        return author;
    }
}
