package library.repositories;

import library.enums.AgeCategory;
import library.enums.Category;
import library.models.Book;
import library.models.BookState;
import library.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookStateRepository extends JpaRepository<BookState, Integer> {

    //TODO: przy sortowaniu (order by) po dacie trzeba dodać słówko kluczowe DESC jeśli chcemy od najnowszej daty lub ASC (domyślnie) jeśli od najstarszej

    /**
     * dopisać test
     */
    @Query("select bs " +
            "from BookState bs " +
            "where bs.book.id = :bookId " +
            "and bs.dateOfLoan = (select max(bs.dateOfLoan) " +
            "from BookState bs " +
            "where bs.book.id = :bookId ) " +
            "and  bs.dateOfReturn= " +
            "(select max (bs.dateOfReturn) from BookState bs where bs.book = :bookId)")
    BookState findBookStateByBook(@Param("bookId") Integer bookId); //czy ta metoda napewno wybiera najnowszego bookstate'a?

    //TODO: dopracować querry, obecnie zwraca więcej niż 1 bs, dodać jakiś warunek
    @Query("select bs from BookState bs where bs.book.id = :bookId and bs.dateOfReturn =" +
            "(select max (bs.dateOfReturn) from BookState bs where bs.book.id = :bookId)")
    BookState findBookStateByBook2(@Param("bookId") Integer bookId);

    //zwraca listę książek gotowych do wypożyczenia w danej kategorii wiekowej
    @Query("select distinct bs.book from BookState bs inner join Book b on bs.book.id = b.id " +
            "and b.ageCategory = ?1 and ( bs.bookStateEnum = 'NOWA' or bs.bookStateEnum = 'ZWROCONA')")
    List<Book> findBookByAgeCategory(@Param("ageCategory") AgeCategory ageCategory);

    //zwraca listę książek gotowych do wypożyczenia w danej kategorii
    @Query("select distinct bs.book from BookState bs inner join Book b on bs.book.id = b.id " +
            "and b.category = ?1 and ( bs.bookStateEnum = 'NOWA' or bs.bookStateEnum = 'ZWROCONA')")
    List<Book> findBookByCategory(@Param("Category") Category Category);

    //metoda zwraca listę aktualnych wypożyczonych książek użytkownika
    // TODO:do dopracowania-dodać jakiś warunek, żeby sprawdzał, czy książka nie jest oddana (np brak daty zwrotu/data zwrotu=9999
    @Query("select distinct bs.book from BookState bs inner join Action a on bs.action.id = a.id " +
            "where a.user = ?1 and bs.bookStateEnum= 'WYPOZYCZONA' order by bs.dateOfLoan desc ")
    List<Book> findLoanedBooksByUser(User user);

    //metoda sumuje ilość stron wszystkich książek, jak wypożyczył użytkownik
    //@Query("select sum (bs.book.pages) from BookState bs where bs.action.user= ?1 and bs.bookStateEnum = 'WYPOZYCZONA'")
    @Query("select sum(b.pages) from Book b inner join BookState bs on bs.book.id = b.id " +
            "inner join Action a on bs.action.id = a.id where a.user = ?1 and bs.bookStateEnum= 'WYPOZYCZONA'")
    Integer sumPagesForUser(User user);


    //metoda zwraca aktualną listę wypożyczeń użytkownika
    //TODO: metoda zwraca listę wypożyczeń (nawet w przypadku oddanych książęk)-
    // trzeba dopisać warunek, którzy wyklucza oddane książki, tak żeby pokazywało tylko aktualnie wypożyczone (niezwrócone)
    //@Query("select bs from BookState bs where bs.action.user =?1 and bs.bookStateEnum='WYPOZYCZONA' order by bs.dateOfLoan desc ")
    @Query("select bs from BookState bs inner join Action a on bs.action.id = a.id  " +
            "  where a.user= ?1 and bs.bookStateEnum= 'WYPOZYCZONA' order by bs.dateOfLoan desc ")
    List<BookState> findCurrentBookStateByUser(User user);

    //metoda zwraca listę wypożyczeń użytkownika-całą historię
    @Query("select bs from BookState bs inner join Action a on bs.action.id = a.id  " +
            "  where a.user= ?1 order by bs.dateOfLoan desc ")
    List<BookState> findBookStateByUser(User user);
}