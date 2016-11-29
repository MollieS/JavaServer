package httpserver.routing;

import httpserver.HTTPRouter;
import httpserver.Request;
import httpserver.Response;
import httpserver.httpresponse.HTTPResponse;
import httpserver.httpresponse.StatusCode;

import java.net.URI;
import java.util.List;

public class Router implements HTTPRouter {

    private final List<Route> registeredRoutes;

    public Router(List<Route> registeredRoutes) {
        this.registeredRoutes = registeredRoutes;
    }

    public Response route(Request httpRequest) {
        for (Route route : registeredRoutes) {
            if (isRegistered(httpRequest.getRequestURI(), route)) {
                return route.performAction(httpRequest);
            }
        }
        return HTTPResponse.create(StatusCode.NOTFOUND);
    }

    private boolean isRegistered(URI uri, Route route) {
        return route.getUri().equals(uri);
    }
}
