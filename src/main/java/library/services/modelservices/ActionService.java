package library.services.modelservices;

import library.enums.ActionDescription;
import library.enums.StatusRekordu;
import library.models.Action;
import library.models.Book;
import library.repositories.ActionRepository;
import library.users.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActionService {

    private ActionRepository actionRepository;

    //czy ta metoda jest potrzebna?
    public Action addBook(Book book, User user) {
        Action action = new Action();
        action.setUser(user);
        action.setBook(book);
        action.setCreated(LocalDateTime.now());
        action.setStatusRekordu(StatusRekordu.ACTIVE);
        action.setActionDescription(ActionDescription.NOWOSC);
        return actionRepository.save(action);
    }

    public Action prolongation(Book book, User user) {
        Action action = new Action();
        action.setUser(user);
        action.setBook(book);
        action.setStatusRekordu(StatusRekordu.ACTIVE);
        action.setCreated(LocalDateTime.now());
        action.setActionDescription(ActionDescription.PRZEDLUZENIE);
        return actionRepository.save(action);
    }

    public Action returnBook(Book book, User user) {
        Action action = new Action();
        action.setUser(user);
        action.setBook(book);
        action.setStatusRekordu(StatusRekordu.ACTIVE);
        action.setCreated(LocalDateTime.now());
        action.setActionDescription(ActionDescription.ZWROT);
        return actionRepository.save(action);
    }

    public Action updateBook(Book book, User user) {
        Action action = new Action();
        action.setUser(user);
        action.setBook(book);
        action.setStatusRekordu(StatusRekordu.ACTIVE);
        action.setCreated(LocalDateTime.now());
        action.setUpdated(LocalDateTime.now());
        action.setActionDescription(ActionDescription.AKTUALIZACJA);
        return actionRepository.save(action);
    }

    public Action paymentInfo(Book book, User user) {
        Action action = new Action();
        action.setUser(user);
        action.setBook(book);
        action.setStatusRekordu(StatusRekordu.ACTIVE);
        action.setCreated(LocalDateTime.now());
        action.setUpdated(LocalDateTime.now());
        action.setActionDescription(ActionDescription.ZAPLACENIE);
        return actionRepository.save(action);
    }

    public Action loanBook(Book book, User user) {
        Action action = new Action();
        action.setUser(user);
        action.setBook(book);
        action.setStatusRekordu(StatusRekordu.ACTIVE);
        action.setCreated(LocalDateTime.now());
        action.setActionDescription(ActionDescription.WYPOZYCZENIE);
        return actionRepository.save(action);
    }

    public Action expiredLoan(Book book, User user) {
        Action action = new Action();
        action.setUser(user);
        action.setBook(book);
        action.setStatusRekordu(StatusRekordu.ACTIVE);
        action.setCreated(LocalDateTime.now());
        action.setActionDescription(ActionDescription.PRZETERMINOWANIE);
        return actionRepository.save(action);
    }

    public Action destroyBook(Book book, User user) {
        Action action = new Action();
        action.setUser(user);
        action.setBook(book);
        action.setStatusRekordu(StatusRekordu.ACTIVE);
        action.setCreated(LocalDateTime.now());
        action.setActionDescription(ActionDescription.ZNISZCZENIE);
        return actionRepository.save(action);
    }

    private Action updateAction(Action action, Integer actionId) {
        Action actionFromBase = actionRepository.getOne(actionId);

        return actionRepository.save(action);
    }
}
