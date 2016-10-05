package httpserver.routing;

import httpserver.httprequests.HTTPRequest;
import httpserver.httpresponse.HTTPResponse;
import httpserver.httpresponse.HTTPResponseDate;

import static httpserver.httpresponse.StatusCode.REDIRECT;

public class RedirectRoute extends Route {

    private final String baseLocation;

    public RedirectRoute(String baseLocation, Method... methods) {
        super("/redirect", methods);
        this.baseLocation = baseLocation;
    }

    @Override
    public HTTPResponse performAction(HTTPRequest httpRequest) {
        HTTPResponse httpResponse = new HTTPResponse(REDIRECT.code, REDIRECT.reason, new HTTPResponseDate());
        httpResponse.setLocation(baseLocation + "/");
        return httpResponse;
    }
}
