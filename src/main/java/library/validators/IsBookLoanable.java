package library.validators;

import library.models.Book;
import library.models.BookState;
import library.repositories.BookStateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class IsBookLoanable {
    private BookStateRepository bookStateRepository;

    @Autowired
    public IsBookLoanable(BookStateRepository bookStateRepository) {
        this.bookStateRepository = bookStateRepository;
    }

    /**
     * warunek sprawdzający, czy książka ma status nowa/zwrócona-czy można ją wypożyczyć
     */
    public boolean isBookAbleToLoan(Book book) {
        BookState bookState = bookStateRepository.findBookStateByBook(book.getId());
        boolean temp;
        switch (bookState.getBookStateEnum()) {
            case WYPOZYCZONA:
                log.info("Książka jest aktualnie wypożyczona");
                temp = false;
                break;
            case ZNISZCZONA:
                log.info("Książka jest zniszczona");
                temp = false;
                break;
            case ZWROCONA:
            case NOWA:
                log.info("Możesz wypożyczyć tę książkę");
                temp = true;
                break;
            default:
                log.info("Nie odnaleziono informacji o książce");
                temp = false;
                break;
        }
        return temp;
    }
}
