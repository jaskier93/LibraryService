package library.validators

import spock.lang.Shared
import spock.lang.Specification

class IsStringOneWordSpockTest extends Specification {
    @Shared
    String variable = new String("");
    @Shared
    String variable2 = new String("");
    List<String> stringList = new ArrayList<>();

    IsStringOneWord validator = new IsStringOneWord();

    def "Expected result"() {
        setup:
        variable = "Damian";
        variable2 = "damian";
        stringList.add(variable);
        stringList.add(variable2);
        stringList >> stringList2
        expect:
        expectedResult == validator.validator(stringList2)

        where:
        stringList2 || expectedResult
        variable    || true
        variable2   || false
    }
}
