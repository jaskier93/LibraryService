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
    @Query("select distinct bs.book from BookState bs where bs.book.ageCategory =:ageCategory " +
            "and (bs.bookStateEnum = 'NOWA' or bs.bookStateEnum = 'ZWROCONA')")
    List<Book> findBookByAgeCategory(@Param("ageCategory") AgeCategory ageCategory);

    //zwraca listę książek gotowych do wypożyczenia w danej kategorii
    @Query("select distinct bs.book from BookState bs where bs.book.category = :Category " +
            "and ( bs.bookStateEnum = 'NOWA' or bs.bookStateEnum = 'ZWROCONA')")
    List<Book> findBookByCategory(@Param("Category") Category Category);

    //metoda zwraca listę aktualnych wypożyczonych książek użytkownika
    @Query("select distinct bs.book from BookState bs where bs.user =?1  and bs.bookStateEnum='WYPOZYCZONA' order by bs.dateOfLoan desc ")
    List<Book> findLoanedBooksByUser(User user);

    //metoda zwraca aktualną listę wypożyczeń użytkownika
    @Query("select bs from BookState bs where bs.user =?1 and bs.bookStateEnum='WYPOZYCZONA' order by bs.dateOfLoan desc ")
    List<BookState> findCurrentBookStateByUser(User user);

    //metoda zwraca listę wypożyczeń użytkownika-całą historię
    @Query("select bs from BookState bs where bs.user =?1 order by bs.dateOfLoan desc ")
    List<BookState> findBookStateByUser(User user);
}