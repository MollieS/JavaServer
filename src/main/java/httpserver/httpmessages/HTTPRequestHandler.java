package httpserver.httpmessages;

import httpserver.RequestHandler;
import httpserver.ResponseBuilder;

public class HTTPRequestHandler implements RequestHandler {

    private final ResponseBuilder responseBuilder;

    public HTTPRequestHandler(ResponseBuilder responseBuilder) {
        this.responseBuilder = responseBuilder;
    }

    public HTTPResponse handle(HTTPRequest httpRequest) {
        return responseBuilder.buildResponse(httpRequest.getMethod(), httpRequest.getRequestURI());
    }
}
