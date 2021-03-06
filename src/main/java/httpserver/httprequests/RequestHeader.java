package httpserver.httprequests;

public enum RequestHeader {

    AUTHORIZATION("Authorization"),
    COOKIE("Cookie"),
    PARAMS("?"),
    RANGE_START("Range"),
    RANGE_END("Range"),
    RANGE("Range"),
    ETAG("If-Match"),
    BODY("content"),
    DATA("data=");

    private final String header;

    public String header() {
        return header;
    }

    RequestHeader(String header) {
        this.header = header;
    }
}
