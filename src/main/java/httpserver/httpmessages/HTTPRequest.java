package httpserver.httpmessages;

import httpserver.routing.Method;

import java.net.URI;

public class HTTPRequest {

    private final Method method;
    private final URI requestURI;
    private String data;

    public HTTPRequest(Method method, String requestURI) {
        this.method = method;
        this.requestURI = URI.create(requestURI);
    }

    public Method getMethod() {
        return method;
    }

    public URI getRequestURI() {
        return requestURI;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
