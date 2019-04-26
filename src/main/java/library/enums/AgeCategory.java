package library.enums;

public enum AgeCategory {
    NAJMŁODSI("Dla dzieci poniżej 13 lat"),
    NASTOLATKOWIE("Dla nastolatków w wieku 13-18"),
    DOROŚLI("Dla dorosłych");



    private String description;

    public String getDescription() {
        return description;
    }

    AgeCategory(String description) {
        this.description = description;
    }
}