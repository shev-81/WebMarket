package webmarket.exceptions;

/**
 * An exception thrown when there is no connection to the service.
 */
public class BadConnectionServiceException extends RuntimeException{

    public enum CartServiceErrors {
        SERVICE_SHUTDOWN, CART_NOT_FOUND
    }

    /**
     * Error code.
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
