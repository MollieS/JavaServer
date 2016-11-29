package httpserver.routing;

import httpserver.Response;
import httpserver.httprequests.RequestFake;
import org.junit.Test;

import java.nio.charset.Charset;

import static httpserver.routing.Method.GET;
import static org.junit.Assert.assertEquals;

public class EatCookieRouteTest {

    private EatCookieRoute eatCookieRoute = new EatCookieRoute(GET);

    @Test
    public void sendsAnOkResponseForAGetRequest() {
        RequestFake httpRequest = new RequestFake(GET, "/eat_cookie");

        Response httpResponse = eatCookieRoute.performAction(httpRequest);

        assertEquals(200, httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
    }

    @Test
    public void sendsTheCorrectBodyIfRequestHasCookie() {
        RequestFake httpRequest = new RequestFake(GET, "/eat_cookue");
        httpRequest.setCookie("chocolate");

        Response httpResponse = eatCookieRoute.performAction(httpRequest);
        String body = new String(httpResponse.getBody(), Charset.defaultCharset());

        assertEquals("mmmm chocolate", body);
    }
}
