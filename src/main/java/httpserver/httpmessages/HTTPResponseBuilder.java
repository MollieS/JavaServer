package httpserver.httpmessages;

import httpserver.ResourceHandler;
import httpserver.ResponseBuilder;
import httpserver.resourcemanagement.Resource;

public class HTTPResponseBuilder implements ResponseBuilder {

    private final ResourceHandler resourceHandler;

    public HTTPResponseBuilder(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    public HTTPResponse buildResponse(String method, String path) {
        Resource resource = resourceHandler.getResource(path);
        if (resource.exists()) {
            HTTPResponse httpResponse = new HTTPResponse(200, "OK");
            httpResponse.setBody(resource.getContents());
            return httpResponse;
        }
        return new HTTPResponse(404, "Not Found");
    }
}
