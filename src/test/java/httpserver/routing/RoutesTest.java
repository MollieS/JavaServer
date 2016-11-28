package httpserver.routing;

import httpserver.Request;
import httpserver.Response;
import org.junit.Test;

import static httpserver.routing.Method.GET;
import static org.junit.Assert.assertEquals;

public class RoutesTest {

    @Test
    public void canRegisterARoute() {
        Routes routes = new Routes();

        routes.register(new DummyRoute());

        assertEquals(1, routes.registered().size());
    }

    private class DummyRoute extends Route {

        @Override
        public Response performAction(Request request) {
            return null;
        }

        public DummyRoute() {
            super("/dummy", GET);
        }
    }
}
