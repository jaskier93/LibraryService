package library.services;

import library.converters.ActionJson;
import library.converters.JsonConverter;
import library.enums.ActionDescription;
import library.models.User;
import library.repositories.UserRepository;
import library.validators.mainValidators.AbstractValidator;
import library.validators.ZbiorczyWalidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class AbstractService {

    private final ZbiorczyWalidator zbiorczyWalidator;
    private final JsonConverter jsonConverter;
    private final UserRepository userRepository;

    @Autowired
    public AbstractService(ZbiorczyWalidator zbiorczyWalidator, JsonConverter jsonConverter, UserRepository userRepository) {
        this.zbiorczyWalidator = zbiorczyWalidator;
        this.jsonConverter = jsonConverter;
        this.userRepository = userRepository;
    }

    public abstract void mainAction(String json);

    public abstract void cancelAction(String json); //ewentualnie id -dla różnych serwisów mogą być potrzebne różne parametry, a można je zawsze wyciągnąć za pomocą ID

    public abstract List<AbstractValidator> getValidators();

    public abstract List<ActionDescription> allowedActions();


    /**
     * Wywoływana ta metoda, później za pomocą strategii jest dobierany(sprawdzamy enum z akcji), który serwis się załączy. Tutaj też są wywoływane walidatory
     * które w każdym serwisie umieszczam w abstrakcyjnej metodzie 'getValidators'.
     *
     * @param json TODO: do poprawy
     */
    private void startAction(String json) {
        mainAction(json);
        ActionJson actionJson = jsonConverter.convertJsonToAction(json);
        User user = userRepository.getOne(actionJson.getUserId());
        zbiorczyWalidator.checkIt(getValidators(), user);
    }

}
