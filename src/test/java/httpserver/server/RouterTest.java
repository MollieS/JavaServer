package httpserver.server;

import httpserver.httpmessages.HTTPResponse;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RouterTest {

    @Test
    public void hasAllowedMethodsForCoffeeURI() {
        Router router = new Router();

        List<Router.Methods> methods = router.allowedMethods("/coffee");

        assertTrue(methods.contains(Router.Methods.GET));
    }

    @Test
    public void knowsIfAMethodIsAllowedForCoffeeURI() {
        Router router = new Router();

        assertTrue(router.methodIsAllowed("/coffee", "GET"));
    }

    @Test
    public void knowsTheExpectedResponseForAGETToCoffeeURI() {
        Router router = new Router();

        HTTPResponse httpResponse = new HTTPResponse(418, "I'm a teapot");

        assertTrue(router.expectedResponse("/coffee", httpResponse));
    }

    @Test
    public void doesNotExpectAResponseForOtherURL() {
        Router router = new Router();

        HTTPResponse httpResponse = new HTTPResponse(404, "Not Found");

        assertTrue(router.expectedResponse("/anything", httpResponse));
    }

    @Test
    public void expectsA200ForTea() {
        Router router = new Router();

        HTTPResponse httpResponse = new HTTPResponse(404, "Not Found");

        assertFalse(router.expectedResponse("/tea", httpResponse));
    }
}
