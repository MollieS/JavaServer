package httpserver.routing;

import httpserver.Resource;
import httpserver.Response;
import httpserver.httprequests.HTTPRequest;
import httpserver.httpresponse.HTTPResponse;
import httpserver.httpresponse.ResponseHeader;
import httpserver.resourcemanagement.HTTPResource;

import java.util.HashMap;

import static httpserver.httpresponse.StatusCode.OK;

public class CookieRoute extends Route {

    private static final String URI = "/cookie";
    private static final byte[] body = "Eat".getBytes();
    private final HashMap<ResponseHeader, byte[]> headers;

    public CookieRoute(Method... methods) {
        super(URI, methods);
        this.headers = getHeaders();
    }

    @Override
    public Response performAction(HTTPRequest httpRequest) {
        Resource resource = new HTTPResource(body);
        if (httpRequest.hasParams()) {
            headers.put(ResponseHeader.COOKIE, httpRequest.getParams().getBytes());
            return HTTPResponse.create(OK).withBody(resource).withHeaders(headers);
        }
        return HTTPResponse.create(OK).withBody(resource);
    }
}
