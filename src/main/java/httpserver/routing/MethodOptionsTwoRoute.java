package httpserver.routing;

import httpserver.Response;
import httpserver.httprequests.HTTPRequest;
import httpserver.httpresponse.HTTPResponse;
import httpserver.httpresponse.ResponseHeader;

import java.util.HashMap;

import static httpserver.httpresponse.ResponseHeader.ALLOW;
import static httpserver.httpresponse.StatusCode.OK;

public class MethodOptionsTwoRoute extends Route {

    private final HashMap<ResponseHeader, byte[]> headers;

    public MethodOptionsTwoRoute(Method... methods) {
        super("/method_options2", methods);
        this.headers = getHeaders();
    }

    @Override
    public Response performAction(HTTPRequest httpRequest) {
        HTTPResponse httpResponse = HTTPResponse.create(OK);
        headers.put(ALLOW, formatAllowedMethods());
        httpResponse.withHeaders(headers);
        return httpResponse;
    }
}
