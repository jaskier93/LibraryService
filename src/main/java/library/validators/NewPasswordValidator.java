package library.validators;

import org.springframework.stereotype.Component;


@Component
public class NewPasswordValidator {
    /**
     * metoda ma na celu sprawdzać poprawność nowego hasła, które:
     * -ma zaczynać się wielką literą
     * -ma mieć łącznie minimum 8 znaków
     * -ma zawierać minimum jedną cyfrę
     *
     * @param newPassword
     * @return
     */
    public boolean validator(String newPassword) {
        int counter = 0;
        int passwordLength = newPassword.length();
        if (passwordLength >= 8 && Character.isUpperCase(newPassword.charAt(0))) {
            for (int i = 1; i < passwordLength; i++) {
                if (Character.isDigit(newPassword.charAt(i))) {
                    counter++;
                }
            }
            return counter > 0;
        }
        return false;
    }
}
