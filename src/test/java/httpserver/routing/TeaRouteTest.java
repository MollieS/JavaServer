package httpserver.routing;

import httpserver.Response;
import httpserver.httprequests.RequestFake;
import org.junit.Test;

import static httpserver.routing.Method.GET;
import static httpserver.routing.Method.POST;
import static org.junit.Assert.assertEquals;

public class TeaRouteTest {

    private TeaRoute teaRoute = new TeaRoute(GET);

    @Test
    public void sendsA200ReponseForAGet() {
        RequestFake httpRequest = new RequestFake(GET, "/tea");

        Response httpResponse = teaRoute.performAction(httpRequest);

        assertEquals(200, httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
    }

    @Test
    public void sendsA405IfMethodNotAllowed() {
        RequestFake httpRequest = new RequestFake(POST, "/tea");

        Response httpResponse = teaRoute.performAction(httpRequest);

        assertEquals(405, httpResponse.getStatusCode());
    }
}
