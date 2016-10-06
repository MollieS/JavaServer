package httpserver.httpmessages;

import httpserver.RequestHandler;
import httpserver.ResourceHandler;
import httpserver.resourcemanagement.Resource;
import httpserver.routing.Router;

import static httpserver.httpmessages.StatusCode.NOTALLOWED;
import static httpserver.httpmessages.StatusCode.NOTFOUND;
import static httpserver.httpmessages.StatusCode.OK;
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
        if (resource.exists()) {
            if (router.allowsMethod(httpRequest.getMethod())) {
                HTTPResponse httpResponse = getOKResponse();
                if (httpRequest.getMethod() == GET) {
                    setBody(resource, httpResponse);
                }
                return httpResponse;
            }
            return new HTTPResponse(NOTALLOWED.code, NOTALLOWED.reason);
        }
        if (router.hasRegistered(httpRequest.getRequestURI())) {
            return router.getResponse(httpRequest);
        }
        return getNotFoundResponse();
    }

    private HTTPResponse getOKResponse() {
        return new HTTPResponse(OK.code, OK.reason);
    }

    private HTTPResponse getNotFoundResponse() {
        return new HTTPResponse(NOTFOUND.code, NOTFOUND.reason);
    }

    private void setBody(Resource resource, HTTPResponse httpResponse) {
        httpResponse.setContentType(resource.getContentType());
        httpResponse.setBody(resource.getContents());
    }

}
