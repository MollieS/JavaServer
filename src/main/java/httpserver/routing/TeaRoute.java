package httpserver.routing;

import httpserver.httprequests.HTTPRequest;
import httpserver.httpresponse.HTTPResponse;
import httpserver.httpresponse.HTTPResponseDate;
import httpserver.httpresponse.StatusCode;

public class TeaRoute extends Route {

    public TeaRoute(Method... methods) {
        super("/tea", methods);
    }

    @Override
    public HTTPResponse performAction(HTTPRequest httpRequest) {
        if (super.methodIsAllowed(httpRequest.getMethod())) {
            return new HTTPResponse(StatusCode.OK.code, StatusCode.OK.reason, new HTTPResponseDate());
        }
        return super.methodNotAllowed();
    }
}
