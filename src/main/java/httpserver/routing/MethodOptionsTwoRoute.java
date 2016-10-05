package httpserver.routing;

import httpserver.httpmessages.HTTPRequest;
import httpserver.httpmessages.HTTPResponse;

public class MethodOptionsTwoRoute extends Route {

    public MethodOptionsTwoRoute(Method... methods) {
        super("/method_options2", methods);
    }

    @Override
    public HTTPResponse performAction(HTTPRequest httpRequest) {
        HTTPResponse httpResponse = new HTTPResponse(200, "OK");
        httpResponse.setAllowedMethods(super.getAllowedMethods());
        return httpResponse;
    }
}
