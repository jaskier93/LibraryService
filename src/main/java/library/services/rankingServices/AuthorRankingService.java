package library.services.rankingServices;

import library.repositories.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * wyświetlanie najpopularniejszych (najczęściej wypożyczane książki danego autora) autorów
 */

@Service
@Slf4j
public class AuthorRankingService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorRankingService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    //TODO: rankingi : najwięcej wypożyczeń (wszystkich książek danego autora), największa suma stron przeczytanych przez użytkowników książek danego autora etc
}
