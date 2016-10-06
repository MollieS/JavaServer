package httpserver.routing;

import httpserver.Response;
import httpserver.httprequests.HTTPRequest;
import org.junit.Test;

import java.nio.charset.Charset;

import static httpserver.routing.Method.GET;
import static org.junit.Assert.assertEquals;

public class EatCookieRouteTest {

    private EatCookieRoute eatCookieRoute = new EatCookieRoute(GET);

    @Test
    public void sendsAnOkResponseForAGetRequest() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/eat_cookie");

        Response httpResponse = eatCookieRoute.performAction(httpRequest);

        assertEquals(200, httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
    }

    @Test
    public void sendsTheCorrectBodyIfRequestHasCookie() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/eat_cookue");
        httpRequest.setCookie("type=chocolate");

        Response httpResponse = eatCookieRoute.performAction(httpRequest);
        String body = new String(httpResponse.getBody(), Charset.defaultCharset());

        assertEquals(body, "mmmm chocolate");
    }
}
