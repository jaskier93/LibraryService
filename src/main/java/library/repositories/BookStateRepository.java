package library.repositories;


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

    /**
     * dopisać test
     */
    @Query("select bs " +
            "from BookState bs " +
            "where bs.book.id = :bookId " +
            "and bs.dateOfLoan = (select max(bs2.dateOfLoan) " +
            "from BookState bs2 " +
            "where bs2.book.id = :bookId ) " +
            "and  bs.dateOfReturn= " +
            "(select max (bs3.dateOfReturn) from BookState bs3 where bs3.dateOfReturn=:bookId) " +
            "and bs.bookStateEnum=ZWROCONA")
    BookState findBookStateByBook(@Param("bookId") Integer bookId); //czy ta metoda napewno wybiera najnowszego bookstate'a?

    @Query("select bs.book from BookState bs where bs.user=?1")
    List<Book> findBooksByUser(User user);

    //metoda zwraca listę aktualnych wypożyczeń użytkownika
    @Query("select bs.book from BookState bs where bs.user=?1 and bs.bookStateEnum='WYPOZYCZONA'")
    List<Book> findLoanedBooksByUser(User user);
}