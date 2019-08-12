package library.validators.mainValidators;

import library.exceptions.ExceptionEmptyList;
import library.exceptions.NoObjectException;
import library.models.Action;
import library.repositories.ActionRepository;
import library.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * metoda do sprawdzania, czy użytkownik  przekroczył maksymalną ilość przeterminowanych zwrotów
 */
@Component
public class OverdueReturnBookAmountValidator extends AbstractValidator {

    //maksymalna ilość przeterminowanych zwrotów
    private final static Integer MAX_AMOUNT_OF_OVERDUE_RETURN = 5;

    private ActionRepository actionRepository;

    @Autowired
    public OverdueReturnBookAmountValidator(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    @Override
    public boolean validator(User user) {
        List<Action> actionList = actionRepository.findActionsWithOverdueReturnsByUser(user);
        return actionList.size() >= MAX_AMOUNT_OF_OVERDUE_RETURN;
    }

    @Override
    public void validatorException(User user) {
        List<Action> actionList = actionRepository.findActionsWithOverdueReturnsByUser(user);
        if (actionList.size() < MAX_AMOUNT_OF_OVERDUE_RETURN || actionList.isEmpty())
            throw new ExceptionEmptyList("Użytkownik nie przekroczył ilości przeterminowanych zwrotów.");
    }
}
