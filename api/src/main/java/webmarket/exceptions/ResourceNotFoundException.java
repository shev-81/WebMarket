package webmarket.exceptions;

/**
 * An exception is thrown if a resource from the database is not found.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
