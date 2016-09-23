package httpserver.httpmessages;

import httpserver.RequestHandler;
import httpserver.ResponseBuilder;
import httpserver.routing.Router;

import static httpserver.httpmessages.HTTPResponseCode.NOTALLOWED;

public class HTTPRequestHandler implements RequestHandler {

    private final ResponseBuilder responseBuilder;
    private final Router router;

    public HTTPRequestHandler(ResponseBuilder responseBuilder, Router router) {
        this.responseBuilder = responseBuilder;
        this.router = router;
    }

    public HTTPResponse handle(HTTPRequest httpRequest) {
        if (router.methodIsAllowed(httpRequest.getRequestURI(), httpRequest.getMethod())) {
            HTTPResponse httpResponse;
            if (router.routeIsRegistered(httpRequest.getRequestURI())) {
                // do action required
                Router.RegisteredRoute registeredRoute = router.getRoute(httpRequest.getRequestURI());
                httpResponse = overrideResponse(registeredRoute);
            } else {
                httpResponse = responseBuilder.buildResponse(httpRequest.getMethod(), httpRequest.getRequestURI());
            }
            responseBuilder.addAllowedMethods(httpResponse, httpRequest, router);
            return httpResponse;

        }
        return new HTTPResponse(NOTALLOWED.code, NOTALLOWED.reason);
    }

    private HTTPResponse overrideResponse(Router.RegisteredRoute registeredRoute) {
        HTTPResponse httpResponse;
        HTTPResponseCode expectedResponse = findExpectedResponse(registeredRoute);
        httpResponse = new HTTPResponse(expectedResponse.code, expectedResponse.reason);
        httpResponse.setBody(expectedResponse.reason.getBytes());
        return httpResponse;
    }

    private HTTPResponseCode findExpectedResponse(Router.RegisteredRoute registeredRoute) {
        for (HTTPResponseCode response : HTTPResponseCode.values()) {
            if (response.code == Integer.valueOf(registeredRoute.expectedCode)) {
                return response;
            }
        }
        return null;
    }
}
