package httpserver.httpmessages;

import httpserver.RequestHandler;
import httpserver.ResponseBuilder;

public class HTTPRequestHandler implements RequestHandler {

    private final ResponseBuilder responseBuilder;

    public HTTPRequestHandler(ResponseBuilder responseBuilder) {
        this.responseBuilder = responseBuilder;
    }

    public HTTPResponse handle(HTTPRequest httpRequest) {
        if (httpRequest.getRequestURI().equals("/")) {
            return responseBuilder.buildResponse(httpRequest.getMethod(), httpRequest.getRequestURI());
        }
        return new HTTPResponse(404, "OK");
    }
}
