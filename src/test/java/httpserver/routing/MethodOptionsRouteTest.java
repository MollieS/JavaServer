package httpserver.routing;

import httpserver.Response;
import httpserver.httprequests.HTTPRequest;
import org.junit.Test;

import java.nio.charset.Charset;

import static httpserver.httpresponse.ResponseHeader.ALLOW;
import static httpserver.routing.Method.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MethodOptionsRouteTest {

    private MethodOptionsRoute methodOptionsRoute = new MethodOptionsRoute(GET, PUT, POST, HEAD, OPTIONS);

    @Test
    public void returnsA200ResponseForAGetRequest() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/method_options");

        Response httpResponse = methodOptionsRoute.performAction(httpRequest);

        assertEquals(200, httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
    }

    @Test
    public void returnsA200ResponseForAPostRequest() {
        HTTPRequest httpRequest = new HTTPRequest(POST, "/method_options");

        Response httpResponse = methodOptionsRoute.performAction(httpRequest);

        assertEquals(200, httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
    }

    @Test
    public void returnsA405IfMethodNotAllowed() {
        HTTPRequest httpRequest = new HTTPRequest(BOGUS, "/method_options");

        Response httpResponse = methodOptionsRoute.performAction(httpRequest);

        assertEquals(405, httpResponse.getStatusCode());
    }

    @Test
    public void returnsResponseWithMethodsForOptionsRequest() {
        HTTPRequest httpRequest = new HTTPRequest(OPTIONS, "/method_options");

        Response httpResponse = methodOptionsRoute.performAction(httpRequest);
        String allowedMethods = new String(httpResponse.getValue(ALLOW), Charset.defaultCharset());

        assertTrue(allowedMethods.contains("GET"));
        assertTrue(allowedMethods.contains("PUT"));
        assertTrue(allowedMethods.contains("POST"));
        assertTrue(allowedMethods.contains("HEAD"));
        assertTrue(allowedMethods.contains("OPTIONS"));
    }
}
