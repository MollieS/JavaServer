package httpserver.routing;

import httpserver.httpmessages.HTTPResponse;

import static httpserver.httpmessages.HTTPResponseCode.TEAPOT;

public class CoffeeRoute extends Route {

    public CoffeeRoute(String uri, Method... methods) {
        super(uri, methods);
    }

    public HTTPResponse performAction(Method method) {
        if (super.methodIsAllowed(method)) {
            HTTPResponse httpResponse = new HTTPResponse(TEAPOT.code, TEAPOT.reason);
            httpResponse.setContentType("text/plain");
            httpResponse.setBody(TEAPOT.reason.getBytes());
            return httpResponse;
        }
        return super.methodNotAllowed();
    }
}
