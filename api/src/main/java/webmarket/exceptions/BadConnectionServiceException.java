package webmarket.exceptions;

/**
 * Исключение выбрасываемое при отсутствии соединения с сервисом.
 */
public class BadConnectionServiceException extends RuntimeException{

    public enum CartServiceErrors {
        SERVICE_SHUTDOWN, CART_NOT_FOUND
    }

    /**
     * Код ошибки.
     */
    private CartServiceErrors code;

    public BadConnectionServiceException() {
        super();
    }

    public BadConnectionServiceException(String message) {
        super(message);
    }


    public BadConnectionServiceException(String message, CartServiceErrors code) {
        super(message);
        this.code = code;
    }



    public CartServiceErrors getCode() {
        return code;
    }

    public void setCode(CartServiceErrors cartServiceErrors) {
        this.code = cartServiceErrors;
    }
}
