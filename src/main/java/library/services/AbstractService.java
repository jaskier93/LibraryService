package library.services;

import library.converters.ActionJson;
import library.converters.JsonConverter;
import library.enums.ActionDescription;
import library.models.Book;
import library.models.User;
import library.repositories.UserRepository;
import library.validators.mainValidators.AbstractValidator;
import library.validators.ZbiorczyWalidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public abstract class AbstractService {

    private final ZbiorczyWalidator zbiorczyWalidator;
    private final JsonConverter jsonConverter;
    private final UserRepository userRepository;

    public abstract void mainAction(String json);

    public abstract void cancelAction(String json); //ewentualnie id -dla różnych serwisów mogą być potrzebne różne parametry, a można je zawsze wyciągnąć za pomocą ID

    public abstract List<AbstractValidator> getValidators();

    public abstract List<ActionDescription> allowedActions();


    private void method(String json) {
        mainAction(json);
        ActionJson actionJson = jsonConverter.convertJsonToAction(json);
        User user = userRepository.getOne(actionJson.getUserId());
        zbiorczyWalidator.checkIt(getValidators(), user);
//        TODO: walidacja;

    }
}
