package httpserver.routing;

import httpserver.httpmessages.HTTPResponse;

import static httpserver.httpmessages.HTTPResponseCode.OK;

public class MethodOptionsRoute extends Route {

    public MethodOptionsRoute(String uri, Method... methods) {
        super(uri, methods);
    }

    @Override
    public HTTPResponse performAction(Method method) {
        HTTPResponse httpResponse;
        if (super.methodIsAllowed(method)) {
            httpResponse = new HTTPResponse(OK.code, OK.reason);
            if (method == Method.OPTIONS) {
                httpResponse.setAllowedMethods(super.getAllowedMethods());
            }
            return httpResponse;
        }
        return super.methodNotAllowed();
    }
}
