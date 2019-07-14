package library.validators

import library.enums.BookStateEnum
import library.models.Book
import library.models.BookState
import library.repositories.BookStateRepository
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class IsBookLoanableSpockTest extends Specification {
    BookStateRepository mockBookStateRepostiroy = Mock(BookStateRepository);
    @Shared
    BookState bookState = new BookState();
    @Shared
    BookState bookState2 = new BookState();
    @Shared
    Book book = new Book()
    @Shared
    Book book2 = new Book();

    IsBookLoanable validator = new IsBookLoanable(mockBookStateRepostiroy);

    @Unroll
    def "Expected result"() {
        setup:
        bookState.setBookStateEnum(BookStateEnum.ZNISZCZONA)
        bookState.setBook(book);
        mockBookStateRepostiroy.save(bookState)
        bookState2.setBookStateEnum(BookStateEnum.NOWA)
        mockBookStateRepostiroy.save(bookState2)

        mockBookStateRepostiroy.findBookStateByBook2(book.id) >> bookStateList

        expect:
        expectedResult == validator.isBookAbleToLoan(book)
        expectedResult == validator.isBookAbleToLoan(book2)

        where:
        bookStateList || expectedResult
        bookState     || false
        bookState2    || true
    }
}
