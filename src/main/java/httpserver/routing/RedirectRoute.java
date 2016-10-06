package httpserver.routing;

import httpserver.Response;
import httpserver.httprequests.HTTPRequest;
import httpserver.httpresponse.HTTPResponse;
import httpserver.httpresponse.ResponseHeader;

import java.util.HashMap;

import static httpserver.httpresponse.ResponseHeader.LOCATION;
import static httpserver.httpresponse.StatusCode.REDIRECT;

public class RedirectRoute extends Route {

    private final String baseLocation;
    private final HashMap<ResponseHeader, byte[]> headers;

    public RedirectRoute(String baseLocation, Method... methods) {
        super("/redirect", methods);
        this.baseLocation = baseLocation;
        this.headers = getHeaders();
    }

    @Override
    public Response performAction(HTTPRequest httpRequest) {
        headers.put(LOCATION, (baseLocation + "/").getBytes());
        return HTTPResponse.create(REDIRECT).withHeaders(headers);
    }
}
