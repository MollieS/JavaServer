package httpserver.httpmessages;

public enum HTTPResponses {

    OK ("OK", 200),
    NOTFOUND ("Not Found", 404),
    TEAPOT("I'm a teapot", 418);

    public final String reason;
    public final int code;

    HTTPResponses(String reason, int code) {
        this.reason = reason;
        this.code = code;
    }
}
