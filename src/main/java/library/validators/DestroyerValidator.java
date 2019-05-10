package library.validators;

import library.models.Action;
import library.repositories.ActionRepository;
import library.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DestroyerValidator extends AbstractValidator {

    private ActionRepository actionRepository;

    @Autowired
    public DestroyerValidator(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    /**
     * metoda sprawdzająca, czy użytkownik ma zniszczenia książki na koncie
     * w przypadku, gdyby metoda miała sprawdzać, czy zdarzyło się to np w ostatnim miesiącu-należy dodać pole z datą akcji w klasie Action
     * zamiast tego można sprawdzić ile zniszczeń ma użytkownik, jeśli ma np 10-może dostać bana, czyli user.setActive(false)-konto zbanowane
     */
    @Override
    public boolean validator(User user) {
        List<Action> actionList = actionRepository.findActionsWithDestroyedBooksByUser(user);
        return (actionList.size() > 0 && (!actionList.isEmpty()));
    }
}