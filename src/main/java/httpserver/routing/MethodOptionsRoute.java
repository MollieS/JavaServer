package httpserver.routing;

import httpserver.httprequests.HTTPRequest;
import httpserver.httpresponse.HTTPResponse;
import httpserver.httpresponse.HTTPResponseDate;

import static httpserver.httpresponse.StatusCode.OK;

public class MethodOptionsRoute extends Route {

    public MethodOptionsRoute(Method... methods) {
        super("/method_options", methods);
    }

    @Override
    public HTTPResponse performAction(HTTPRequest httpRequest) {
        HTTPResponse httpResponse;
        if (methodIsAllowed(httpRequest.getMethod())) {
            httpResponse = new HTTPResponse(OK.code, OK.reason, new HTTPResponseDate());
            if (httpRequest.getMethod() == Method.OPTIONS) {
                httpResponse.setAllowedMethods(super.getAllowedMethods());
            }
            return httpResponse;
        }
        return super.methodNotAllowed();
    }
}
