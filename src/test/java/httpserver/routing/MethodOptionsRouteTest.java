package httpserver.routing;

import httpserver.httpmessages.HTTPResponse;
import org.junit.Test;

import static httpserver.routing.Method.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MethodOptionsRouteTest {
    private MethodOptionsRoute methodOptionsRoute = new MethodOptionsRoute("/method_options", GET, PUT, POST, HEAD, OPTIONS);

    @Test
    public void returnsA200ResponseForAGetRequest() {
        HTTPResponse httpResponse = methodOptionsRoute.performAction(GET);

        assertEquals("200", httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
    }

    @Test
    public void returnsA200ResponseForAPostRequest() {
        HTTPResponse httpResponse = methodOptionsRoute.performAction(POST);

        assertEquals("200", httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
    }

    @Test
    public void returnsResponseWithMethodsForOptionsRequest() {
        HTTPResponse httpResponse = methodOptionsRoute.performAction(OPTIONS);

        assertTrue(httpResponse.allowedMethods().contains(GET));
        assertTrue(httpResponse.allowedMethods().contains(PUT));
        assertTrue(httpResponse.allowedMethods().contains(POST));
        assertTrue(httpResponse.allowedMethods().contains(HEAD));
        assertTrue(httpResponse.allowedMethods().contains(OPTIONS));
    }

}
