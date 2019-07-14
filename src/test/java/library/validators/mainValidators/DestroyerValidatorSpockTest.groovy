package library.validators.mainValidators

import library.enums.ActionDescription
import library.models.Action
import library.models.User
import library.repositories.ActionRepository
import library.repositories.UserRepository
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class DestroyerValidatorSpockTest extends Specification {

    ActionRepository mockActionRepository = Mock(ActionRepository)
    @Shared
    private User user = new User();
    @Shared
    private Action action = new Action();
    @Shared
    private Action action2 = new Action();

    DestroyerValidator validator = new DestroyerValidator(mockActionRepository)

    @Unroll
    def "Expected result #expectedResult and actionList size #actionList.size"() {

        setup:
        mockActionRepository.findActionsWithDestroyedBooksByUser(user) >> actionList
        action.setActionDescription(ActionDescription.ZNISZCZENIE);
        action2.setActionDescription(ActionDescription.NOWOSC);

        expect:
        expectedResult == validator.validator(user)

        where:
        actionList || expectedResult
        action     || true
        action2    || false
    }
}