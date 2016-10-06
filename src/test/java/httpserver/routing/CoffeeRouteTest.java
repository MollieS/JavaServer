package httpserver.routing;

import httpserver.Response;
import httpserver.httprequests.HTTPRequest;
import httpserver.httpresponse.ResponseHeader;
import org.junit.Test;

import java.net.URI;

import static httpserver.ByteArrayConverter.getString;
import static httpserver.routing.Method.*;
import static org.junit.Assert.assertEquals;

public class CoffeeRouteTest {

    private CoffeeRoute coffeeRoute = new CoffeeRoute(GET, HEAD, OPTIONS);

    @Test
    public void returnsTheCorrectStatusForAGet() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/coffee");

        Response httpResponse = coffeeRoute.performAction(httpRequest);

        assertEquals(418, httpResponse.getStatusCode());
        assertEquals("I'm a teapot", httpResponse.getReasonPhrase());
    }

    @Test
    public void addsBodyToResponseForGet() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/coffee");

        Response httpResponse = coffeeRoute.performAction(httpRequest);
        String body = getString(httpResponse.getBody());
        String contentType = getString(httpResponse.getValue(ResponseHeader.CONTENT_TYPE));

        assertEquals("I'm a teapot", body);
        assertEquals("text/plain", contentType);
    }

    @Test
    public void hasAURI() {
        CoffeeRoute coffeeRoute = new CoffeeRoute(GET);

        assertEquals(URI.create("/coffee"), coffeeRoute.getUri());
    }

    @Test
    public void returnsA405ForMethodNotAllowed() {
        HTTPRequest httpRequest = new HTTPRequest(BOGUS, "/coffee");

        Response httpResponse = coffeeRoute.performAction(httpRequest);

        assertEquals(405, httpResponse.getStatusCode());
    }
}
