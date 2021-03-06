package library.repositories;

import library.enums.AgeCategory;
import library.enums.Category;
import library.models.Book;
import library.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    //TODO:dopracować
/*    @Query("select b from Author a " +
            "inner join Book b on a.book.id=b.id where a.lastName=:author")
        //sprawdzić czy działa
    List<Book> findBookByAuthor(@Param("author") String author);*/

    @Query("select b " +
            "   from Book b " +
            "where b.title=?1")
    List<Book> findBookByTitle(String title);

    @Query("select b " +
            "   from Book b " +
            "where b.category =?1 " +
            "order by b.title")
    List<Book> findAllBooksByCategory(@Param("category") Category category);

    @Query("select b " +
            "   from Book b " +
            "where b.ageCategory =?1 " +
            "order by b.title")
    List<Book> findAllBooksByAgeCategory(@Param("ageCategory") AgeCategory ageCategory);

    @Query("select distinct b " +
            "   from Book b " +
            "inner join Action a on a.book = b.id " +
            "where a.actionDescription = 'WYPOZYCZENIE'" +
            "   and a.statusRekordu='ACTIVE'" +
            "group by b  " +
            "order by count (a.actionDescription) desc, b.title, b.id ")
    List<Book> topLoanedBooks();

    @Query("select distinct b " +
            "   from Book b " +
            "inner join Action a on a.book = b.id " +
            "where a.actionDescription = 'WYPOZYCZENIE'" +
            "   and a.statusRekordu='ACTIVE'" +
            "   and b.category = ?1 " +
            "group by b  " +
            "order by count (a.actionDescription) desc, b.title, b.id ")
    List<Book> topLoanedBooksByCategory(Category category);

    @Query("select distinct b " +
            "   from Book b " +
            "inner join Action a on a.book = b.id " +
            "where a.actionDescription = 'WYPOZYCZENIE'" +
            "   and a.statusRekordu='ACTIVE'" +
            "   and b.ageCategory = ?1 " +
            "group by b  " +
            "order by count (a.actionDescription) desc, b.title, b.id ")
    List<Book> topLoanedBooksByAgeCategory(AgeCategory ageCategory);


    //zwraca listę książek gotowych do wypożyczenia w danej kategorii wiekowej
    @Query("select distinct b " +
            "   from Book b " +
            "inner join BookState bs on bs.book.id = b.id " +
            "   and b.ageCategory = :ageCategory " +
            "   and ( bs.bookStateEnum = 'NOWA' or bs.bookStateEnum = 'ZWROCONA')")
    List<Book> findBookByAgeCategory(@Param("ageCategory") AgeCategory ageCategory);

    //zwraca listę książek gotowych do wypożyczenia w danej kategorii
    @Query("select distinct b " +
            "   from Book b " +
            "inner join BookState bs on bs.book.id = b.id " +
            "   and b.category = ?1 " +
            "   and ( bs.bookStateEnum = 'NOWA' or bs.bookStateEnum = 'ZWROCONA')")
    List<Book> findBookByCategory(@Param("Category") Category Category);

    /**
     * metoda zwraca listę aktualnych wypożyczonych książek użytkownika
     * TODO:do dopracowania-dodać jakiś warunek, żeby sprawdzał, czy książka nie jest oddana (np brak daty zwrotu/data zwrotu=9999
     *
     * @param user
     * @return
     */
    @Query("select distinct bs.book " +
            "   from BookState bs " +
            "inner join Action a on bs.action.id = a.id " +
            "where a.user = ?1 and bs.bookStateEnum= 'WYPOZYCZONA' " +
            "order by bs.dateFrom desc ")
    List<Book> findLoanedBooksByUser(User user);

    /**
     * metoda sumuje ilość stron wszystkich książek, jak wypożyczył użytkownik
     */
    @Query("select sum(b.pages) " +
            "   from Book b " +
            "inner join BookState bs on bs.book.id = b.id " +
            "inner join Action a on bs.action.id = a.id " +
            "where 1 = 1 " +
            "  and a.user = ?1 " +
            "  and bs.bookStateEnum= 'WYPOZYCZONA'")
    Integer sumPagesForUser(User user);

    /**
     * wyświetlanie książek w kolejności dodania do biblioteki
     */
    @Query("select b " +
            "   from Book b " +
            "order by b.created")
    List<Book> sortedBooksByAddingData();

    /**
     * lista książek wydana w danym okresie, np w przeciągu roku
     *
     * @param localDate
     * @return
     */
    @Query("select b " +
            "   from Book b " +
            "where b.releaseDate > :date " +
            "order by b.title")
    List<Book> booksAddedInPeriod(@Param("date") LocalDate localDate);

    /**
     * lista książek dodana w danym okresie, np w przeciągu miesiąca
     *
     * @param localDateTime
     * @return
     */
    @Query("select b " +
            "   from Book b " +
            "where b.created > :date " +
            "order by b.title ")
    List<Book> booksReleasedInPeriod(@Param("date") LocalDateTime localDateTime);

    /**
     * wyświetlanie książek w kolejności wydania
     * można dodać podobną metodę z zastrzeżeniem okresu, np wyświetlanie książek wydanych w danym roku
     */
    @Query("select b " +
            "from Book b " +
            "order by b.releaseDate desc")
    List<Book> sortedBooksByReleaseData();
}
