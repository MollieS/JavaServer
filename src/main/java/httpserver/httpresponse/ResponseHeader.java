package httpserver.httpresponse;

public enum ResponseHeader {

    CONTENT_TYPE("Content-Type"),
    CONTENT_RANGE("Content-Range"),
    ALLOW("Allow"),
    LOCATION("Location"),
    DATE("Date"),
    COOKIE("Set-Cookie"),
    AUTH("WWW-Authenticate");

    private final String headerName;

    public String headerName() {
        return headerName;
    }

    ResponseHeader(String headerName) {
        this.headerName = headerName;
    }
}
