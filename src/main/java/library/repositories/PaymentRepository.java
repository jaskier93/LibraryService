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
    @Query("select p from Payment  p where p.amount > :value and p.user.id= :userId")
    List<Payment> findPaymentsAboveAmount(@Param("value") Integer amount, @Param("userId") Integer userId);

    //metoda zwraca listę płatności danego użytkownika
    @Query("select p from Payment  p where p.user = ?1")
    List<Payment> findPaymentsByUser(User user);

    /* wyliczenie sumy płatności dla jednego użytkownika (niezapłaconych-p.active=true lub 1
     * przy próbie dodania warunku p.active=1 (lub true) wyskakuje błąd z BookService i problem utworzenia beana)*/
    @Query("select sum(p.amount) from Payment p where p.user.id = :userid")
    Integer sumPaymentsForOneUser(@Param("userid") Integer userId);

}
