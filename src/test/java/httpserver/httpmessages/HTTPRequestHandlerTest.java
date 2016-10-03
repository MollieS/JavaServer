package httpserver.httpmessages;

import httpserver.ResponseBuilder;
import httpserver.routing.*;
import org.junit.Test;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static httpserver.routing.Method.*;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HTTPRequestHandlerTest {

    private List<Route> routes = Arrays.asList(new CoffeeRoute("/coffee", GET), new TeaRoute("/tea", GET), new MethodOptionsRoute("/method_options", GET, POST, PUT, OPTIONS, HEAD));
    private Router router = new Router(routes);
    private ResponseBuilderSpy responseBuilderSpy = new ResponseBuilderSpy();
    private HTTPRequestHandler httpRequestHandler = new HTTPRequestHandler(responseBuilderSpy, router);

    @Test
    public void delegatesToResponseBuilderIfARequestToRoot() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/");

        httpRequestHandler.handle(httpRequest);

        assertTrue(responseBuilderSpy.buildResponseHasBeenCalled);
    }

    @Test
    public void delegatesToResponseBuilderIfUnkownURI() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/foobar");

        httpRequestHandler.handle(httpRequest);

        assertTrue(responseBuilderSpy.buildResponseHasBeenCalled);
    }

    @Test
    public void returnsA418ResponseForAGETToCoffee() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/coffee");

        HTTPResponse httpResponse = httpRequestHandler.handle(httpRequest);

        assertEquals(418, httpResponse.getStatusCode());
        assertEquals("I'm a teapot", httpResponse.getReasonPhrase());
    }

    @Test
    public void returnsA200ResponseForAGETToTea() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/tea");

        HTTPResponse httpResponse = httpRequestHandler.handle(httpRequest);

        assertEquals(200, httpResponse.getStatusCode());
    }

    @Test
    public void returnsA405ResponseForAPOST() {
        HTTPRequest httpRequest = new HTTPRequest(POST, "/file1");

        HTTPResponse httpResponse = httpRequestHandler.handle(httpRequest);

        assertEquals(405, httpResponse.getStatusCode());
    }

    @Test
    public void returnsA200ResponseForOptionsRequestToMethodOptions() {
        HTTPRequest httpRequest = new HTTPRequest(OPTIONS, "/method_options");

        HTTPResponse httpResponse = httpRequestHandler.handle(httpRequest);

        assertEquals(200, httpResponse.getStatusCode());
    }

    @Test
    public void returnsA200ResponseForAHEADRequestToRoot() {
        HTTPRequest httpRequest = new HTTPRequest(HEAD, "/");

        HTTPResponse httpResponse = httpRequestHandler.handle(httpRequest);

        assertEquals(200, httpResponse.getStatusCode());
    }

    @Test
    public void addsAllowedMethodsToAnOptionsRequest() {
        HTTPRequest httpRequest = new HTTPRequest(OPTIONS, "/method_options");

        HTTPResponse httpResponse = httpRequestHandler.handle(httpRequest);

        assertNotNull(httpResponse.allowedMethods());
    }

    private class ResponseBuilderSpy implements ResponseBuilder {

        public boolean buildResponseHasBeenCalled = false;

        @Override
        public HTTPResponse buildResponse(Method method, URI path) {
            buildResponseHasBeenCalled = true;
            return new HTTPResponse(200, "OK");
        }
    }
}
