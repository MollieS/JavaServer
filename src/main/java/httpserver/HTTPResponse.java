package httpserver;

public class HTTPResponse {

    private String statusCode;

    public HTTPResponse(int statusCode) {
        this.statusCode = String.valueOf(statusCode);
    }

    public String HTTP() {
        return "HTTP/1.1";
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return "OK";
    }
}
