package library.services.modelservices;

import library.enums.ActionDescription;
import library.enums.StatusRekordu;
import library.models.Action;
import library.models.Book;
import library.repositories.ActionRepository;
import library.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActionService {

    private final ActionRepository actionRepository;

    /**
     * Tworzenie akcji sparametryzowanej
     *
     * @param book
     * @param user
     * @param actionDescription
     * @return
     */
    public Action createAction(Book book, User user, ActionDescription actionDescription) {
        Action action = new Action();
        action.setUser(user);
        action.setBook(book);
        action.setCreated(LocalDateTime.now());
        action.setStatusRekordu(StatusRekordu.ACTIVE);
        action.setActionDescription(actionDescription);
        return actionRepository.save(action);
    }

    public Action addBook(Book book, User user) {
        Action action = createAction(book, user, ActionDescription.NOWOSC);
        return actionRepository.save(action);
    }

    public Action prolongation(Book book, User user) {
        Action action = createAction(book, user, ActionDescription.PRZEDLUZENIE);
        return actionRepository.save(action);
    }

    public Action returnBook(Book book, User user) {
        Action action = createAction(book, user, ActionDescription.ZWROT);
        return actionRepository.save(action);
    }

    public Action updateBook(Book book, User user) {
        Action action = createAction(book, user, ActionDescription.AKTUALIZACJA);
        action.setUpdated(LocalDateTime.now());
        return actionRepository.save(action);
    }

    public Action paymentInfo(Book book, User user) {
        Action action = createAction(book, user, ActionDescription.ZAPLACENIE);
        action.setUpdated(LocalDateTime.now());
        return actionRepository.save(action);
    }

    public Action loanBook(Book book, User user) {
        Action action = createAction(book, user, ActionDescription.WYPOZYCZENIE);
        return actionRepository.save(action);
    }

    public Action expiredLoan(Book book, User user) {
        Action action = createAction(book, user, ActionDescription.PRZETERMINOWANIE);
        return actionRepository.save(action);
    }

    public Action destroyBook(Book book, User user) {
        Action action = createAction(book, user, ActionDescription.ZNISZCZENIE);
        return actionRepository.save(action);
    }

    private Action updateAction(Action action, Integer actionId) {
        Action actionFromBase = actionRepository.getOne(actionId);
        return actionRepository.save(action);
    }
}
