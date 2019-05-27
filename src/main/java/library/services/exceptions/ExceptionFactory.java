package library.services.exceptions;

import library.enums.Category;

public class ExceptionFactory {

    public static ExceptionEmptyList trowException(String msg, Category category){
        String msgd = String.format("Nie znaleziono żadnej książki o podanej kategorii %d", category);
        return new ExceptionEmptyList(msgd);
    }
}
