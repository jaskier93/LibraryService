package library.services;

import library.enums.BookStateEnum;
import library.models.Book;
import library.models.BookState;
import library.repositories.BookRepository;
import library.repositories.BookStateRepository;
import library.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class RentService {

    private BookRepository bookRepository;
    private BookStateRepository bookStateRepository;

    @Autowired
    public RentService(BookRepository bookRepository, BookStateRepository bookStateRepository) {
        this.bookRepository = bookRepository;
        this.bookStateRepository = bookStateRepository;
    }

    //warunek sprawdzający, czy książka ma status nowa/zwrócona-czy można ją wypożyczyć
    private boolean isBookAbleToLoan(Book book) {
        BookState bookState2 = isBookExisting(book.getId());
        if ((bookState2.getBookStateEnum() == BookStateEnum.ZWRÓCONA) ||
                ((bookState2.getBookStateEnum() == BookStateEnum.NOWA))) {
            System.out.println("Możesz wypożyczyć tą książkę");
        }
        System.out.println("Podana książka jest wypożczyona lub zniszczona, nie możesz jej wypożyczyć. ");
        return false;
    }

    //warunek sprawdzający, czy książka istnieje (jest w bibliotece)
    private BookState isBookExisting(Integer bookId) {
        BookState bookState = bookStateRepository.findBookStateByBook(bookId);
        if (bookState == null) {
            System.out.println("Nie znalezionego książki o podanym ID");
        }
        return bookState;
    }

    //wypożyczanie książki
    public void rentBook(Book book, User user) {
        BookState bookState3 = new BookState();
        bookState3.setBook(book);
        bookState3.setDateOfLoan(LocalDate.now());
        bookState3.setBookStateEnum(BookStateEnum.WYPOŻYCZONA);
        /*bookState3.setAction(?);*   czy ustawiać coś w akcji? jeśli tak, to co?*/
//tutaj powinno być jeszcze setUser, aby do użytkownika przypisać dane wypożyczenie

    }

    public void returnBook(Book book, User user /*czy tutaj user będzie potrzebny?*/) {
        BookState bookState4 = new BookState();
        bookState4.setBook(book);
        bookState4.setDateOfReturn(LocalDate.now());
        //zrobić walidacje zwrotu książki, sprawdzić, czy użytkownik oddał w terminie 30 dni
        // jeśli nie, to naliczyć karę
        bookState4.setBookStateEnum(BookStateEnum.ZWRÓCONA);
        /*bookState3.setAction(?);*   czy ustawiać coś w akcji? jeśli tak, to co?*/
//tutaj powinno być jeszcze setUser, aby do użytkownika przypisać dane wypożyczenie

    }


}
