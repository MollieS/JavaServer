package httpserver.httprequests;

import httpserver.RequestHandler;
import httpserver.ResourceHandler;
import httpserver.httpresponse.HTTPResponse;
import httpserver.httpresponse.HTTPResponseDate;
import httpserver.resourcemanagement.Resource;
import httpserver.routing.Router;

import static httpserver.httpresponse.StatusCode.NOTALLOWED;
import static httpserver.httpresponse.StatusCode.NOTFOUND;
import static httpserver.httpresponse.StatusCode.OK;
import static httpserver.routing.Method.GET;

public class HTTPRequestHandler implements RequestHandler {

    private final ResourceHandler resourceHandler;
    private final Router router;

    public HTTPRequestHandler(ResourceHandler resourceHandler, Router router) {
        this.resourceHandler = resourceHandler;
        this.router = router;
    }

    public HTTPResponse handle(HTTPRequest httpRequest) {
        Resource resource = resourceHandler.getResource(httpRequest.getRequestURI());
        if (router.hasRegistered(httpRequest.getRequestURI())) {
            return router.getResponse(httpRequest);
        }
        if (resource.exists()) {
            if (router.allowsMethod(httpRequest.getMethod())) {
                HTTPResponse httpResponse = getOKResponse();
                if (httpRequest.getMethod() == GET) {
                    setBody(resource, httpResponse);
                }
                return httpResponse;
            }
            return new HTTPResponse(NOTALLOWED.code, NOTALLOWED.reason, new HTTPResponseDate());
        }
        return getNotFoundResponse();
    }

    private HTTPResponse getOKResponse() {
        return new HTTPResponse(OK.code, OK.reason, new HTTPResponseDate());
    }

    private HTTPResponse getNotFoundResponse() {
        return new HTTPResponse(NOTFOUND.code, NOTFOUND.reason, new HTTPResponseDate());
    }

    private void setBody(Resource resource, HTTPResponse httpResponse) {
        httpResponse.setContentType(resource.getContentType());
        httpResponse.setBody(resource.getContents());
    }
}
