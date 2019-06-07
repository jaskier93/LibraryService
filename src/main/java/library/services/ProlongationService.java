package library.services;

import library.models.Book;
import library.services.modelservices.ActionService;
import library.services.modelservices.BookStateService;
import library.users.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProlongationService {
    private static final Integer LOAN_PERIOD = 30;
    private final ActionService actionService;
    private final BookStateService bookStateService;

    /**
     * TODO: dorobić walidację, czy użytkownik już wcześniej przedłużał te wypożyczenie (będzie można przedłużyć tylko raz)
     * oraz czy nie próbuje przedłużyć po dacie zwrotu
     */
    public String loanBookProlongation(Book book, User user) {
        //najpierw wywołać metodę walidującą z ProlongationValidator
        actionService.prolongation(book, user);
        bookStateService.prolongation(actionService.prolongation(book, user));
        return "Przedłużyłeś wypożyczenie książki pt.\"" + book.getTitle() + "\"." +
                "Termin zwrotu książki to: " + LocalDate.now().plusDays(LOAN_PERIOD);
    }
}
