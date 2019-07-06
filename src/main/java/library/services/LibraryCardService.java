package library.services;

import library.models.LibraryCard;
import library.repositories.ActionRepository;
import library.repositories.BookRepository;
import library.repositories.PaymentRepository;
import library.services.modelservices.BookService;
import library.services.modelservices.PaymentService;
import library.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Serwis będzie pokazywał aktualną kartę biblioteczną użytkownika, a w nim informacje:
 * -ilość aktualnie wypożyczonych książek
 * -suma płatnośći, które zostały nadane użytkownikowi (za zniszczenia/przeterminowane zwroty)
 * -ilość wszystkich płatnośći
 * -ilość zniszczonych książek
 * -ilość przeterminowanych zwrotów
 * -ilość i sumę niezapłaconych płatności
 */

@Service
@RequiredArgsConstructor
public class LibraryCardService {

    private final ActionRepository actionRepository;
    private final PaymentRepository paymentRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final PaymentService paymentService;

    public LibraryCard showLibraryCard(User user) {
        LibraryCard libraryCard = new LibraryCard();
        libraryCard.setLoanedBooks(bookService.numberOfLoanedBooks(user));
        libraryCard.setAllPaymentsQuantity(paymentRepository.findPaymentsByUser(user).size());
        libraryCard.setAllPaymentsSum(paymentService.sumPaymentsForOneUser(user));
        libraryCard.setDestroyedBooks(actionRepository.findActionsWithDestroyedBooksByUser(user).size());
        libraryCard.setExpiredReturnsOfBooks(actionRepository.findActionsWithOverdueReturnsByUser(user).size());
        libraryCard.setUnpaidPaymentsQuantity(paymentRepository.findActivePaymentsByUser(user).size());
        libraryCard.setUnpaidPaymentsSum(paymentService.sumActivePaymentsForOneUser(user));
        libraryCard.setSumAllBooksPages(bookService.sumPagesForUser(user));
        libraryCard.setLatestLoanedBook(bookService.latestLoanedBook(user));
        return libraryCard;

        //TODO: dodać exceptiony (emptyList/za duży wynik-np przy ilości aktualnie wypożyczonych książek)
        /**
         * przykladowa walidacja:
         * w danym serwisie metoda abstrakcyjna odziedziczona z klasy abstrakcyjnej, zwraca liste walidacji
         * public List<AbstractValidator> abstractValidationList(){
         *  add. któryśwalidator(parametr z encji) np:
         * isWordOneString (author.lastName);}
         *
         */
    }


}
