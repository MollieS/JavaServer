package httpserver.routing;

import httpserver.httpmessages.HTTPRequest;
import httpserver.httpmessages.HTTPResponse;
import httpserver.httpmessages.StatusCode;

public class TeaRoute extends Route {

    public TeaRoute(Method... methods) {
        super("/tea", methods);
    }

    @Override
    public HTTPResponse performAction(HTTPRequest httpRequest) {
        if (super.methodIsAllowed(httpRequest.getMethod())) {
            return new HTTPResponse(StatusCode.OK.code, StatusCode.OK.reason);
        }
        return super.methodNotAllowed();
    }
}
