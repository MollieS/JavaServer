package httpserver.routing;

import httpserver.httpmessages.HTTPResponse;
import org.junit.Test;

import static httpserver.routing.Method.GET;
import static httpserver.routing.Method.POST;
import static org.junit.Assert.assertEquals;

public class TeaRouteTest {

    @Test
    public void sendsA200ReponseForAGet() {
        TeaRoute teaRoute = new TeaRoute("/tea", GET);

        HTTPResponse httpResponse = teaRoute.performAction(GET);

        assertEquals("200", httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
    }

    @Test
    public void sendsA405IfMethodNotAllowed() {
        TeaRoute teaRoute = new TeaRoute("/tea", GET);

        HTTPResponse httpResponse = teaRoute.performAction(POST);

        assertEquals("405", httpResponse.getStatusCode());
    }
}
