package library.services;

import library.converters.ActionJson;
import library.converters.JsonConverter;
import library.enums.ActionDescription;
import library.enums.BookStateEnum;
import library.enums.StatusRekordu;
import library.models.Action;
import library.models.Book;
import library.models.BookState;
import library.repositories.ActionRepository;
import library.repositories.BookRepository;
import library.repositories.BookStateRepository;
import library.repositories.UserRepository;
import library.services.exceptions.ExceptionEmptyList;
import library.services.modelservices.ActionService;
import library.services.modelservices.BookStateService;
import library.models.User;
import library.validators.ZbiorczyWalidator;
import library.validators.mainValidators.AbstractValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ProlongationService extends AbstractService {

    private final ActionService actionService;
    private final BookStateService bookStateService;
    private final ActionRepository actionRepository;
    private final UserRepository userRepository;
    private final BookStateRepository bookStateRepository;
    private final BookRepository bookRepository;
    private final JsonConverter jsonConverter;

    @Autowired
    public ProlongationService(ZbiorczyWalidator zbiorczyWalidator, JsonConverter jsonConverter, UserRepository userRepository, ActionService actionService, BookStateService bookStateService,
                               ActionRepository actionRepository, UserRepository userRepository1, BookStateRepository bookStateRepository, BookRepository bookRepository, JsonConverter jsonConverter1) {
        super(zbiorczyWalidator, jsonConverter, userRepository);
        this.actionService = actionService;
        this.bookStateService = bookStateService;
        this.actionRepository = actionRepository;
        this.userRepository = userRepository1;
        this.bookStateRepository = bookStateRepository;
        this.bookRepository = bookRepository;
        this.jsonConverter = jsonConverter1;
    }

    @Override
    public void mainAction(String json) {
        ActionJson actionJson = jsonConverter.convertJsonToAction(json);
        Book book = bookRepository.getOne(actionJson.getBookId());
        User user = userRepository.getOne(actionJson.getBookId());
        Action action = actionService.createAction(book, user, ActionDescription.PRZEDLUZENIE);
        BookState bookState = bookStateService.createBookState(action, BookStateEnum.WYPOZYCZONA);
        bookState.setDateTo(LocalDate.now().plusDays(30));
        bookStateRepository.save(bookState);
    }

    @Override
    public void cancelAction(String json) {
        ActionJson actionJson = jsonConverter.convertJsonToAction(json);
        Book book = bookRepository.getOne(actionJson.getBookId());
        User user = userRepository.getOne(actionJson.getBookId());
        Action actionFromBase = actionRepository.findNewestAction(user, ActionDescription.PRZEDLUZENIE, LocalDateTime.now().minusDays(3)).get(0);
        BookState bookStateFromBase = bookStateRepository.findNewestBookState(user, ActionDescription.PRZEDLUZENIE).get(0);

        if (user.getId().equals(actionFromBase.getId()) && book.getId().equals(bookStateFromBase.getId())) {
            actionFromBase.setStatusRekordu(StatusRekordu.HISTORY);
            bookStateFromBase.setStatusRekordu(StatusRekordu.HISTORY);
            bookStateFromBase.setBookStateEnum(BookStateEnum.WYPOZYCZONA);
            actionRepository.save(actionFromBase);
            bookStateRepository.save(bookStateFromBase);
            bookRepository.save(book);
        } else {
            throw new ExceptionEmptyList(" Nie odnaleziono akcji/książki/użytkownika. ");
        }
    }

    @Override
    public List<AbstractValidator> getValidators() {
        //    getValidators().add(prolongationValidator);
        return null;
    }

    @Override
    public List<ActionDescription> allowedActions() {
        return null;
    }
}
