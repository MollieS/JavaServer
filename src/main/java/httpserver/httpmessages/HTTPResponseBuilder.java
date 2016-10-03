package httpserver.httpmessages;

import httpserver.ResourceHandler;
import httpserver.ResponseBuilder;
import httpserver.resourcemanagement.Resource;
import httpserver.routing.Method;

import java.net.URI;

import static httpserver.httpmessages.HTTPResponseCode.NOTFOUND;
import static httpserver.httpmessages.HTTPResponseCode.OK;
import static httpserver.routing.Method.GET;

public class HTTPResponseBuilder implements ResponseBuilder {

    private final ResourceHandler resourceHandler;

    public HTTPResponseBuilder(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    public HTTPResponse buildResponse(Method method, URI path) {
        Resource resource = resourceHandler.getResource(path);
        if (resource.exists()) {
            HTTPResponse httpResponse = getOKResponse();
            if (method == GET) {
                setBody(resource, httpResponse);
            }
            return httpResponse;
        } else {
            return getNotFoundResponse();
        }
    }

    private HTTPResponse getNotFoundResponse() {
        return new HTTPResponse(NOTFOUND.code, NOTFOUND.reason);
    }

    private HTTPResponse getOKResponse() {
        HTTPResponse httpResponse = new HTTPResponse(OK.code, OK.reason);
        return httpResponse;
    }

    private void setBody(Resource resource, HTTPResponse httpResponse) {
        httpResponse.setContentType(resource.getContentType());
        httpResponse.setBody(resource.getContents());
    }
}
