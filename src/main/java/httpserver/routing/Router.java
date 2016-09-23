package httpserver.routing;

import httpserver.httpmessages.HTTPRequest;
import httpserver.httpmessages.HTTPResponse;

import java.net.URI;
import java.util.List;

import static httpserver.routing.Method.*;

public class Router {

    private final List<Route> registeredRoutes;

    public Router(List<Route> registeredRoutes) {
        this.registeredRoutes = registeredRoutes;
    }

    public boolean hasRegistered(URI uri) {
        for (Route route : registeredRoutes) {
            if (isRegistered(uri, route)) {
                return true;
            }
        }
        return false;
    }

    public HTTPResponse getResponse(HTTPRequest httpRequest) {
        for (Route route : registeredRoutes) {
            if (isRegistered(httpRequest.getRequestURI(), route)) {
                return route.performAction(httpRequest);
            }
        }
        return null;
    }

    private boolean isRegistered(URI uri, Route route) {
        return route.getUri().equals(uri);
    }

    public boolean allowsMethod(Method method) {
        return method == GET || method == HEAD;
    }
}
