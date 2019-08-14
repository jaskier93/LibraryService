package library.services;

import com.google.common.collect.ImmutableList;
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
import library.exceptions.ExceptionEmptyList;
import library.services.modelservices.ActionService;
import library.services.modelservices.BookStateService;
import library.models.User;
import library.validators.mainValidators.AbstractValidator;
import library.validators.mainValidators.BookAmountValidator;
import library.validators.mainValidators.PaymentAmountValidator;
import library.validators.mainValidators.RentFifthBookValidator;
import library.validators.ZbiorczyWalidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class RentService extends AbstractService {

    private final BookRepository bookRepository;
    private final BookStateRepository bookStateRepository;
    private final ActionRepository actionRepository;
    private final ActionService actionService;
    private final BookStateService bookStateService;
    private final JsonConverter jsonConverter;
    private final PaymentAmountValidator paymentAmountValidator;
    private final RentFifthBookValidator rentFifthBookValidator;
    private final BookAmountValidator bookAmountValidator;
    private final UserRepository userRepository;

    @Autowired
    public RentService(ZbiorczyWalidator zbiorczyWalidator, JsonConverter jsonConverter, UserRepository userRepository, BookRepository bookRepository, BookStateRepository bookStateRepository,
                       ActionRepository actionRepository, ActionService actionService, BookStateService bookStateService, JsonConverter jsonConverter1, PaymentAmountValidator paymentAmountValidator,
                       RentFifthBookValidator rentFifthBookValidator, BookAmountValidator bookAmountValidator, UserRepository userRepository1) {
        super(zbiorczyWalidator, jsonConverter, userRepository);
        this.bookRepository = bookRepository;
        this.bookStateRepository = bookStateRepository;
        this.actionRepository = actionRepository;
        this.actionService = actionService;
        this.bookStateService = bookStateService;
        this.jsonConverter = jsonConverter1;
        this.paymentAmountValidator = paymentAmountValidator;
        this.rentFifthBookValidator = rentFifthBookValidator;
        this.bookAmountValidator = bookAmountValidator;
        this.userRepository = userRepository1;
    }

    @Override
    public void mainAction(String json) {
        ActionJson actionJson = jsonConverter.convertJsonToAction(json);
        Book book = bookRepository.getOne(actionJson.getBookId());
        User user = userRepository.getOne(actionJson.getBookId());
        boolean fourthBook = bookAmountValidator.validator(user);
        boolean fifthBook = rentFifthBookValidator.validator(user);

        if (fifthBook || fourthBook)  //po dodaniu listy walidacji może być if(fifthBook ||  zbiorczyWalidator.checkIt(getValidators(), user))
        {
            Action action = actionService.createAction(book, user, ActionDescription.WYPOZYCZENIE);
            BookState bookState = bookStateService.createBookState(action, BookStateEnum.WYPOZYCZONA);
            bookState.setDateTo(LocalDate.now().MAX);
            bookStateRepository.save(bookState);
        }
    }

    @Override
    public void cancelAction(String json) {
        ActionJson actionJson = jsonConverter.convertJsonToAction(json);
        Book book = bookRepository.getOne(actionJson.getBookId());
        User user = userRepository.getOne(actionJson.getBookId());
        Action actionFromBase = actionRepository.findNewestAction(user, ActionDescription.WYPOZYCZENIE, LocalDateTime.now().minusDays(3)).get(0);
        BookState bookStateFromBase = bookStateRepository.findNewestBookState(user, ActionDescription.WYPOZYCZENIE).get(0);
        if (user.getId().equals(actionFromBase.getId()) && book.getId().equals(bookStateFromBase.getId())) {
            actionFromBase.setStatusRekordu(StatusRekordu.HISTORY);
            bookStateFromBase.setStatusRekordu(StatusRekordu.HISTORY);
            bookStateFromBase.setBookStateEnum(BookStateEnum.ZWROCONA);
            actionRepository.save(actionFromBase);
            bookStateRepository.save(bookStateFromBase);
            bookRepository.save(book);
        } else {
            throw new ExceptionEmptyList(" Nie odnaleziono akcji/książki/użytkownika. ");
        }
    }

    @Override
    public List<AbstractValidator> getValidators() {
        return ImmutableList.of(bookAmountValidator, rentFifthBookValidator, paymentAmountValidator);
    }

    @Override
    public List<ActionDescription> allowedActions() {
        return ImmutableList.of(ActionDescription.WYPOZYCZENIE);
    }

}
