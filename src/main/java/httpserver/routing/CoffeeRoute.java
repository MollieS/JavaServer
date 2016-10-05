package httpserver.routing;

import httpserver.httpmessages.HTTPRequest;
import httpserver.httpmessages.HTTPResponse;

import static httpserver.httpmessages.StatusCode.TEAPOT;

public class CoffeeRoute extends Route {

    public CoffeeRoute(Method... methods) {
        super("/coffee", methods);
    }

    public HTTPResponse performAction(HTTPRequest httpRequest) {
        if (super.methodIsAllowed(httpRequest.getMethod())) {
            HTTPResponse httpResponse = new HTTPResponse(TEAPOT.code, TEAPOT.reason);
            httpResponse.setContentType("text/plain");
            httpResponse.setBody(TEAPOT.reason.getBytes());
            return httpResponse;
        }
        return super.methodNotAllowed();
    }
}
