package httpserver.routing;

import httpserver.httpmessages.HTTPRequest;
import httpserver.httpmessages.HTTPResponse;

import static httpserver.httpmessages.StatusCode.OK;

public class ParameterRoute extends Route {

    private final String PARAM_NOTATION = "variable_";

    public ParameterRoute(String uri, Method... methods) {
        super(uri, methods);
    }

    @Override
    public HTTPResponse performAction(HTTPRequest httpRequest) {
        if (methodIsAllowed(httpRequest.getMethod())) {
            HTTPResponse httpResponse = new HTTPResponse(OK.code, OK.reason);
            if (httpRequest.hasParams()) {
                httpResponse.setBody(parseParams(httpRequest));
            }
            return httpResponse;
        }
        return methodNotAllowed();
    }

    private byte[] parseParams(HTTPRequest httpRequest) {

    }
}
