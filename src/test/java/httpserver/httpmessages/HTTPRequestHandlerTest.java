package httpserver.httpmessages;

import httpserver.resourcemanagement.HTTPResourceHandler;
import httpserver.resourcemanagement.ResourceParser;
import httpserver.routing.*;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static httpserver.routing.Method.*;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

public class HTTPRequestHandlerTest {

    private List<Route> routes = Arrays.asList(new CoffeeRoute("/coffee", GET), new TeaRoute("/tea", GET), new MethodOptionsRoute("/method_options", GET, POST, PUT, OPTIONS, HEAD), new RedirectRoute("/redirect", "http://localhost:5000", GET));
    private Router router = new Router(routes);
    String path = getClass().getClassLoader().getResource("directory").getPath();
    private HTTPRequestHandler httpRequestHandler = new HTTPRequestHandler(new HTTPResourceHandler(path, new ResourceParser()), router);

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

    @Test
    public void buildsTheCorrectResponseForAGETRequestToRoot() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/");
        HTTPResponse httpResponse = httpRequestHandler.handle(httpRequest);

        assertEquals(200, httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
        assertEquals("text/html", httpResponse.getContentType());
        assertTrue(httpResponse.hasBody());
        String body = new String(httpResponse.getBody(), Charset.forName("UTF-8"));
        assertTrue(body.contains("file1"));
    }

    @Test
    public void buildsTheCorrectResponseForAGETRequestToAJPEG() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/image.jpeg");
        HTTPResponse httpResponse = httpRequestHandler.handle(httpRequest);

        assertEquals(200, httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
        assertEquals("image/jpeg", httpResponse.getContentType());
    }

    @Test
    public void aHeadRequestDoesNotContainBody() {
        HTTPRequest httpRequest = new HTTPRequest(HEAD, "/");
        HTTPResponse httpResponse = httpRequestHandler.handle(httpRequest);

        assertFalse(httpResponse.hasBody());
    }

    @Test
    public void returnsTheCorrectResponseForAnUnknownURL() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/foobar");

        HTTPResponse httpResponse = httpRequestHandler.handle(httpRequest);

        assertEquals(404, httpResponse.getStatusCode());
        assertEquals("Not Found", httpResponse.getReasonPhrase());
    }

    @Test
    public void returnsTheCorrectResponseForARedirect() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/redirect");

        HTTPResponse httpResponse = httpRequestHandler.handle(httpRequest);

        assertEquals(302, httpResponse.getStatusCode());
        assertEquals("Found", httpResponse.getReasonPhrase());
        assertEquals("http://localhost:5000/", httpResponse.getLocation());

    }
}
