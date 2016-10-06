package httpserver.routing;

import httpserver.Resource;
import httpserver.Response;
import httpserver.httprequests.HTTPRequest;
import httpserver.httpresponse.HTTPResponse;
import httpserver.resourcemanagement.HTTPResource;

import static httpserver.httpresponse.StatusCode.OK;

public class EatCookieRoute extends Route {
    private final static String URI = "/eat_cookie";

    public EatCookieRoute(Method... methods) {
        super(URI, methods);
    }

    @Override
    public Response performAction(HTTPRequest httpRequest) {
        if (httpRequest.hasCookie()) {
            String type = httpRequest.getCookie().split("=")[1];
            Resource resource = new HTTPResource(("mmmm " + type).getBytes());
            return HTTPResponse.create(OK).withBody(resource);
        }
        return HTTPResponse.create(OK);
    }
}
