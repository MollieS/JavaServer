package httpserver.httpmessages;

public enum StatusCode {

    OK ("OK", 200),
    NOTFOUND ("Not Found", 404);

    public final String reason;
    public final int code;

    StatusCode(String reason, int code) {
        this.reason = reason;
        this.code = code;
    }
}
