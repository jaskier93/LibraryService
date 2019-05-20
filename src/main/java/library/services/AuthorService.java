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

    public Author updateAuthor(Integer authorId, Author author) {
        Author authorFromBase = authorRepository.getOne(authorId);
        if (authorFromBase == null) {
            log.info("Nie odnaleziono takie autora");
        } else {
            if (!author.getLastName().isEmpty()) {
                authorFromBase.setLastName(author.getLastName());
            }
            if (!author.getCreated().isEqual(authorFromBase.getCreated())) {
                authorFromBase.setCreated(author.getCreated());
            }
            if (!author.getDateOfBirth().isEqual(authorFromBase.getDateOfBirth())) {
                authorFromBase.setDateOfBirth(author.getDateOfBirth());
            }
            if (!author.getDateOfDeath().isEqual(authorFromBase.getDateOfDeath())) {
                authorFromBase.setDateOfDeath(author.getDateOfDeath());
            }
            if (!author.getName().isEmpty()) {
                authorFromBase.setName(author.getName());
            }
            if (author.getStatus() != 0) {
                authorFromBase.setStatus(author.getStatus());
            }
            if (!author.getSecondName().isEmpty()) {
                authorFromBase.setSecondName(author.getSecondName());
            }
            authorRepository.save(authorFromBase);
        }
        return author;
    }
}
