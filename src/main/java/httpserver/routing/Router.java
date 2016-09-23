package httpserver.routing;

import httpserver.httpmessages.HTTPResponse;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static httpserver.routing.Method.*;

public class Router {

    public List<Method> allowedMethods(URI requestURI) {
        List<Method> allowedMethods = new ArrayList<>();
        RegisteredRoute registeredRoute = checkForKnownRoute(requestURI);
        if (registeredRoute != null) {
            for (Method method : registeredRoute.methods) {
                allowedMethods.add(method);
            }
        } else {
            allowedMethods.add(GET);
            allowedMethods.add(HEAD);
            allowedMethods.add(OPTIONS);
        }
        return allowedMethods;
    }

    private RegisteredRoute checkForKnownRoute(URI requestURI) {
        for (RegisteredRoute registeredRoute : RegisteredRoute.values()) {
            if (registeredRoute.uri.equals(requestURI)) {
                return registeredRoute;
            }
        }
        return null;
    }

    public boolean methodIsAllowed(URI requestURI, Method method) {
        return allowedMethods(requestURI).contains(method);
    }

    public boolean expectedResponse(URI requestURI, HTTPResponse httpResponse) {
        for (RegisteredRoute registeredRoute : RegisteredRoute.values()) {
            if (registeredRoute.uri.equals(requestURI) && !registeredRoute.expectedCode.equals(httpResponse.getStatusCode())) {
                return false;
            }
        }
        return true;
    }

    public RegisteredRoute getRoute(URI requestURI) {
        for (RegisteredRoute registeredRoute : RegisteredRoute.values()) {
            if (requestURI.equals(registeredRoute.uri)) {
                return registeredRoute;
            }
        }
        return null;
    }

    public boolean isOverrideAllowed(RegisteredRoute registeredRoute) {
        return registeredRoute.overrideAllowed;
    }

    public boolean routeIsRegistered(URI uri) {
        for (RegisteredRoute route : RegisteredRoute.values()) {
            if (route.uri.equals(uri)) {
                return true;
            }
        }
        return false;
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
