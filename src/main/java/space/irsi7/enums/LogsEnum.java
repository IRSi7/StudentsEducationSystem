package space.irsi7.enums;

public enum LogsEnum {

    SUCCESS("Операция прошла успешно"),
    FAIL("Ошибка в введённых данных");

    private final String message;

    LogsEnum (String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
