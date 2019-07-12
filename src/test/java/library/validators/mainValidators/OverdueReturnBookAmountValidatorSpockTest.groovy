package library.validators.mainValidators

import library.models.Action
import library.models.User
import library.repositories.ActionRepository
import spock.lang.Specification
import spock.lang.Unroll

class OverdueReturnBookAmountValidatorSpockTest extends Specification {
    ActionRepository mockActionRepository = Mock(ActionRepository)
    private User user = new User();

    OverdueReturnBookAmountValidator validator = new OverdueReturnBookAmountValidator()

    @Unroll
    def "test"() {
        setup:
        mockActionRepository.findActionsWithOverdueReturnsByUser(user) >> actionList

        expect:
        expectedResult == validator.validator(user)

        where:
        actionList                                                                                         || expectedResult
        [new Action()]                                                                                     || false
        [new Action(), new Action(), new Action(), new Action(), new Action(), new Action(), new Action()] || true

    }
}
