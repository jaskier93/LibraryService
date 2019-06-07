package library.services;

import library.models.Book;
import library.models.Payment;
import library.services.modelservices.ActionService;
import library.services.modelservices.BookStateService;
import library.services.modelservices.PaymentService;
import library.users.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReturnBookService {

    private final ActionService actionService;
    private final BookStateService bookStateService;
    private final PaymentService paymentService;

    //czy jest sens wysyłać maila w przypadku zwrotu książki?
    public void returnBook(Book book, User user /*czy tutaj user będzie potrzebny?*/) {

        //TODO:dodać walidację daty zwrotu książki, if true-tworzymy dwie akcje, else-tworzona jedna (prawidłowy termin zwrotu)
        actionService.expiredLoan(book, user);
        actionService.returnBook(book, user);
        bookStateService.returnBook(actionService.returnBook(book, user));
        /*zrobić walidacje zwrotu książki, sprawdzić, czy użytkownik oddał w terminie 30 dni
        jeśli nie, to naliczyć karę*/
        Payment expiredLoanPayment = paymentService.expiredLoan(bookStateService.returnBook(actionService.returnBook(book, user)));
        //TODO:ewentualnie dodać walidację (jeśli potrzebna)-czy użytkownik od razu płaci karę
        actionService.paymentInfo(book, user);
        paymentService.updatePayment(expiredLoanPayment.getId());
    }
}
