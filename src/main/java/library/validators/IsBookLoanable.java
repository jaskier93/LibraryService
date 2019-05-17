package library.validators;

import library.enums.BookStateEnum;
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
    private boolean isBookAbleToLoan(Book book) {
        BookState bookState = bookStateRepository.getOne(book.getId());
        if (bookState != (null)) {
            if (bookState.getBookStateEnum() == BookStateEnum.NOWA || bookState.getBookStateEnum() == BookStateEnum.ZWRÓCONA) {
                log.info("Możesz wypożyczyć tę książkę");
                return true;
            } else if (bookState.getBookStateEnum() == BookStateEnum.WYPOŻYCZONA) {
                log.info("Książka jest aktualnie wypożyczona");
                return false;
            } else {
                log.info("Książka jest zniszczona.");
                return false;
            }
        } else {
            log.info("Nie odnaleziono informacji o książce");
            return false;
        }
    }

}
