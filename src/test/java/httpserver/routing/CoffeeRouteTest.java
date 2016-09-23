package httpserver.routing;

import httpserver.httpmessages.HTTPResponse;
import org.junit.Test;

import java.net.URI;
import java.nio.charset.Charset;

import static httpserver.routing.Method.*;
import static org.junit.Assert.assertEquals;

public class CoffeeRouteTest {

    @Test
    public void returnsTheCorrectStatusForAGet() {
        CoffeeRoute coffeeRoute = new CoffeeRoute("/coffee", GET, HEAD, OPTIONS);

        HTTPResponse httpResponse = coffeeRoute.performAction(GET);

        assertEquals("418", httpResponse.getStatusCode());
        assertEquals("I'm a teapot", httpResponse.getReasonPhrase());
    }

    @Test
    public void addsBodyToResponseForGet() {
        CoffeeRoute coffeeRoute = new CoffeeRoute("/coffee", GET, HEAD, OPTIONS);

        HTTPResponse httpResponse = coffeeRoute.performAction(GET);
        String body = new String(httpResponse.getBody(), Charset.forName("UTF-8"));

        assertEquals("I'm a teapot", body);
        assertEquals("text/plain", httpResponse.getContentType());
    }

    @Test
    public void hasAURI() {
        CoffeeRoute coffeeRoute = new CoffeeRoute("/coffee", GET);

        assertEquals(URI.create("/coffee"), coffeeRoute.getUri());
    }

    @Test
    public void returnsA405ForMethodNotAllowed() {
        CoffeeRoute coffeeRoute = new CoffeeRoute("/coffee", GET);

        HTTPResponse httpResponse = coffeeRoute.performAction(POST);

        assertEquals("405", httpResponse.getStatusCode());

    }
}
