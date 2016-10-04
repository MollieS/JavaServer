package httpserver.routing;

import httpserver.httpmessages.HTTPRequest;
import httpserver.httpmessages.HTTPResponse;

import static httpserver.httpmessages.StatusCode.REDIRECT;

public class RedirectRoute extends Route {

    private final String baseLocation;

    public RedirectRoute(String uri, String baseLocation, Method... methods) {
        super(uri, methods);
        this.baseLocation = baseLocation;
    }

    @Override
    public HTTPResponse performAction(HTTPRequest httpRequest) {
        HTTPResponse httpResonse = new HTTPResponse(REDIRECT.code, REDIRECT.reason);
        httpResonse.setLocation(baseLocation + "/");
        return httpResonse;
    }
}
