package library.validators.mainValidators;

import library.models.Action;
import library.repositories.ActionRepository;
import library.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * metoda do sprawdzania, czy użytkownik  przekroczył maksymalną ilość przeterminowanych zwrotów
 * TODO: jeśli w historii wypożyczeń użytkownik ma przynajmniej 5 zwrotów po terminie,
 * TODO: można mu nadać płatność lub zezwalać na wypożyczanie maksymalnie jednej książki naraz
 */
@Component

public class OverdueReturnBookAmountValidator extends AbstractValidator {

    private ActionRepository actionRepository;

    @Autowired
    public OverdueReturnBookAmountValidator(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    //maksymalna ilość przeterminowanych zwrotów
    private final Integer MAX_AMOUNT_OF_OVERDUE_RETURN = 5;

    @Override
    public boolean validator(User user) {
        //lista przeterminowanych zwrotów jednego użytkownika
        List<Action> actionList = actionRepository.findActionsWithOverdueReturnsByUser(user);
        return actionList.size() >= MAX_AMOUNT_OF_OVERDUE_RETURN;
    }
}
