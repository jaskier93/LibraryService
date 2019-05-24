package library.validators;

import org.springframework.stereotype.Component;

@Component
public class IsStringOneWord {

    private int counter = 0;

    /**
     * metoda ma za zadanie sprawdzać czy podana zmienna typu string:
     * -jest jednym słowem
     * -zaczyna się z wielkiej litery
     * -nie jest pusta
     * -zawiera tylko litery
     * -może być wykorzystana przy imionach, nazwiskach itd
     * -zawiera więcej niż jedną literę
     */
    public boolean validator(String variable) {
        //pierwszy warunek sprawdza czy wprowadzone słowo nie jest jedną litere lub pustym stringiem ( "")
        if (variable == null ||variable.length() <= 1  ) {
            return false;
        } else if (Character.isUpperCase(variable.charAt(0))) {
            for (int i = 0; i < variable.length() - 1; i++) {
                // sprawdzić czy isLetter uzwględni polskie znaki
                if (Character.isLetter(variable.charAt(i))) {
                    counter++;
                }
            }
            return counter == variable.length() - 1;
        }
        return false;
    }
}