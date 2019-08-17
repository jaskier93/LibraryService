package library.repositories;

import library.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u" +
            "   from User u " +
            "where u.lastName = ?1")
    List<User> findUserByLastName(String lastName);

    @Query("select u" +
            "   from User u " +
            "where u.login = ?1")
    User findUserByLogin(String login);

    @Query("select u " +
            "   from User u " +
            "where u.id = :userId")
    User findUserById(@Param("userId") Integer userId);

    @Query("select u " +
            "   from User u " +
            "where u.dateOfBirth <: date")
    List<User> findAdultUsers(@Param("date") LocalDate localDate);


    @Query("select u " +
            "   from User u " +
            "inner join Action a on a.user = u.id " +
            "where a.actionDescription = 'WYPOZYCZENIE'" +
            "   and a.statusRekordu='ACTIVE'" +
            "group by u  " +
            "order by count (a.actionDescription) desc, u.lastName, u.name, u.id ")
    List<User> topUsersByLoansQuantity();

    @Query("select u " +
            "   from User u " +
            "inner join Action a on a.user.id = u.id  " +
            "inner join Book b on b.id = a.book " +
            "where a.actionDescription = 'WYPOZYCZENIE'" +
            "   and a.statusRekordu='ACTIVE'" +
            "group by u  " +
            "order by sum (b.pages) desc , u.lastName, u.name, u.id")
    List<User> topUsersBySumOfBooksPages();
}

