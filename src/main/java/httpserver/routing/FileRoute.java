package httpserver.routing;

import httpserver.Request;
import httpserver.ResourceHandler;
import httpserver.Response;
import httpserver.httpresponse.HTTPResponse;
import httpserver.resourcemanagement.FileResource;

import static httpserver.httpresponse.StatusCode.*;
import static httpserver.routing.Method.GET;
import static httpserver.routing.Method.HEAD;

public class FileRoute extends Route {

    private final ResourceHandler resourceHandler;

    public FileRoute(ResourceHandler resourceHandler) {
        super("/", GET, HEAD);
        this.resourceHandler = resourceHandler;
    }

    @Override
    public Response performAction(Request httpRequest) {
        if (!methodIsAllowed(httpRequest.getMethod())) {
            return methodNotAllowed();
        }
        FileResource resource = resourceHandler.getResource(httpRequest.getRequestURI());
        if (resource.exists()) {
            HTTPResponse responseMessage = HTTPResponse.create(OK);
            if (httpRequest.getMethod() == GET) {
                return responseMessage.withBody(resource);
            }
            return responseMessage;
        }
        return HTTPResponse.create(NOTFOUND);
    }
}
