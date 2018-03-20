package by.fyodorov.multithreading.exception;

public class MultiThreadException extends Exception {

    public MultiThreadException(String message) {
        super(message);
    }

    public MultiThreadException(String message, Exception e) {
        super(message, e);
    }
}
