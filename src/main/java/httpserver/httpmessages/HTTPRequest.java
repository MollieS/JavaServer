package httpserver.httpmessages;

public class HTTPRequest {

    private String method;
    private String requestURI;

    public HTTPRequest(String method, String requestUri) {
        this.method = method;
        this.requestURI = requestUri;
    }

    public String getMethod() {
        return method;
    }

    public String getRequestURI() {
        return requestURI;
    }
}
