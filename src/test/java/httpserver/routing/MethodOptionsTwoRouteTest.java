package httpserver.routing;

import httpserver.Request;
import httpserver.Response;
import httpserver.httprequests.RequestFake;
import org.junit.Test;

import java.nio.charset.Charset;

import static httpserver.httpresponse.ResponseHeader.ALLOW;
import static httpserver.routing.Method.GET;
import static httpserver.routing.Method.OPTIONS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MethodOptionsTwoRouteTest {

    @Test
    public void sendsTheCorrectResponseForAnOptionsRequest() {
        MethodOptionsTwoRoute methodOptionsTwoRoute = new MethodOptionsTwoRoute(GET, OPTIONS);
        Request httpRequest = new RequestFake(OPTIONS, "/method_options2");

        Response httpResponse = methodOptionsTwoRoute.performAction(httpRequest);
        String allowedMethods = new String(httpResponse.getValue(ALLOW), Charset.defaultCharset());

        assertTrue(allowedMethods.contains("GET"));
        assertTrue(allowedMethods.contains("OPTIONS"));
        assertEquals(200, httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
    }

    @Test
    public void sendsTheCorrectResponseForAGetRequest() {
        MethodOptionsTwoRoute methodOptionsTwoRoute = new MethodOptionsTwoRoute(GET, OPTIONS);

        Request httpRequest = new RequestFake(GET, "/method_options2");
        Response httpResponse = methodOptionsTwoRoute.performAction(httpRequest);

        assertEquals(200, httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
    }
}
