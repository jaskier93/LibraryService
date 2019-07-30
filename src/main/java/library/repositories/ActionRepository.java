package library.repositories;

import library.enums.ActionDescription;
import library.models.Action;
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
public interface ActionRepository extends JpaRepository<Action, Integer> {

    /**
     * metoda zwraca listę tego samego typu akcji  po jego opisie-np zwrotów książek, wypożyczeń, zniszczeń etc, dodać ewentualnie jakiś warunek
     */
    @Query("select a " +
            "   from Action  a " +
            "where  a.actionDescription =:actionDescription")
    List<Action> findActionByActionDescription(@Param("actionDescription") ActionDescription actionDescription);

    /**
     * metoda zwracająca listę zniszczeń jednego użytkownika
     *
     * @param user
     * @return
     */
    @Query("select a " +
            "   from Action  a " +
            "where  a.actionDescription='ZNISZCZENIE' " +
            "   and a.user =:user")
    List<Action> findActionsWithDestroyedBooksByUser(@Param("user") User user);

    /**
     * lista przeterminowanych zwrotów jednego użytkownika
     *
     * @param user
     * @return
     */
    @Query("select a " +
            "   from Action a " +
            "where a.actionDescription='PRZETERMINOWANIE' " +
            "   and a.user =:user")
    List<Action> findActionsWithOverdueReturnsByUser(@Param("user") User user);

    /**
     * zwraca listę akcji danej książki
     *
     * @param book
     * @return
     */
    @Query("select a " +
            "   from Action a " +
            "where a.book =?1")
    List<Action> findActionByBook(Book book);

    /**
     * zwraca listę akcji u danego użytkownika
     *
     * @param user
     * @return
     */
    @Query("select a " +
            "   from Action  a " +
            "where a.user=?1")
    List<Action> findActionByUser(User user);

    /**
     * zwraca listę akcji danego typu u danego użytkownika posortowaną od najnowszej (po dacie stworzenia)
     *
     * @param user
     * @return
     */
    @Query("select a " +
            "   from Action  a " +
            "where a.user=?1" +
            "   and a.actionDescription=?2 " +
            "   and a.created > ?3 " +
            "order by a.created desc ")
    List<Action> findNewestAction(User user, ActionDescription actionDescription, LocalDateTime localDateTime);
}
