package httpserver.routing;

import httpserver.httpmessages.HTTPRequest;
import httpserver.httpmessages.HTTPResponse;
import org.junit.Test;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static httpserver.routing.Method.GET;
import static httpserver.routing.Method.HEAD;
import static httpserver.routing.Method.POST;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RouterTest {

    private List<Route> registeredRoutes = Arrays.asList(new CoffeeRoute("/coffee", GET), new TeaRoute("/tea", GET));
    private Router router = new Router(registeredRoutes);

    @Test
    public void knowsAllRegisteredRoutes() {
        assertTrue(router.hasRegistered(URI.create("/coffee")));
        assertTrue(router.hasRegistered(URI.create("/tea")));
    }

    @Test
    public void getsTheCorrectHTTPResponseForAGetToCoffee() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/coffee");

        HTTPResponse httpResponse = router.getResponse(httpRequest);

        assertEquals("418", httpResponse.getStatusCode());
    }

    @Test
    public void allowsGetAndHeadRequestsToAnyURI() {
        assertTrue(router.allowsMethod(GET));
        assertTrue(router.allowsMethod(HEAD));
    }

    @Test
    public void doesNotAllowPostRequestsToGeneralURLs() {
        assertFalse(router.allowsMethod(POST));
    }
}
