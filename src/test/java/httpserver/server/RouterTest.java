package httpserver.server;

import httpserver.httpmessages.HTTPResponse;
import httpserver.routing.Method;
import httpserver.routing.Router;
import org.junit.Test;

import java.net.URI;
import java.util.List;

import static httpserver.routing.Method.*;
import static httpserver.routing.Router.RegisteredRoute.COFFEE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RouterTest {

    private Router router = new Router();

    @Test
    public void hasAllowedMethodsForCoffeeURI() {
        List<Method> methods = router.allowedMethods(URI.create("/coffee"));

        assertTrue(methods.contains(GET));
    }

    @Test
    public void knowsIfAMethodIsAllowedForCoffeeURI() {
        assertTrue(router.methodIsAllowed(URI.create("/coffee"), GET));
    }

    @Test
    public void knowsTheExpectedResponseForAGETToCoffeeURI() {
        HTTPResponse httpResponse = new HTTPResponse(418, "I'm a teapot");

        assertTrue(router.expectedResponse(URI.create("/coffee"), httpResponse));
    }

    @Test
    public void doesNotExpectAResponseForOtherURL() {
        HTTPResponse httpResponse = new HTTPResponse(404, "Not Found");

        assertTrue(router.expectedResponse(URI.create("/anything"), httpResponse));
    }

    @Test
    public void expectsA200ForTea() {
        HTTPResponse httpResponse = new HTTPResponse(404, "Not Found");

        assertFalse(router.expectedResponse(URI.create("/tea"), httpResponse));
    }

    @Test
    public void allowsGETRequestForAnyURL() {
        assertTrue(router.methodIsAllowed(URI.create("/file1"), GET));
        assertTrue(router.methodIsAllowed(URI.create("/doesntexist"), GET));
        assertTrue(router.methodIsAllowed(URI.create("/hello/thisisaroute"), GET));
    }

    @Test
    public void knowsIfOverrideIsAllowedForCoffeeRoute() {
        assertTrue(router.isOverrideAllowed(COFFEE));
    }

    @Test
    public void knowsIfARouteIsRegistered() {
        assertTrue(router.routeIsRegistered(URI.create("/coffee")));
        assertTrue(router.routeIsRegistered(URI.create("/tea")));
    }

    @Test
    public void knowsIfARouteIsNotRegistered() {
        assertFalse(router.routeIsRegistered(URI.create("/file1")));
        assertFalse(router.routeIsRegistered(URI.create("/foobar")));
    }

    @Test
    public void knowsTheAllowedMethodsForMethodOptions() {
        List<Method> allowedMethods = router.allowedMethods(URI.create("/method_options"));

        assertTrue(allowedMethods.contains(GET));
        assertTrue(allowedMethods.contains(HEAD));
        assertTrue(allowedMethods.contains(POST));
        assertTrue(allowedMethods.contains(OPTIONS));
        assertTrue(allowedMethods.contains(PUT));
    }
}
