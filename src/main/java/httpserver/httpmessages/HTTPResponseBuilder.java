package httpserver.httpmessages;

import httpserver.ResourceHandler;
import httpserver.ResponseBuilder;
import httpserver.resourcemanagement.Resource;

import static httpserver.httpmessages.HTTPResponses.*;

public class HTTPResponseBuilder implements ResponseBuilder {

    private final ResourceHandler resourceHandler;

    public HTTPResponseBuilder(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    public HTTPResponse buildResponse(String method, String path) {
        Resource resource = resourceHandler.getResource(path);
        if (resource.exists()) {
            HTTPResponse httpResponse = new HTTPResponse(OK.code, OK.reason);
            httpResponse.setContentType(resource.getContentType());
            httpResponse.setBody(resource.getContents());
            return httpResponse;
        }
        return new HTTPResponse(NOTFOUND.code, NOTFOUND.reason);
    }
}
