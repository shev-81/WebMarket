package webmarket.exceptions;

/**
 * Класс обертка, объект класса создается для отправки на фронт после
 * перехвата исключения глобальным перехватчиком исключений.
 */
public class AppError {

    /**
     * Код ошибки исключения.
     */
    private int statusCode;

    /**
     * Сообщение от исключения.
     */
    private String message;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AppError() {
    }

    public AppError(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
