package httpserver.routing;

import httpserver.Response;
import httpserver.httprequests.RequestFake;
import httpserver.httpresponse.ResponseHeader;
import org.junit.Test;

import static httpserver.ByteArrayConverter.getString;
import static httpserver.routing.Method.GET;
import static org.junit.Assert.assertEquals;

public class RedirectRouteTest {

    private RedirectRoute redirectRoute = new RedirectRoute("http://localhost:5000", GET);

    @Test
    public void sendsA302Response() {
        RequestFake httpRequest = new RequestFake(GET, "/redirect");

        Response httpResponse = redirectRoute.performAction(httpRequest);

        assertEquals(302, httpResponse.getStatusCode());
    }

    @Test
    public void redirectsToRoot() {
        RequestFake httpRequest = new RequestFake(GET, "/redirect");

        Response httpResponse = redirectRoute.performAction(httpRequest);

        assertEquals("http://localhost:5000/", getString(httpResponse.getValue(ResponseHeader.LOCATION)));
    }
}
