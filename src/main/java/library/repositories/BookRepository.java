package library.repositories;

import library.models.Book;
import library.models.BookState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("select b from Book b where b.title=?1")
    List<Book> findBookByTitle(String title);


/*    @Query("select b from Author a " +
            "inner join Book b on a.book.id=b.id where a.lastName=:author") //sprawdzić czy działa
    List<Book> findBookByAuthor(@Param("author") String author);

    @Query("select b from Book b where b.category =?1") //sprawdzićz czy działa
    List<Book> findBookByCategory(@Param("category") String category);

    @Query("select b from Book order by addingDate")
    List<Book> sortedBooksByAddingData();

    @Query("select b from Book order by releaseDate")
    List<Book> sortedBooksByReleaseData();
*/

}
