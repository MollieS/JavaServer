package httpserver.routing;

import httpserver.Response;
import httpserver.httprequests.RequestFake;
import httpserver.httpresponse.ResponseHeader;
import org.junit.Test;

import java.nio.charset.Charset;

import static httpserver.routing.Method.GET;
import static org.junit.Assert.assertEquals;

public class CookieRouteTest {

    private CookieRoute cookieRoute = new CookieRoute(GET);

    @Test
    public void sendsA200ResponseForAGetRequest() {
        RequestFake request = new RequestFake(GET, "/cookie");

        Response httpResponse = cookieRoute.performAction(request);

        assertEquals(200, httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
    }

    @Test
    public void sendsTheCorrectBodyForAGetRequest() {
        RequestFake request = new RequestFake(GET, "/cookie");

        Response httpResponse = cookieRoute.performAction(request);
        String body = new String(httpResponse.getBody(), Charset.defaultCharset());

        assertEquals("Eat", body);
    }

    @Test
    public void addsACookieToResponseIfRequestHasParams() {
        RequestFake request = new RequestFake(GET, "/cookie");
        request.setParams("chocolate");

        Response httpResponse = cookieRoute.performAction(request);
        String cookie = new String(httpResponse.getValue(ResponseHeader.COOKIE), Charset.defaultCharset());

        assertEquals("chocolate", cookie);
    }
}
