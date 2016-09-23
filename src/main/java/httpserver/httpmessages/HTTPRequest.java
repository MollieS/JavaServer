package httpserver.httpmessages;

import httpserver.routing.Method;

import java.net.URI;

public class HTTPRequest {

    private final Method method;
    private final URI requestURI;

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
}
