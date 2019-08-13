package library.services;

import library.models.LibraryCard;
import library.repositories.ActionRepository;
import library.repositories.PaymentRepository;
import library.services.modelservices.BookService;
import library.services.modelservices.PaymentService;
import library.models.User;
import lombok.Builder;
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
    private final BookService bookService;
    private final PaymentService paymentService;

    public LibraryCard showLibraryCard(User user) {
        return LibraryCard.builder().
                loanedBooks(bookService.numberOfLoanedBooks(user)).
                allPaymentsQuantity(paymentRepository.findPaymentsByUser(user).size()).
                allPaymentsSum(paymentService.sumPaymentsForOneUser(user)).
                destroyedBooks(actionRepository.findActionsWithDestroyedBooksByUser(user).size()).
                expiredReturnsOfBooks(actionRepository.findActionsWithOverdueReturnsByUser(user).size()).
                unpaidPaymentsQuantity(paymentRepository.findActivePaymentsByUser(user).size()).
                unpaidPaymentsSum(paymentService.sumActivePaymentsForOneUser(user)).
                sumAllBooksPages(bookService.sumPagesForUser(user)).
                latestLoanedBook(bookService.latestLoanedBook(user)).
                build();
    }
}
