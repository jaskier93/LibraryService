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
     *///TODO:póki co nie działa
    @Query("select bs " +
            "from BookState bs " +
            "where bs.book.id = :bookId " +
            "and bs.dateOfLoan = (select max(bs.dateOfLoan) " +
            "from BookState bs " +
            "where bs.book.id = :bookId ) " +
            "and  bs.dateOfReturn= " +
            "(select max (bs.dateOfReturn) from BookState bs where bs.book=:bookId) " +
            "and bs.bookStateEnum='ZWROCONA'")
    BookState findBookStateByBook(@Param("bookId") Integer bookId); //czy ta metoda napewno wybiera najnowszego bookstate'a?

    //TODO: dopracować querry, obecnie zwraca więcej niż 1 bs, dodać jakiś warunek
    @Query("select distinct bs from BookState bs where bs.book.id = :bookId and bs.dateOfLoan =" +
            "(select distinct max (bs.dateOfLoan) from BookState bs where bs.book.id = :bookId)")
    BookState findBookStateByBook2(@Param("bookId") Integer bookId);


    //metoda zwraca listę aktualnych wypożyczonych książek użytkownika
    @Query("select distinct bs.book from BookState bs where bs.user=?1 and bs.bookStateEnum='WYPOZYCZONA' order by bs.dateOfLoan")
    List<Book> findLoanedBooksByUser(User user);

    //metoda zwraca aktualną listę wypożyczeń użytkownika
    @Query("select bs from BookState bs where bs.user=?1 and bs.bookStateEnum='WYPOZYCZONA' order by bs.dateOfLoan")
    List<BookState> findCurrentBookStateByUser(User user);

    //metoda zwraca listę wypożyczeń użytkownika-całą historię
    //TODO:ewentualnie można zwracać książki i w takiej formie pokazać userowi jego listę wypożyczonych książek (cała historia)
    @Query("select bs from BookState bs where bs.user=?1 order by bs.dateOfLoan")
    List<BookState> findBookStateByUser(User user);
}