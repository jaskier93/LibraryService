package library.repositories;

import library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("select b from Book b where b.title=?1")
    List<Book> findBookByTitle (String title);

    @Query //dopisać querry
    List<Book> findBookByAuthor(@Param("author") String author);

    @Query //dopisać querry
    List<Book> findBookByCategory(@Param("category") String category);
}
