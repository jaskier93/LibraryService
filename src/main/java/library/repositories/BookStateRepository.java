package library.repositories;

import library.enums.ActionDescription;
import library.models.Book;
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
            "   and bs.dateFrom = " +
            "(select max(bs.dateFrom) " +
            "   from BookState bs " +
            "where bs.book.id = :bookId ) " +
            "   and  bs.dateTo= " +
            "(select max (bs.dateTo) " +
            "   from BookState bs " +
            "where bs.book = :bookId)")
    BookState findBookStateByBook(@Param("bookId") Integer bookId); //czy ta metoda napewno wybiera najnowszego bookstate'a?

    //TODO: dopracować querry, obecnie zwraca więcej niż 1 bs, dodać jakiś warunek
    @Query("select bs " +
            "   from BookState bs " +
            "where bs.book.id = :bookId " +
            "   and bs.dateTo =" +
            "(select max (bs.dateTo) " +
            "   from BookState bs " +
            "where bs.book.id = :bookId)")
    BookState findBookStateByBook2(@Param("bookId") Integer bookId);

    /**
     * metoda zwraca aktualną listę wypożyczeń użytkownika
     * TODO: metoda zwraca listę wypożyczeń (nawet w przypadku oddanych książęk)-
     * trzeba dopisać warunek, którzy wyklucza oddane książki, tak żeby pokazywało tylko aktualnie wypożyczone (niezwrócone)
     *
     * @param user
     * @return
     */
    @Query("select bs " +
            "   from BookState bs " +
            "inner join Action a on bs.action.id = a.id  " +
            "   and a.user= :user " +
            "where bs.bookStateEnum= 'WYPOZYCZONA' " +
            "order by bs.dateFrom desc ")
    List<BookState> findCurrentBookStateByUser(@Param("user") User user);

    /**
     * metoda zwraca listę wypożyczeń użytkownika-całą historię
     *
     * @param user
     * @return
     */
    @Query("select bs " +
            "   from BookState bs " +
            "inner join Action a on bs.action.id = a.id  " +
            "where a.user= :user " +
            "order by bs.dateFrom desc ")
    List<BookState> findBookStateByUser(@Param("user") User user);

    /**
     * zwraca listę wypożyczeń u danego użytkownika posortowaną od najnowszej (po dacie stworzenia)
     *
     * @param user
     * @return
     */
    @Query("select bs " +
            "   from BookState bs " +
            "inner join Action a on bs.action.id = a.id " +
            "where a.user=?1 " +
            "   and a.actionDescription=?1 " +
            "order by  bs.created desc ")
    List<BookState> findNewestBookState(User user, ActionDescription actionDescription);

    @Query("select bs " +
            "   from BookState bs" +
            "   where bs.book = :givenBook " +
            "   and dateTo > sysdate")
    List<BookState> findApplicableBookState(@Param("givenBook") Book book);

}