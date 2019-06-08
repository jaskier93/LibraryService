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


    // metoda zwraca listę płatnośći powyżej danej kwoty dla jednego użytkownika
    @Query("select p from Payment  p where p.amount > :value and p.action.user= :user")
    List<Payment> findPaymentsAboveAmount(@Param("value") Integer amount, @Param("user") User user);

    //metoda zwraca listę wszystkich płatności danego użytkownika
    @Query("select p from Payment  p where p.action.user = ?1")
    List<Payment> findPaymentsByUser(User user);

    //metoda zwraca listę niezapłaconych płatności danego użytkownika
    @Query("select p from Payment  p where p.isActive = true and p.action.user = ?1")
    List<Payment> findActivePaymentsByUser(User user);


    /* wyliczenie sumy płatności dla jednego użytkownika (niezapłaconych-p.active=true lub 1
     * przy próbie dodania warunku p.active=1 (lub true) wyskakuje błąd z BookService i problem utworzenia beana)*/
    @Query("select sum(p.amount) from Payment p where p.action.user = :user")
    Integer sumPaymentsForOneUser(@Param("user") User user);

    //działa! suma aktywnych (niezapłaconych) płatności jednego użytkownika
    @Query("select sum(p.amount) from Payment p where p.isActive = true and p.action.user = :user")
    Integer sumActivePaymentsForOneUser(@Param("user") User user);


}
