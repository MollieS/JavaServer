package httpserver.httpmessages;

import httpserver.RequestHandler;
import httpserver.ResponseBuilder;
import httpserver.server.Router;

public class HTTPRequestHandler implements RequestHandler {

    private final ResponseBuilder responseBuilder;
    private final Router router;

    public HTTPRequestHandler(ResponseBuilder responseBuilder, Router router) {
        this.responseBuilder = responseBuilder;
        this.router = router;
    }

    public HTTPResponse handle(HTTPRequest httpRequest) {
        if (router.methodIsAllowed(httpRequest.getRequestURI(), httpRequest.getMethod())) {
            HTTPResponse httpResponse = responseBuilder.buildResponse(httpRequest.getMethod(), httpRequest.getRequestURI());
            if (!router.expectedResponse(httpRequest.getRequestURI(), httpResponse)) {
                Router.Route route = router.getRoute(httpRequest.getRequestURI());
                httpResponse = overrideResponse(route);
            }
            return httpResponse;
        }
        return new HTTPResponse(404, "Not Found");
    }

    private HTTPResponse overrideResponse(Router.Route route) {
        HTTPResponse httpResponse;
        HTTPResponses expectedResponse = findExpectedResponse(route);
        httpResponse = new HTTPResponse(expectedResponse.code, expectedResponse.reason);
        httpResponse.setBody(expectedResponse.reason.getBytes());
        return httpResponse;
    }

    private HTTPResponses findExpectedResponse(Router.Route route) {
        for (HTTPResponses response : HTTPResponses.values()) {
            if (response.code == Integer.valueOf(route.expectedCode)) {
                return response;
            }
        }
        return null;
    }
}
