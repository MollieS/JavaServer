package httpserver.routing;

import httpserver.httpmessages.HTTPResponse;
import httpserver.httpmessages.HTTPResponseCode;

public class TeaRoute extends Route {

    public TeaRoute(String uri, Method...methods) {
        super(uri, methods);
    }

    @Override
    public HTTPResponse performAction(Method method) {
        if (super.methodIsAllowed(method)) {
            return new HTTPResponse(HTTPResponseCode.OK.code, HTTPResponseCode.OK.reason);
        }
        return super.methodNotAllowed();
    }
}
