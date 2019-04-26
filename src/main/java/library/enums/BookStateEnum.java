package library.enums;

public enum  BookStateEnum {
    WYPOŻYCZONA(1),
    ZWRÓCONA(2),
    ZNISZCZONA(3),
    NOWA(4);


    private Integer number;

    BookStateEnum(Integer number) {
        this.number = number;
    }

    public static Integer getNumber(BookStateEnum bookStateEnum) {
        return bookStateEnum.number;
    }
}