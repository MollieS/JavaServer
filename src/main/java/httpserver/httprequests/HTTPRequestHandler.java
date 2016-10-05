package httpserver.httprequests;

import httpserver.RequestHandler;
import httpserver.ResourceHandler;
import httpserver.httpresponse.HTTPResponse;
import httpserver.httpresponse.HTTPResponseDate;
import httpserver.resourcemanagement.FileResource;
import httpserver.routing.Router;

import static httpserver.httpresponse.StatusCode.*;

public class HTTPRequestHandler implements RequestHandler {

    private final ResourceHandler resourceHandler;
    private final Router router;

    public HTTPRequestHandler(ResourceHandler resourceHandler, Router router) {
        this.resourceHandler = resourceHandler;
        this.router = router;
    }

    public HTTPResponse handle(HTTPRequest httpRequest) {
        return router.getResponse(httpRequest);
    }

    private HTTPResponse getOKResponse() {
        return new HTTPResponse(OK.code, OK.reason, new HTTPResponseDate());
    }

    private HTTPResponse getNotFoundResponse() {
        return new HTTPResponse(NOTFOUND.code, NOTFOUND.reason, new HTTPResponseDate());
    }

    private void setBody(FileResource resource, HTTPResponse httpResponse) {
        httpResponse.setContentType(resource.getContentType());
        httpResponse.setBody(resource.getContents());
    }
}
