package httpserver.routing;

import httpserver.ResourceHandler;
import httpserver.Response;
import httpserver.httprequests.HTTPRequest;
import httpserver.httpresponse.ResponseHeader;
import httpserver.resourcemanagement.HTTPResourceHandler;
import httpserver.resourcemanagement.ResourceParser;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static httpserver.ByteArrayConverter.getString;
import static httpserver.routing.Method.*;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RouterTest {

    private String path = getClass().getClassLoader().getResource("directory").getPath();
    private List<Route> routes = Arrays.asList(new CoffeeRoute(GET), new TeaRoute(GET), new MethodOptionsRoute(GET, POST, PUT, OPTIONS, HEAD), new RedirectRoute("http://localhost:5000", GET), new PartialContentRoute(new HTTPResourceHandler(path, new ResourceParser()), GET));
    private ResourceHandler resourceHandler = new HTTPResourceHandler(path, new ResourceParser());
    private Router router = new Router(new FileRoute(resourceHandler), routes);

    @Test
    public void getsTheCorrectHTTPResponseForAGetToCoffee() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/coffee");

        Response httpResponse = router.route(httpRequest);

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

    @Test
    public void returnsA418ResponseForAGETToCoffee() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/coffee");

        Response httpResponse = router.route(httpRequest);

        assertEquals(418, httpResponse.getStatusCode());
        assertEquals("I'm a teapot", httpResponse.getReasonPhrase());
    }

    @Test
    public void returnsA200ResponseForAGETToTea() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/tea");

        Response httpResponse = router.route(httpRequest);

        assertEquals(200, httpResponse.getStatusCode());
    }

    @Test
    public void returnsA405ResponseForAPOST() {
        HTTPRequest httpRequest = new HTTPRequest(POST, "/file1");

        Response httpResponse = router.route(httpRequest);

        assertEquals(405, httpResponse.getStatusCode());
    }

    @Test
    public void returnsA200ResponseForOptionsRequestToMethodOptions() {
        HTTPRequest httpRequest = new HTTPRequest(OPTIONS, "/method_options");

        Response httpResponse = router.route(httpRequest);

        assertEquals(200, httpResponse.getStatusCode());
    }

    @Test
    public void returnsA200ResponseForAHEADRequestToRoot() {
        HTTPRequest httpRequest = new HTTPRequest(HEAD, "/");

        Response httpResponse = router.route(httpRequest);

        assertEquals(200, httpResponse.getStatusCode());
    }

    @Test
    public void addsAllowedMethodsToAnOptionsRequest() {
        HTTPRequest httpRequest = new HTTPRequest(OPTIONS, "/method_options");

        Response httpResponse = router.route(httpRequest);

        assertTrue(httpResponse.hasHeader(ResponseHeader.ALLOW));
    }

    @Test
    public void buildsTheCorrectResponseForAGETRequestToRoot() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/");
        Response httpResponse = router.route(httpRequest);

        assertEquals(200, httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
        assertEquals("text/html", getString(httpResponse.getValue(ResponseHeader.CONTENT_TYPE)));
        assertTrue(httpResponse.hasBody());
        String body = new String(httpResponse.getBody(), Charset.forName("UTF-8"));
        assertTrue(body.contains("file1"));
    }

    @Test
    public void buildsTheCorrectResponseForAGETRequestToAJPEG() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/image.jpeg");
        Response httpResponse = router.route(httpRequest);

        assertEquals(200, httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
        assertEquals("image/jpeg", getString(httpResponse.getValue(ResponseHeader.CONTENT_TYPE)));
    }

    @Test
    public void aHeadRequestDoesNotContainBody() {
        HTTPRequest httpRequest = new HTTPRequest(HEAD, "/");
        Response httpResponse = router.route(httpRequest);

        Assert.assertFalse(httpResponse.hasBody());
    }

    @Test
    public void returnsTheCorrectResponseForAnUnknownURL() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/foobar");

        Response httpResponse = router.route(httpRequest);

        assertEquals(404, httpResponse.getStatusCode());
        assertEquals("Not Found", httpResponse.getReasonPhrase());
    }

    @Test
    public void returnsTheCorrectResponseForARedirect() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/redirect");

        Response httpResponse = router.route(httpRequest);

        assertEquals(302, httpResponse.getStatusCode());
        assertEquals("Found", httpResponse.getReasonPhrase());
        assertEquals("http://localhost:5000/", getString(httpResponse.getValue(ResponseHeader.LOCATION)));
    }

    @Test
    public void returnsTheCorrectResponseForAPartialContentGetWithFullRange() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/partial_content.txt");
        httpRequest.setRangeEnd(0);
        httpRequest.setRangeEnd(4);

        Response httpResponse = router.route(httpRequest);

        assertEquals(206, httpResponse.getStatusCode());
        assertEquals("Partial Content", httpResponse.getReasonPhrase());
    }

    @Test
    public void returnsTheCorrectResponseForAPartialContentGetWithRangeEnd() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/partial_content.txt");
        httpRequest.setRangeEnd(6);

        Response httpResponse = router.route(httpRequest);
        String body = new String(httpResponse.getBody(), Charset.defaultCharset());

        assertEquals(206, httpResponse.getStatusCode());
        assertEquals("Partial Content", httpResponse.getReasonPhrase());
        assertEquals(" 206.\n", body);
        assertEquals("6", getString(httpResponse.getValue(ResponseHeader.CONTENT_RANGE)));
    }
}
