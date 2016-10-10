package httpserver.routing;

import httpserver.Request;
import httpserver.Resource;
import httpserver.Response;
import httpserver.httpresponse.HTTPResponse;
import httpserver.resourcemanagement.HTTPResource;

import static httpserver.httpresponse.StatusCode.TEAPOT;

public class CoffeeRoute extends Route {

    private static final String URI = "/coffee";

    public CoffeeRoute(Method... methods) {
        super(URI, methods);
    }

    public Response performAction(Request httpRequest) {
        if (methodIsAllowed(httpRequest.getMethod())) {
            Resource resource = new HTTPResource(TEAPOT.reason.getBytes());
            return HTTPResponse.create(TEAPOT).withBody(resource);
        }
        return methodNotAllowed();
    }
}
