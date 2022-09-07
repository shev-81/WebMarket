package webmarket.exceptions;

/**
 * Исключение выбрасывается, если не найден ресурс из БД.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
