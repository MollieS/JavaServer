package httpserver.httprequests;

import httpserver.Request;
import httpserver.routing.Method;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class HTTPRequest implements Request {

    private static final String PROTOCOL_VERSION = "HTTP/1.1";
    private final Method method;
    private final URI requestURI;
    private final HashMap<RequestHeader, String> headers;
    private String statusHeader;

    private HTTPRequest(Method method, String requestURI) {
        this.method = method;
        this.requestURI = URI.create(requestURI);
        this.statusHeader = method.name() + " " + requestURI + " " + PROTOCOL_VERSION;
        this.headers = new HashMap<>();
    }

    private HTTPRequest(Method method, URI requestURI, HashMap<RequestHeader, String> headers) {
        this.method = method;
        this.requestURI = requestURI;
        this.headers = headers;
    }

    @Override
    public boolean hasHeader(RequestHeader header) {
        return (headers.containsKey(header) && headers.get(header) != null);
    }

    @Override
    public String getValue(RequestHeader header) {
        for (Map.Entry<RequestHeader, String> entry : headers.entrySet()) {
            if (entry.getKey() == header) {
                return entry.getValue();
            }
        }
        return null;
    }

    public Method getMethod() {
        return method;
    }

    public URI getRequestURI() {
        return requestURI;
    }

    public String getStatusHeader() {
        return statusHeader;
    }

    public static HTTPRequest create(Method requestMethod, String path) {
        return new HTTPRequest(requestMethod, path);
    }

    public HTTPRequest withHeaders(HashMap<RequestHeader, String> headers) {
        return new HTTPRequest(this.method, this.requestURI, headers);
    }
}
