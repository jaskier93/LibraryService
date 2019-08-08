package library.repositories;

import library.enums.AgeCategory;
import library.enums.Category;
import library.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Query("select a " +
            "   from Author a " +
            "where a.lastName =?1")
    List<Author> findAuthorsByLastName(String lastName);

    @Query("select distinct a" +
            "   from Author  a " +
            "inner join Book b on b.author = a.id " +
            "inner join Action x on x.book = b.id  " +
            "where x.actionDescription = 'WYPOZYCZENIE'" +
            "   and x.statusRekordu='ACTIVE'" +
            "group by a  " +
            "order by  count (x.actionDescription) desc , a.lastName, a.id")
    List<Author> topAuthorsByLoansQuantity();

    @Query("select distinct  a" +
            "   from Author  a " +
            "inner join Book b on b.author = a.id " +
            "inner join Action x on x.book = b.id  " +
            "where x.actionDescription = 'WYPOZYCZENIE'" +
            "   and x.statusRekordu='ACTIVE'" +
            "group by a  " +
            "order by  sum (b.pages) desc , a.lastName, a.id")
    List<Author> topAuthorsBySumOfBookPages();

    @Query("select distinct a" +
            "   from Author  a " +
            "inner join Book b on b.author = a.id " +
            "inner join Action x on x.book = b.id  " +
            "where b.ageCategory = ?1 " +
            "and x.actionDescription = 'WYPOZYCZENIE'" +
            "   and x.statusRekordu='ACTIVE'" +
            "group by a  " +
            "order by  count (x.actionDescription) desc , a.lastName, a.id")
    List<Author> topAgeCategoryAuthorsByLoansQuantity(@Param("ageCategory") AgeCategory ageCategory);

    @Query("select  a" +
            "   from Author  a " +
            "inner join Book b on b.author = a.id " +
            "inner join Action x on x.book = b.id  " +
            "where b.category = ?1 " +
            "   and x.actionDescription = 'WYPOZYCZENIE'" +
            "   and x.statusRekordu='ACTIVE'" +
            "group by a  " +
            "order by  count (x.actionDescription) desc , a.lastName, a.id")
    List<Author> topCategoryAuthorsByLoansQuantity(@Param("category") Category category);
}
