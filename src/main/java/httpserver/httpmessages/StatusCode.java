package httpserver.httpmessages;

public enum StatusCode {

    OK ("OK", 200),
    NOTFOUND ("Not Found", 404),
    TEAPOT("I'm a teapot", 418),
    NOTALLOWED("Method Not Allowed", 405);

    public final String reason;
    public final int code;

    StatusCode(String reason, int code) {
        this.reason = reason;
        this.code = code;
    }
}
