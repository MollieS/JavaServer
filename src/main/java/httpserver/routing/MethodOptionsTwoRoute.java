package httpserver.routing;

import httpserver.httprequests.HTTPRequest;
import httpserver.httpresponse.HTTPResponse;
import httpserver.httpresponse.HTTPResponseDate;

import static httpserver.httpresponse.StatusCode.OK;

public class MethodOptionsTwoRoute extends Route {

    public MethodOptionsTwoRoute(Method... methods) {
        super("/method_options2", methods);
    }

    @Override
    public HTTPResponse performAction(HTTPRequest httpRequest) {
        HTTPResponse httpResponse = new HTTPResponse(OK.code, OK.reason, new HTTPResponseDate());
        httpResponse.setAllowedMethods(super.getAllowedMethods());
        return httpResponse;
    }
}
