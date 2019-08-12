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
        action.setUpdated(LocalDateTime.now());
        action.setStatusRekordu(StatusRekordu.ACTIVE);
        action.setActionDescription(actionDescription);
        return actionRepository.save(action);
    }
}
