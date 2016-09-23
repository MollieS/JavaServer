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
            if (route.getUri().equals(uri)) {
                return true;
            }
        }
        return false;
    }

    public HTTPResponse getResponse(HTTPRequest httpRequest) {
        for (Route route : registeredRoutes) {
            if (route.getUri().equals(httpRequest.getRequestURI())) {
                return route.performAction(httpRequest.getMethod());
            }
        }
        return null;
    }

    public boolean allowsMethod(Method method) {
        return method == GET;
    }

    public enum RegisteredRoute {
        COFFEE(true, "418", "/coffee", GET, OPTIONS),
        TEA(false, "200", "/tea", GET, OPTIONS),
        METHOD_OPTIONS(false, "200", "/method_options", GET, HEAD, POST, OPTIONS, PUT),
        FORM(false, "200", "/form", GET, POST, PUT),
        OPTIONS2(false, "200", "/method_options2", GET, OPTIONS);

        public final String expectedCode;
        public final URI uri;
        public final Method[] methods;
        public final boolean overrideAllowed;

        RegisteredRoute(boolean overrideAllowed, String code, String uri, Method... methods) {
            this.overrideAllowed = overrideAllowed;
            this.expectedCode = code;
            this.uri = URI.create(uri);
            this.methods = methods;
        }
    }
}
