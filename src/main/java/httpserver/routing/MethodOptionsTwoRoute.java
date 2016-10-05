package httpserver.routing;

import httpserver.httpmessages.HTTPRequest;
import httpserver.httpmessages.HTTPResponse;

public class MethodOptionsTwoRoute extends Route {

    public MethodOptionsTwoRoute(String uri, Method... methods) {
        super(uri, methods);
    }

    @Override
    public HTTPResponse performAction(HTTPRequest httpRequest) {
        HTTPResponse httpResponse = new HTTPResponse(200, "OK");
        httpResponse.setAllowedMethods(super.getAllowedMethods());
        return httpResponse;
    }
}
