package library.repositories;

import library.models.Payment;

import library.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {


    /* metoda zwraca listę płatnośći powyżej 10zł
    TODO: zamiast 10 umieścić zmienną, w razie potrzeby zmiany wartośći */
    @Query("select p from Payment  p where p.amount> 10")
    List<Payment> findByAmount(Integer amount);


    //metoda zwraca listę płatności danego użytkownika
    @Query("select p from Payment  p where p.user= ?1")
    List<Payment> findByUser(User user);

}
