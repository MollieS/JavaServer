package httpserver.routing;

import httpserver.Request;
import httpserver.Response;
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
        this.headers = getResponseHeaders();
    }

    @Override
    public Response performAction(Request httpRequest) {
        headers.put(LOCATION, (baseLocation + "/").getBytes());
        return HTTPResponse.create(REDIRECT).withHeaders(headers);
    }
}
