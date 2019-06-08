package library.services;

import library.models.LibraryCard;
import library.repositories.ActionRepository;
import library.repositories.BookStateRepository;
import library.repositories.PaymentRepository;
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

    private final BookStateRepository bookStateRepository;
    private final ActionRepository actionRepository;
    private final PaymentRepository paymentRepository;

    private LibraryCard showLibraryCard(User user) {
        LibraryCard libraryCard = new LibraryCard();
        libraryCard.setLoanedBooks(bookStateRepository.findLoanedBooksByUser(user).size());
        libraryCard.setAllPaymentsQuantity(paymentRepository.findPaymentsByUser(user).size());
        libraryCard.setAllPaymentsSum(paymentRepository.sumPaymentsForOneUser(user));
        libraryCard.setDestroyedBooks(actionRepository.findActionsWithDestroyedBooksByUser(user).size());
        libraryCard.setExpiredReturnsOfBooks(actionRepository.findActionsWithOverdueReturnsByUser(user).size());
        libraryCard.setUnpaidPaymentsQuantity(paymentRepository.findActivePaymentsByUser(user).size());
        libraryCard.setUnpaidPaymentsSum(paymentRepository.sumActivePaymentsForOneUser(user));
        libraryCard.setSumAllBooksPages(bookStateRepository.sumPagesForUser(user));
        return libraryCard;
    }
}
