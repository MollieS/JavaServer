package httpserver.httpmessages;

public class ResponseWriterException extends RuntimeException {

    public ResponseWriterException(String message, Throwable cause) {
        super(message, cause);
    }
}
