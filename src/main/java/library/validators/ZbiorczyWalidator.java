package library.validators;

import library.models.User;
import library.validators.mainValidators.AbstractValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ZbiorczyWalidator {

    public boolean checkIt(List<AbstractValidator> booleanList, User user) {
        List<Integer> lista = new ArrayList();
        booleanList.forEach(klasaValidatora -> {
            if (!klasaValidatora.validator(user)) {
                   throw klasaValidatora.validatorException();
            } else {
                lista.add(1);
            }
        });
        return lista.size() == booleanList.size();
    }
}
