package library.validators;

import library.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ZbiorczyWalidator {

/*    public boolean checkIt(List<AbstractValidator> booleanList, User user) {
        List<Integer> lista = new ArrayList();
        booleanList.forEach(c -> {
            if (!c.validator(user)) {
//                        c.exception
            } else {
                lista.add(1);
            }
        });
        return lista.size() == booleanList.size();
    }*/
}
