package library.repositories;

import library.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Query("select a " +
            "   from Author a " +
            "where a.lastName =?1")
    List<Author> findAuthorsByLastName(String lastName);
}
