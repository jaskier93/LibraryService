package library.repositories;

import library.users.User;
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

    @Query("select u " +
            "   from User u " +
            "where u.id = :userId")
    User findUserById(@Param("userId") Integer userId);

    @Query("select u " +
            "   from User u " +
            "where u.dateOfBirth <: date")
    List<User> findAdultUsers(@Param("date") LocalDate localDate);


    //TODO: dwie metody poniżej do poprawy!

    @Query("select u " +
            "   from User u " +
            "inner join Action a " +
            "on a.user.id = u.id  " +
            "inner join BookState bs " +
            "on bs.action.id = a.id " +
            "where bs.bookStateEnum = 'WYPOZYCZONA' " +
            "   and a.statusRekordu='ACTIVE'" +
            "order by count (a.id) desc , u.lastName asc")
    List<User> topUsersByLoansQuantity();

    @Query("select u " +
            "   from User u " +
            "inner join Action a " +
            "on a.user.id = u.id  " +
            "inner join BookState bs " +
            "on bs.action.id = a.id " +
            "inner join Book b " +
            "on bs.book.id = b.id " +
            "where bs.bookStateEnum = 'WYPOZYCZONA' " +
            "   and a.statusRekordu='ACTIVE'" +
            "order by sum (b.pages) desc, u.lastName asc")
    List<User> topUsersBySumOfBooksPages();
}

