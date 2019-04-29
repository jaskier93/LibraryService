package library.repositories;


import library.models.BookState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookStateRepository extends JpaRepository<BookState, Integer> {


    @Query("select bs " +
            "from BookState bs " +
            "where bs.book.id = :bookId " +
            "and bs.dateOfLoan = (select max(bs2.dateOfLoan) " +
            "                   from BookState bs2 " +
            "                   where bs2.book.id = :bookId )")
    BookState findBookStateByBook(@Param("bookId") Integer bookId);

}



