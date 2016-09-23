package httpserver.routing;

import httpserver.httpmessages.HTTPResponse;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static httpserver.httpmessages.HTTPResponseCode.NOTALLOWED;

public abstract class Route {

    private final URI uri;
    private List<Method> allowedMethods;

    public abstract HTTPResponse performAction(Method method);

    public Route(String uri, Method...methods) {
        this.uri = URI.create(uri);
        this.allowedMethods = new ArrayList<>();
        for (Method method : methods) {
            allowedMethods.add(method);
        }
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
        return new HTTPResponse(NOTALLOWED.code, NOTALLOWED.reason);
    }
}
