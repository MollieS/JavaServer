package httpserver.routing;

import httpserver.httpmessages.HTTPResponse;
import org.junit.Test;

import static httpserver.routing.Method.GET;
import static httpserver.routing.Method.HEAD;
import static httpserver.routing.Method.OPTIONS;

public class CoffeeRouteTest {

    @Test
    public void knowsItsAction() {
        CoffeeRoute coffeeRoute = new CoffeeRoute(GET, HEAD, OPTIONS);

        HTTPResponse httpResponse = coffeeRoute.performAction(GET);
    }
}
