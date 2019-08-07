package library.validators.dateValidators

import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDate

class IsDateCorrectSpockTest extends Specification {

    LocalDate today = LocalDate.now();
    @Shared
    LocalDate date = LocalDate.now().minusDays(25);
    @Shared
    LocalDate date2 = LocalDate.now().plusDays(25);
    List<LocalDate> dateList = new ArrayList<>();
    IsDateCorrect validator = new IsDateCorrect();

    def "Expected result"() {
        setup:
        dateList.add(date)
        dateList.add(date2)
        expect:
        expectedResult == validator.validator(LocalDate)

        where:
        dateList || expectedResult
        date     || true
        date2    || false
    }
}
