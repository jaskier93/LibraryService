package library.repositories;

import library.models.BookState;
import library.models.User;
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
            "   from BookState bs " +
            "where bs.book.id = :bookId " +
            "   and bs.dateOfLoan = " +
            "(select max(bs.dateOfLoan) " +
            "   from BookState bs " +
            "where bs.book.id = :bookId ) " +
            "   and  bs.dateOfReturn= " +
            "(select max (bs.dateOfReturn) " +
            "   from BookState bs " +
            "where bs.book = :bookId)")
    BookState findBookStateByBook(@Param("bookId") Integer bookId); //czy ta metoda napewno wybiera najnowszego bookstate'a?

    //TODO: dopracować querry, obecnie zwraca więcej niż 1 bs, dodać jakiś warunek
    @Query("select bs " +
            "   from BookState bs " +
            "where bs.book.id = :bookId " +
            "   and bs.dateOfReturn =" +
            "(select max (bs.dateOfReturn) " +
            "   from BookState bs " +
            "where bs.book.id = :bookId)")
    BookState findBookStateByBook2(@Param("bookId") Integer bookId);

    //metoda zwraca aktualną listę wypożyczeń użytkownika
    //TODO: metoda zwraca listę wypożyczeń (nawet w przypadku oddanych książęk)-
    // trzeba dopisać warunek, którzy wyklucza oddane książki, tak żeby pokazywało tylko aktualnie wypożyczone (niezwrócone)
    //@Query("select bs from BookState bs where bs.action.user =?1 and bs.bookStateEnum='WYPOZYCZONA' order by bs.dateOfLoan desc ")
    @Query("select bs " +
            "   from BookState bs " +
            "inner join Action a on bs.action.id = a.id  " +
            "   and a.user= :user " +
            "where bs.bookStateEnum= 'WYPOZYCZONA' " +
            "order by bs.dateOfLoan desc ")
    List<BookState> findCurrentBookStateByUser(@Param("user") User user);

    //metoda zwraca listę wypożyczeń użytkownika-całą historię
    @Query("select bs " +
            "   from BookState bs " +
            "inner join Action a on bs.action.id = a.id  " +
            "where a.user= :user " +
            "order by bs.dateOfLoan desc ")
    List<BookState> findBookStateByUser(@Param("user") User user);
}