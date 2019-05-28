package library.enums;

//TODO: dodawać zwroty bez polskich znaków
public enum ActionDescription {
    TEST, //enum tylko do testów-łatwiejsze usuwanie testowych obiektów typu Action
    WYPOZYCZENIE,
    NOWOSC,
    ZNISZCZENIE,
    AKTUALIZACJA,
    PRZEDLUZENIE,
    ZAPLACENIE, //opłacenie płatności za zniszczenie książki/zwrot po terminie
    PRZETERMINOWANIE, //przeterminowany zwrot książki
    ZWROT;
}
