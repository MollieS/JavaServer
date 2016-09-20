package httpserver;

public class SocketConnectionException extends RuntimeException {

    public SocketConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
