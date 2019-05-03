package library.repositories;

import library.models.Action;
import library.models.Book;
import library.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionRepository extends JpaRepository<Action, Integer> {

    //TODO: w klasie Action można pomyśleć nad dorobieniem Enuma na podobieństwo BSEnum: zniszczenie, oddanie, wypożyczenie książki itd


    //metoda zwraca listę tego samego typu akcji  po jego opisie-np zwrotów książek, wypożyczeń, zniszczeń etc
    @Query("select a from Action  a where  a.actionDescription=?1")
    List<Action> findActionByActionDescription(String description);

    //zwraca listę akcji danej książki
    @Query("select a from Action  a where  a.book=?1")
    List<Action> findActionByBook(Book book);

    //zwraca listę akcji u danego użytkownika
    @Query("select a from Action  a where  a.user=?1")
    List<Action> findActionByUser(User user);


}
