package httpserver.server;

import httpserver.httpmessages.HTTPResponse;

import java.util.ArrayList;
import java.util.List;

import static httpserver.server.Router.Methods.GET;

public class Router {

    public List<Methods> allowedMethods(String requestURI) {
        List<Methods> allowedMethods = new ArrayList<>();
        allowedMethods.add(GET);
        return allowedMethods;
    }

    public boolean methodIsAllowed(String requestURI, String method) {
        return allowedMethods(requestURI).contains(Methods.valueOf(method));
    }

    public boolean expectedResponse(String requestURI, HTTPResponse httpResponse) {
        for (Route route : Route.values()) {
            if (route.url.equals(requestURI) && !route.expectedCode.equals(httpResponse.getStatusCode())) {
                return false;
            }
        }
        return true;
    }

    public Route getRoute(String requestURI) {
        for (Route route : Route.values()) {
            if (requestURI.equals(route.url)) {
                return route;
            }
        }
        return null;
    }

    public enum Methods {
        GET
    }

    public enum Route {
        COFFEE ("418", "/coffee"),
        TEA ("200", "/tea");

        public final String expectedCode;
        public final String url;

        Route(String code, String url) {
            this.expectedCode = code;
            this.url = url;
        }
    }
}
