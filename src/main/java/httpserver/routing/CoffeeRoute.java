package httpserver.routing;

import httpserver.Resource;
import httpserver.Response;
import httpserver.httprequests.HTTPRequest;
import httpserver.httpresponse.HTTPResponse;
import httpserver.resourcemanagement.HTTPResource;

import static httpserver.httpresponse.StatusCode.TEAPOT;

public class CoffeeRoute extends Route {

    private static final String URI = "/coffee";

    public CoffeeRoute(Method... methods) {
        super(URI, methods);
    }

    public Response performAction(HTTPRequest httpRequest) {
        if (super.methodIsAllowed(httpRequest.getMethod())) {
            Resource resource = new HTTPResource(TEAPOT.reason.getBytes());
            return HTTPResponse.create(TEAPOT).withBody(resource);
        }
        return super.methodNotAllowed();
    }
}
