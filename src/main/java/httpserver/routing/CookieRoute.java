package httpserver.routing;

import httpserver.Request;
import httpserver.Resource;
import httpserver.Response;
import httpserver.httpresponse.HTTPResponse;
import httpserver.httpresponse.ResponseHeader;
import httpserver.resourcemanagement.HTTPResource;

import java.util.HashMap;

import static httpserver.httprequests.RequestHeader.PARAMS;
import static httpserver.httpresponse.StatusCode.OK;

public class CookieRoute extends Route {

    private static final String URI = "/cookie";
    private static final byte[] body = "Eat".getBytes();
    private final HashMap<ResponseHeader, byte[]> headers;

    public CookieRoute(Method... methods) {
        super(URI, methods);
        this.headers = getResponseHeaders();
    }

    @Override
    public Response performAction(Request httpRequest) {
        Resource resource = new HTTPResource(body);
        if (httpRequest.hasHeader(PARAMS)) {
            headers.put(ResponseHeader.COOKIE, httpRequest.getValue(PARAMS).getBytes());
            return HTTPResponse.create(OK).withHeaders(headers).withBody(resource);
        }
        return HTTPResponse.create(OK).withBody(resource);
    }
}
