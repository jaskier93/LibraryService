package library.services.modelservices;

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

    private static final BookStateRepository bookStateRepository = null;
    private static final ActionRepository actionRepository = null;
    private static final PaymentRepository paymentRepository = null;

    private LibraryCard showLibraryCard(User user) {
        LibraryCard libraryCard = new LibraryCard();
        libraryCard.setLoanedBooks(bookStateRepository.findLoanedBooksByUser(user).size());
        libraryCard.setAllPaymentsQuantity(paymentRepository.findPaymentsByUser(user).size());
        libraryCard.setAllPaymentsSum(paymentRepository.sumPaymentsForOneUser(user.getId()));
        libraryCard.setDestroyedBooks(actionRepository.findActionsWithDestroyedBooksByUser(user).size());
        libraryCard.setExpiredReturnsOfBooks(actionRepository.findActionsWithOverdueReturnsByUser(user).size());
        //TODO; unpaidPaymentsQuantity/unpaidPaymentsSum -trzeba dopracować metody w repozytorium Payments,
        // problemem jest wyszukiwanie aktywnych płatności (boolean isActive=true)
        // zwykle w repo wyskakuje błąd ze składnią SQL/problem z beanem w zupełnie innym miejscu
        // można zamiast metody w repozytorium wyszukiwać wszystkie, a później w serwisie wybierać tylko ze statusem aktywnym
        return libraryCard;
    }
}
