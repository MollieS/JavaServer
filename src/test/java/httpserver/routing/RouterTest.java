package httpserver.routing;

import httpserver.Request;
import httpserver.Response;
import httpserver.httprequests.HTTPRequest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static httpserver.routing.Method.GET;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class RouterTest {

    @Test
    public void callsPerformActionIfRouteIsRegistered() {
        Request request = HTTPRequest.create(GET, "/spy");
        List<Route> registeredRoutes = new ArrayList();
        RouteSpy routeSpy = new RouteSpy();
        registeredRoutes.add(routeSpy);
        Router tttRouter = new Router(registeredRoutes);
        tttRouter.route(request);
        assertTrue(routeSpy.performActionWasCalled);
    }

    @Test
    public void returnsA404IfRouteIsNotRegistered() {
        Request request = HTTPRequest.create(GET, "/spy");
        List<Route> registeredRoutes = new ArrayList();
        Router router = new Router(registeredRoutes);
        Response response = router.route(request);
        assertEquals(404, response.getStatusCode());
        assertEquals("Not Found", response.getReasonPhrase());
    }

    private class RouteSpy extends Route {

        public boolean performActionWasCalled = false;

        public RouteSpy() {
            super("/spy", GET);
        }

        @Override
        public Response performAction(Request request) {
            performActionWasCalled = true;
            return null;
        }
    }
}
