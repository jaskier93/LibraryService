package library.services;

import com.google.common.collect.ImmutableList;
import library.converters.ActionJson;
import library.converters.JsonConverter;
import library.enums.ActionDescription;
import library.enums.BookStateEnum;
import library.enums.StatusRekordu;
import library.models.*;
import library.repositories.ActionRepository;
import library.repositories.BookRepository;
import library.repositories.BookStateRepository;
import library.repositories.UserRepository;
import library.services.exceptions.ExceptionEmptyList;
import library.services.modelservices.ActionService;
import library.services.modelservices.BookStateService;
import library.services.modelservices.PaymentService;
import library.validators.ZbiorczyWalidator;
import library.validators.mainValidators.AbstractValidator;
import library.validators.mainValidators.PaymentAmountValidator;
import library.validators.mainValidators.PaymentSumValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ReturnBookService extends AbstractService {

    private final ActionService actionService;
    private final BookStateService bookStateService;
    private final PaymentService paymentService;
    private final JsonConverter jsonConverter;
    private final UserRepository userRepository;
    private final ActionRepository actionRepository;
    private final BookStateRepository bookStateRepository;
    private final BookRepository bookRepository;
    private final PaymentAmountValidator paymentAmountValidator;
    private final PaymentSumValidator paymentSumValidator;

    @Autowired
    public ReturnBookService(ZbiorczyWalidator zbiorczyWalidator, JsonConverter jsonConverter, UserRepository userRepository, ActionService actionService, BookStateService bookStateService, PaymentService paymentService, JsonConverter jsonConverter1, UserRepository userRepository1, ActionRepository actionRepository, BookStateRepository bookStateRepository, BookRepository bookRepository, PaymentAmountValidator paymentAmountValidator, PaymentSumValidator paymentSumValidator) {
        super(zbiorczyWalidator, jsonConverter, userRepository);
        this.actionService = actionService;
        this.bookStateService = bookStateService;
        this.paymentService = paymentService;
        this.jsonConverter = jsonConverter1;
        this.userRepository = userRepository1;
        this.actionRepository = actionRepository;
        this.bookStateRepository = bookStateRepository;
        this.bookRepository = bookRepository;
        this.paymentAmountValidator = paymentAmountValidator;
        this.paymentSumValidator = paymentSumValidator;
    }

    @Override
    public void mainAction(String json) {
        //
        ActionJson actionJson = jsonConverter.convertJsonToAction(json);
        Book book = bookRepository.getOne(actionJson.getBookId());
        User user = userRepository.getOne(actionJson.getBookId());

        Action returnBookAction = actionService.createAction(book, user, ActionDescription.ZWROT);

        BookState bookStateReturned = bookStateService.closeLastBookStateAndCreateOneWith(book, returnBookAction, BookStateEnum.ZWROCONA);

        //temp boolean
        boolean isProlongatedBook = false; // przyjmujemy, że książka przedłużana jest tylko o 14 dni i tyle. Przez to nawet jeśli stan obowiązuje do 9999 roku to wiemy, że od daty od + 2 tyg
        /** TODO Dajmy na to, że tylko 2 razy może przedłużyć ->
         *    Do napisania metody:
         * 1. Sprawdzajaca ile od ostatniego stanu 'wypozyczona' minelo dni
         * 2. Sprawdzajaca ile od ostatniego stanu 'wypozyczona' bylo akcji z enumem 'przedluzona'
         * 3. metoda ktora wykorzystuje ww metody i nalicza kare za przetrzymanie
         * ---------------------------------------------------------------
         */

        Payment expiredLoanPayment = paymentService.expiredLoan(bookStateReturned); // sprawdzić daty

        //TODO:ewentualnie dodać walidację (jeśli potrzebna)-czy użytkownik od razu płaci karę
        Action paymentAction = actionService.createAction(book, user, ActionDescription.ZAPLACENIE);
        Payment updatedExpiredLoanPayment = paymentService.updatePayment(expiredLoanPayment.getId());
    }

    @Override
    public void cancelAction(String json) {
        ActionJson actionJson = jsonConverter.convertJsonToAction(json);
        Book book = bookRepository.getOne(actionJson.getBookId());
        User user = userRepository.getOne(actionJson.getBookId());
        Action actionFromBase = actionRepository.findNewestAction(user, ActionDescription.ZWROT, LocalDateTime.now().minusDays(3)).get(0);
        BookState bookStateFromBase = bookStateRepository.findNewestBookState(user, ActionDescription.ZWROT).get(0);
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
        return ImmutableList.of(paymentAmountValidator, paymentSumValidator);
    }

    @Override
    public List<ActionDescription> allowedActions() {
        return null;
    }
}
