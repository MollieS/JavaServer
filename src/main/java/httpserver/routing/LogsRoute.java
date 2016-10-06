package httpserver.routing;

import httpserver.Response;
import httpserver.httprequests.HTTPRequest;
import httpserver.httpresponse.HTTPResponse;
import httpserver.httpresponse.ResponseHeader;

import java.util.HashMap;

import static httpserver.httpresponse.StatusCode.UNAUTHORIZED;

public class LogsRoute extends Route {

    private final static String URI = "/logs";
    private final static String AUTHENTICATION_SCHEME = "Basic";
    private final HashMap<ResponseHeader, byte[]> headers;

    public LogsRoute(Method... methods) {
        super(URI, methods);
        this.headers = getHeaders();
    }

    @Override
    public Response performAction(HTTPRequest httpRequest) {
        headers.put(ResponseHeader.AUTH, (AUTHENTICATION_SCHEME + " " + "realm=" + URI).getBytes());
        return HTTPResponse.create(UNAUTHORIZED).withHeaders(headers);
    }
}
