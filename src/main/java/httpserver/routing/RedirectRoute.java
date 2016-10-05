package httpserver.routing;

import httpserver.httpmessages.HTTPRequest;
import httpserver.httpmessages.HTTPResponse;

import static httpserver.httpmessages.StatusCode.REDIRECT;

public class RedirectRoute extends Route {

    private final String baseLocation;

    public RedirectRoute(String baseLocation, Method... methods) {
        super("/redirect", methods);
        this.baseLocation = baseLocation;
    }

    @Override
    public HTTPResponse performAction(HTTPRequest httpRequest) {
        HTTPResponse httpResponse = new HTTPResponse(REDIRECT.code, REDIRECT.reason);
        httpResponse.setLocation(baseLocation + "/");
        return httpResponse;
    }
}
