package httpserver.routing;

import httpserver.httpmessages.HTTPRequest;
import httpserver.httpmessages.HTTPResponse;
import org.junit.Test;

import static httpserver.routing.Method.GET;
import static httpserver.routing.Method.POST;
import static org.junit.Assert.assertEquals;

public class TeaRouteTest {

    private TeaRoute teaRoute = new TeaRoute("/tea", GET);

    @Test
    public void sendsA200ReponseForAGet() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/tea");

        HTTPResponse httpResponse = teaRoute.performAction(httpRequest);

        assertEquals(200, httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
    }

    @Test
    public void sendsA405IfMethodNotAllowed() {
        HTTPRequest httpRequest = new HTTPRequest(POST, "/tea");

        HTTPResponse httpResponse = teaRoute.performAction(httpRequest);

        assertEquals(405, httpResponse.getStatusCode());
    }
}
