package httpserver.routing;

import httpserver.Request;
import httpserver.Resource;
import httpserver.ResourceHandler;
import httpserver.Response;
import httpserver.httpresponse.HTTPResponse;

import java.net.URI;

import static httpserver.httprequests.RequestHeader.BODY;
import static httpserver.httprequests.RequestHeader.ETAG;
import static httpserver.httpresponse.StatusCode.NOCONTENT;
import static httpserver.httpresponse.StatusCode.OK;
import static httpserver.routing.Method.GET;
import static httpserver.routing.Method.PATCH;

public class PatchContentRoute extends Route {
    private final ResourceHandler resourceHandler;
    private String latestVersion;

    public PatchContentRoute(ResourceHandler resourceHandler, Method... methods) {
        super("/patch-content.txt", methods);
        this.resourceHandler = resourceHandler;
    }

    @Override
    public Response performAction(Request request) {
        if (request.getMethod() == GET) {
            Resource resource = getCorrectResource(request);
            return HTTPResponse.create(OK).withBody(resource);
        }
        if (request.getMethod() == PATCH) {
            updateResourceWithEtag(request);
        }
        return HTTPResponse.create(NOCONTENT);
    }

    private void updateResourceWithEtag(Request request) {
        if (request.hasHeader(ETAG)) {
            String etag = request.getValue(ETAG);
            resourceHandler.createResource(request.getRequestURI(), etag, request.getValue(BODY));
            this.latestVersion = request.getRequestURI().getPath() + etag;
        }
    }

    private Resource getCorrectResource(Request request) {
        if (this.latestVersion != null) {
            Resource resource = resourceHandler.getResource(URI.create(latestVersion));
            return resource;
        } else {
             return resourceHandler.getResource(request.getRequestURI());
        }
    }
}
