package httpserver.routing;

import httpserver.httprequests.HTTPRequest;
import httpserver.httpresponse.HTTPResponse;
import httpserver.httpresponse.ResponseMessage;

import static httpserver.httpresponse.StatusCode.TEAPOT;

public class CoffeeRoute extends Route {

    private static final String URI = "/coffee";

    public CoffeeRoute(Method... methods) {
        super(URI, methods);
    }

    public HTTPResponse performAction(HTTPRequest httpRequest) {
        if (super.methodIsAllowed(httpRequest.getMethod())) {
            return ResponseMessage.create(TEAPOT);
        }
        return super.methodNotAllowed();
    }
}
