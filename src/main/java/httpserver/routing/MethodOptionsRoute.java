package httpserver.routing;

import httpserver.httpmessages.HTTPRequest;
import httpserver.httpmessages.HTTPResponse;

import static httpserver.httpmessages.HTTPResponseCode.OK;

public class MethodOptionsRoute extends Route {

    public MethodOptionsRoute(String uri, Method... methods) {
        super(uri, methods);
    }

    @Override
    public HTTPResponse performAction(HTTPRequest httpRequest) {
        HTTPResponse httpResponse;
        if (methodIsAllowed(httpRequest.getMethod())) {
            httpResponse = new HTTPResponse(OK.code, OK.reason);
            if (httpRequest.getMethod() == Method.OPTIONS) {
                httpResponse.setAllowedMethods(super.getAllowedMethods());
            }
            return httpResponse;
        }
        return super.methodNotAllowed();
    }
}
