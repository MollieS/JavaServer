package httpserver;

public class HTTPResponse {

    private final String reasonPhrase;
    private final String statusCode;

    public HTTPResponse(int statusCode, String reasonPhrase) {
        this.statusCode = String.valueOf(statusCode);
        this.reasonPhrase = reasonPhrase;
    }

    public String HTTP() {
        return "HTTP/1.1";
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}
