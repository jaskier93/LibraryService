package library.validators;

import library.models.User;
import library.validators.mainValidators.AbstractValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ZbiorczyWalidator {

    public boolean checkIt(List<AbstractValidator> booleanList, User user) {
        List<Integer> lista = new ArrayList();
        booleanList.forEach(klasaValidatora -> {
            if (!klasaValidatora.validator(user)) {
             //   throw klasaValidatora.createException();
            } else {
                lista.add(1);
            }
        });
        return lista.size() == booleanList.size();
    }
}
