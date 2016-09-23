package httpserver.routing;

import httpserver.httpmessages.HTTPRequest;
import httpserver.httpmessages.HTTPResponse;
import org.junit.Test;

import static httpserver.routing.Method.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MethodOptionsRouteTest {

    private MethodOptionsRoute methodOptionsRoute = new MethodOptionsRoute("/method_options", GET, PUT, POST, HEAD, OPTIONS);

    @Test
    public void returnsA200ResponseForAGetRequest() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/method_options");

        HTTPResponse httpResponse = methodOptionsRoute.performAction(httpRequest);

        assertEquals("200", httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
    }

    @Test
    public void returnsA200ResponseForAPostRequest() {
        HTTPRequest httpRequest = new HTTPRequest(POST, "/method_options");

        HTTPResponse httpResponse = methodOptionsRoute.performAction(httpRequest);

        assertEquals("200", httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
    }

    @Test
    public void returnsResponseWithMethodsForOptionsRequest() {
        HTTPRequest httpRequest = new HTTPRequest(OPTIONS, "/method_options");

        HTTPResponse httpResponse = methodOptionsRoute.performAction(httpRequest);

        assertTrue(httpResponse.allowedMethods().contains(GET));
        assertTrue(httpResponse.allowedMethods().contains(PUT));
        assertTrue(httpResponse.allowedMethods().contains(POST));
        assertTrue(httpResponse.allowedMethods().contains(HEAD));
        assertTrue(httpResponse.allowedMethods().contains(OPTIONS));
    }

}
