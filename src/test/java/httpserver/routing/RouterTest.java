package httpserver.routing;

import httpserver.httprequests.HTTPRequest;
import httpserver.httpresponse.HTTPResponse;
import httpserver.resourcemanagement.HTTPResourceHandler;
import httpserver.resourcemanagement.ResourceParser;
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

    private List<Route> registeredRoutes = Arrays.asList(new CoffeeRoute(GET), new TeaRoute(GET));
    String path = getClass().getClassLoader().getResource("directory").getPath();
    private Router router = new Router(new FileRoute(new HTTPResourceHandler(path, new ResourceParser())), registeredRoutes);

    @Test
    public void knowsAllRegisteredRoutes() {
        assertTrue(router.hasRegistered(URI.create("/coffee")));
        assertTrue(router.hasRegistered(URI.create("/tea")));
    }

    @Test
    public void getsTheCorrectHTTPResponseForAGetToCoffee() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/coffee");

        HTTPResponse httpResponse = router.getResponse(httpRequest);

        assertEquals(418, httpResponse.getStatusCode());
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
