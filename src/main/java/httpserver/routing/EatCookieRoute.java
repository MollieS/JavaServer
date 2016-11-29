package httpserver.routing;

import httpserver.Request;
import httpserver.Resource;
import httpserver.Response;
import httpserver.httpresponse.HTTPResponse;
import httpserver.resourcemanagement.HTTPResource;

import static httpserver.httprequests.RequestHeader.COOKIE;
import static httpserver.httpresponse.StatusCode.OK;

public class EatCookieRoute extends Route {
    private final static String URI = "/eat_cookie";

    public EatCookieRoute(Method... methods) {
        super(URI, methods);
    }

    @Override
    public Response performAction(Request httpRequest) {
        if (httpRequest.hasHeader(COOKIE)) {
            String type = httpRequest.getValue(COOKIE);
            Resource resource = new HTTPResource(("mmmm " + type).getBytes());
            return HTTPResponse.create(OK).withBody(resource);
        }
        return HTTPResponse.create(OK);
    }
}
