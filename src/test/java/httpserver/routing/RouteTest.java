package httpserver.routing;

import httpserver.Request;
import httpserver.Response;
import org.junit.Test;

import java.nio.charset.Charset;

import static httpserver.routing.Method.GET;
import static httpserver.routing.Method.HEAD;
import static httpserver.routing.Method.POST;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RouteTest {

    private Route route = new SampleRoute("/", GET, POST);

    @Test
    public void knowsIfAMethodIsAllowed() {
        assertTrue(route.methodIsAllowed(GET));
    }

    @Test
    public void knowsIfAMethodIsNotAllowed() {
        assertFalse(route.methodIsAllowed(HEAD));
    }

    @Test
    public void knowsAllAllowedMethods() {
        assertTrue(route.getAllowedMethods().contains(GET));
        assertTrue(route.getAllowedMethods().contains(POST));
        assertFalse(route.getAllowedMethods().contains(HEAD));
    }

    @Test
    public void formatsAllowedMethods() {
        byte[] allowedMethods = route.formatAllowedMethods();
        String stringMethods = new String(allowedMethods, Charset.defaultCharset());

        assertEquals("GET,POST", stringMethods);
    }

    @Test
    public void returnsAMethodNotAllowedResponse() {
        Response response = route.methodNotAllowed();

        assertEquals(405, response.getStatusCode());
        assertEquals("Method Not Allowed", response.getReasonPhrase());
    }

    private class SampleRoute extends Route {

        public SampleRoute(String uri, Method... methods) {
            super(uri, methods);
        }

        @Override
        public Response performAction(Request request) {
            return null;
        }
    }
}

