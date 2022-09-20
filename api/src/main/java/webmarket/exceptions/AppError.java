package webmarket.exceptions;

/**
 * Wrapper class, a class object is created to be sent to the front after
 * interception of an exception by a global exception interceptor.
 */
public class AppError {

    /**
     * Exception error code.
     */
    private int statusCode;

    /**
     * The message from the exception.
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
