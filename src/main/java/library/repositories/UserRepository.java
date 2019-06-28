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

   /* @Query("select u " +
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
    List<User> topUsersBySumOfBooksPages();*/

/*
    select count(a.action_description) liczba_wypozyczen, user_id
    from actions as a where a.action_description = 'WYPOZYCZENIE' and a.status_rekordu='TEST'
    group by a.user_id
    order by  liczba_wypozyczen desc , a.user_id*/

    //TODO:do sprawdzenia, dodać sortowanie po nazwisku (asc) i liczbie wypożyczeń (desc)
    @Query("select u from User u " +
            "inner join Action a on a.user = u.id " +
            "where a.actionDescription = 'WYPOZYCZENIE'"+
            "   and a.statusRekordu='ACTIVE'" +
            "group by u  " +
            "order by count (a.actionDescription) desc, u.lastName, u.name, u.id ")
    List<User> topUsersByLoansQuantity();



}

