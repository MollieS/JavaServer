package httpserver.routing;

import httpserver.Request;
import httpserver.Response;
import httpserver.httpresponse.HTTPResponse;

import static httpserver.httpresponse.StatusCode.OK;

public class TeaRoute extends Route {

    private static final String URI = "/tea";

    public TeaRoute(Method... methods) {
        super(URI, methods);
    }

    @Override
    public Response performAction(Request httpRequest) {
        if (super.methodIsAllowed(httpRequest.getMethod())) {
            return HTTPResponse.create(OK);
        }
        return super.methodNotAllowed();
    }
}
