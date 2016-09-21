package httpserver.httpmessages;

public class HTTPRequest {

    private String method;
    private String requestURI;

    public HTTPRequest(String method, String requestURI) {
        this.method = method;
        this.requestURI = requestURI;
    }

    public String getMethod() {
        return method;
    }

    public String getRequestURI() {
        return requestURI;
    }
}
