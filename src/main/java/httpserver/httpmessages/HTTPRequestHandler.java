package httpserver.httpmessages;

import httpserver.RequestHandler;
import httpserver.ResponseBuilder;
import httpserver.routing.Router;

public class HTTPRequestHandler implements RequestHandler {

    private final ResponseBuilder responseBuilder;
    private final Router router;

    public HTTPRequestHandler(ResponseBuilder responseBuilder, Router router) {
        this.responseBuilder = responseBuilder;
        this.router = router;
    }

    public HTTPResponse handle(HTTPRequest httpRequest) {
        HTTPResponse httpResponse;
        if (router.hasRegistered(httpRequest.getRequestURI())) {
            httpResponse = router.getResponse(httpRequest);
        } else {
            if(router.allowsMethod(httpRequest.getMethod())) {
                httpResponse = responseBuilder.buildResponse(httpRequest.getMethod(), httpRequest.getRequestURI());
            } else {
                httpResponse = new HTTPResponse(405, "Not allowed");
            }
        }
        return httpResponse;
    }

}
