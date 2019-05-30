package library.repositories;

import library.enums.AgeCategory;
import library.enums.Category;
import library.models.Book;
import library.models.BookState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {


    @Query("select b from Book b where b.title=?1")
    List<Book> findBookByTitle(String title);


    //czy parametr powinien być typu String czy Category?
    @Query("select b from Book b where b.category =?1 order by b.title")
    List<Book> findBookByCategory(@Param("category") Category category);

    //czy parametr powinien być typu String czy AgeCategory?
    @Query("select b from Book b where b.ageCategory =?1 order by b.ageCategory")
    List<Book> findBookByAgeCategory(@Param("ageCategory") AgeCategory ageCategory);

    /**
     * wyświetlanie książek w kolejności dodania do biblioteki
     * można dodać podobną metodę z zastrzeżeniem okresu, np wyświetlanie nowości z okresu miesiąca
     */
    @Query("select b from Book b order by b.created")
    List<Book> sortedBooksByAddingData();

    //lista książek wydana w danym okresie, np w przeciągu roku
    @Query("select b from Book b where b.releaseDate>:date order by b.title")
    List<Book> booksAddedInPeriod(@Param("date") LocalDate localDate);

    //lista książek dodana w danym okresie, np w przeciągu miesiąca
    @Query("select b from Book b where b.created>:date order by b.title ")
    List<Book> booksReleasedInPeriod(@Param("date") LocalDateTime localDateTime);

    /**
     * wyświetlanie książek w kolejności wydania
     * można dodać podobną metodę z zastrzeżeniem okresu, np wyświetlanie książek wydanych w danym roku
     */
    @Query("select b from Book b order by b.releaseDate desc")
    List<Book> sortedBooksByReleaseData();


/*    @Query("select b from Author a " +
            "inner join Book b on a.book.id=b.id where a.lastName=:author")
        //sprawdzić czy działa
    List<Book> findBookByAuthor(@Param("author") String author);*/

}
