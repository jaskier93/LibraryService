package library.repositories;

import library.models.Payment;

import library.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {


    /* metoda zwraca listę płatnośći powyżej 10zł
    TODO: zamiast 10 umieścić zmienną, w razie potrzeby zmiany wartośći */

    @Query("select p from Payment  p where p.amount > :value")
    List<Payment> findPaymentsAboveAmount(@Param("value") Integer amount);

    //metoda zwraca listę płatności danego użytkownika
    @Query("select p from Payment  p where p.user = ?1")
    List<Payment> findByUser(User user);

    /**
     * wyliczenie sumy płatności dla jednego użytkownika (niezapłaconych-p.active=true lub 1
     * przy próbie dodania warunku p.active=1 (lub true) wyskakuje błąd z BookService i problem utworzenia beana)
     * TODO: ta metoda nie działa prawidłowo!
     */
    @Query("select sum(p.amount) from Payment p where p.user.id = :userid")
    Integer sumPaymentsForOneUser(@Param("userid") Integer userId);

}
