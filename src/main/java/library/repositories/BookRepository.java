package library.repositories;

import library.enums.AgeCategory;
import library.enums.Category;
import library.models.Book;
import library.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {


    @Query("select b " +
            "from Book b " +
            "where b.title=?1")
    List<Book> findBookByTitle(String title);


    //czy parametr powinien być typu String czy Category?
    @Query("select b from Book b " +
            "where b.category =?1 " +
            "order by b.title")
    List<Book> findAllBooksByCategory(@Param("category") Category category);

    //czy parametr powinien być typu String czy AgeCategory?
    @Query("select b " +
            "from Book b " +
            "where b.ageCategory =?1 " +
            "order by b.ageCategory")
    List<Book> findAllBooksByAgeCategory(@Param("ageCategory") AgeCategory ageCategory);


    //zwraca listę książek gotowych do wypożyczenia w danej kategorii wiekowej
    @Query("select distinct b " +
            "from BookState bs " +
            "inner join Book b on bs.book.id = b.id " +
            "and b.ageCategory = ?1 " +
            "and ( bs.bookStateEnum = 'NOWA' or bs.bookStateEnum = 'ZWROCONA')")
    List<Book> findBookByAgeCategory(@Param("ageCategory") AgeCategory ageCategory);

    //zwraca listę książek gotowych do wypożyczenia w danej kategorii
    @Query("select distinct bs.book " +
            "from BookState bs " +
            "inner join Book b on bs.book.id = b.id " +
            "and b.category = ?1 " +
            "and ( bs.bookStateEnum = 'NOWA' or bs.bookStateEnum = 'ZWROCONA')")
    List<Book> findBookByCategory(@Param("Category") Category Category);

    //metoda zwraca listę aktualnych wypożyczonych książek użytkownika
    // TODO:do dopracowania-dodać jakiś warunek, żeby sprawdzał, czy książka nie jest oddana (np brak daty zwrotu/data zwrotu=9999
    @Query("select distinct bs.book " +
            "from BookState bs " +
            "inner join Action a on bs.action.id = a.id " +
            "where a.user = ?1 and bs.bookStateEnum= 'WYPOZYCZONA' " +
            "order by bs.dateOfLoan desc ")
    List<Book> findLoanedBooksByUser(User user);

    //metoda sumuje ilość stron wszystkich książek, jak wypożyczył użytkownik
    //@Query("select sum (bs.book.pages) from BookState bs where bs.action.user= ?1 and bs.bookStateEnum = 'WYPOZYCZONA'")
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
     * można dodać podobną metodę z zastrzeżeniem okresu, np wyświetlanie nowości z okresu miesiąca
     */
    @Query("select b " +
            "from Book b " +
            "order by b.created")
    List<Book> sortedBooksByAddingData();

    //lista książek wydana w danym okresie, np w przeciągu roku
    @Query("select b " +
            "from Book b " +
            "where b.releaseDate > :date " +
            "order by b.title")
    List<Book> booksAddedInPeriod(@Param("date") LocalDate localDate);

    //lista książek dodana w danym okresie, np w przeciągu miesiąca
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



/*    @Query("select b from Author a " +
            "inner join Book b on a.book.id=b.id where a.lastName=:author")
        //sprawdzić czy działa
    List<Book> findBookByAuthor(@Param("author") String author);*/


}
