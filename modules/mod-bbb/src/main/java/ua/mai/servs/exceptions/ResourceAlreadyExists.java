package ua.mai.servs.exceptions;

public class ResourceAlreadyExists extends RuntimeException {
    public ResourceAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }
}
