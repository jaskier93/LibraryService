package library.repositories;

import library.enums.ActionDescription;
import library.models.Action;
import library.models.Book;
import library.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionRepository extends JpaRepository<Action, Integer> {

    //metoda zwraca listę tego samego typu akcji  po jego opisie-np zwrotów książek, wypożyczeń, zniszczeń etc, dodać ewentualnie jakiś warunek
    @Query("select a from Action  a where  a.actionDescription =:actionDescription")
    List<Action> findActionByActionDescription(@Param("actionDescription") ActionDescription actionDescription);

    ///metoda zwracająca listę zniszczeń jednego użytkownika
    @Query("select a from Action  a where  a.actionDescription='ZNISZCZENIE' and a.user=:value")
    List<Action> findActionsWithDestroyedBooksByUser(@Param("value") User user);

    //lista przeterminowanych zwrotów jednego użytkownika
    @Query("select a from Action a where a.actionDescription='PRZETERMINOWANIE' and a.user=:value")
    List<Action> findActionsWithOverdueReturnsByUser(@Param("value") User user);

    //zwraca listę akcji danej książki
    @Query("select a from Action a where a.book=?1")
    List<Action> findActionByBook(Book book);

    //zwraca listę akcji u danego użytkownika
    @Query("select a from Action  a where  a.user=?1")
    List<Action> findActionByUser(User user);
}
