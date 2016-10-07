package httpserver.routing;

import httpserver.Request;
import httpserver.Response;
import httpserver.httpresponse.HTTPResponse;
import httpserver.httpresponse.ResponseHeader;

import java.util.HashMap;

import static httpserver.httpresponse.ResponseHeader.ALLOW;
import static httpserver.httpresponse.StatusCode.OK;

public class MethodOptionsRoute extends Route {

    private final HashMap<ResponseHeader, byte[]> headers;

    public MethodOptionsRoute(Method... methods) {
        super("/method_options", methods);
        this.headers = getResponseHeaders();
    }

    @Override
    public Response performAction(Request httpRequest) {
        HTTPResponse httpResponse;
        if (methodIsAllowed(httpRequest.getMethod())) {
            httpResponse = HTTPResponse.create(OK);
            if (httpRequest.getMethod() == Method.OPTIONS) {
                byte[] allowedMethods = formatAllowedMethods();
                headers.put(ALLOW, allowedMethods);
                httpResponse.withHeaders(headers);
            }
            return httpResponse;
        }
        return super.methodNotAllowed();
    }
}
