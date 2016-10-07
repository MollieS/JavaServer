package httpserver.routing;

import httpserver.Request;
import httpserver.Response;
import httpserver.httpresponse.HTTPResponse;
import httpserver.httpresponse.ResponseHeader;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static httpserver.httpresponse.StatusCode.NOTALLOWED;

public abstract class Route {

    private final URI uri;
    private final HashMap<ResponseHeader, byte[]> responseHeaders;
    private List<Method> allowedMethods;

    public abstract Response performAction(Request request);

    public Route(String uri, Method...methods) {
        this.uri = URI.create(uri);
        this.allowedMethods = new ArrayList<>();
        this.responseHeaders = new HashMap<>();
        Collections.addAll(allowedMethods, methods);
    }

    public URI getUri() {
        return uri;
    }

    public boolean methodIsAllowed(Method method) {
        return allowedMethods.contains(method);
    }

    public List<Method> getAllowedMethods() {
        return allowedMethods;
    }

    public HTTPResponse methodNotAllowed() {
        return HTTPResponse.create(NOTALLOWED);
    }

    public HashMap<ResponseHeader, byte[]> getResponseHeaders() {
        return responseHeaders;
    }

    public byte[] formatAllowedMethods() {
        String methods = "";
        for (Method method : getAllowedMethods()) {
            methods += method.name() + ",";
        }
        methods = methods.substring(0, methods.length() - 1);
        return methods.getBytes();
    }
}
