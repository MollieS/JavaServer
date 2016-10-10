package httpserver.routing;

import httpserver.Request;
import httpserver.Resource;
import httpserver.ResourceHandler;
import httpserver.Response;
import httpserver.httprequests.RequestHeader;
import httpserver.httpresponse.HTTPResponse;
import httpserver.resourcemanagement.FileResource;
import httpserver.resourcemanagement.HTTPResource;

import java.util.Arrays;

import static httpserver.httpresponse.StatusCode.OK;
import static httpserver.httpresponse.StatusCode.PARTIAL;

public class PartialContentRoute extends Route {

    private final ResourceHandler resourceHandler;

    public PartialContentRoute(ResourceHandler resourceHandler, Method... methods) {
        super("/partial_content.txt", methods);
        this.resourceHandler = resourceHandler;
    }

    @Override
    public Response performAction(Request httpRequest) {
        FileResource resource = resourceHandler.getResource(super.getUri());
        if (httpRequest.hasHeader(RequestHeader.RANGE)) {
            Resource partialResource = getPartialContent(httpRequest, resource);
            return HTTPResponse.create(PARTIAL).withBody(partialResource);
        }
        return HTTPResponse.create(OK).withBody(resource);
    }

    private HTTPResource getPartialContent(Request httpRequest, FileResource resource) {
        int rangeStart = getRangeStart(httpRequest, resource);
        int rangeEnd = getRangeEnd(httpRequest, resource);
        byte[] body = Arrays.copyOfRange(resource.getContents(), rangeStart, rangeEnd);
        return new HTTPResource(body);
    }

    private int getRangeEnd(Request httpRequest, FileResource resource) {
        if (httpRequest.hasHeader(RequestHeader.RANGE_END) && httpRequest.hasHeader(RequestHeader.RANGE_START)) {
            String rangeEnd = httpRequest.getValue(RequestHeader.RANGE_END);
            return Integer.valueOf(rangeEnd) + 1;
        }
        return resource.getContents().length;
    }

    private int getRangeStart(Request httpRequest, FileResource resource) {
        if (!httpRequest.hasHeader(RequestHeader.RANGE_START)) {
            int rangeEnd = Integer.parseInt(httpRequest.getValue(RequestHeader.RANGE_END));
            return (resource.getContents().length - (rangeEnd + 1)) + 1;
        }
        return Integer.parseInt(httpRequest.getValue(RequestHeader.RANGE_START));
    }
}
