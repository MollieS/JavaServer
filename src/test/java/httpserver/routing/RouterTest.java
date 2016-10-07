package httpserver.routing;

import httpserver.Request;
import httpserver.ResourceHandler;
import httpserver.Response;
import httpserver.httprequests.RequestFake;
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
        RequestFake httpRequest = new RequestFake(GET, "/coffee");

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
        Request httpRequest = new RequestFake(GET, "/coffee");

        Response httpResponse = router.route(httpRequest);

        assertEquals(418, httpResponse.getStatusCode());
        assertEquals("I'm a teapot", httpResponse.getReasonPhrase());
    }

    @Test
    public void returnsA200ResponseForAGETToTea() {
        Request httpRequest = new RequestFake(GET, "/tea");

        Response httpResponse = router.route(httpRequest);

        assertEquals(200, httpResponse.getStatusCode());
    }

    @Test
    public void returnsA405ResponseForAPOST() {
        Request httpRequest = new RequestFake(POST, "/file1");

        Response httpResponse = router.route(httpRequest);

        assertEquals(405, httpResponse.getStatusCode());
    }

    @Test
    public void returnsA200ResponseForOptionsRequestToMethodOptions() {
        Request httpRequest = new RequestFake(OPTIONS, "/method_options");

        Response httpResponse = router.route(httpRequest);

        assertEquals(200, httpResponse.getStatusCode());
    }

    @Test
    public void returnsA200ResponseForAHEADRequestToRoot() {
        Request httpRequest = new RequestFake(HEAD, "/");

        Response httpResponse = router.route(httpRequest);

        assertEquals(200, httpResponse.getStatusCode());
    }

    @Test
    public void addsAllowedMethodsToAnOptionsRequest() {
        Request httpRequest = new RequestFake(OPTIONS, "/method_options");

        Response httpResponse = router.route(httpRequest);

        assertTrue(httpResponse.hasHeader(ResponseHeader.ALLOW));
    }

    @Test
    public void buildsTheCorrectResponseForAGETRequestToRoot() {
        Request httpRequest = new RequestFake(GET, "/");
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
        Request httpRequest = new RequestFake(GET, "/image.jpeg");
        Response httpResponse = router.route(httpRequest);

        assertEquals(200, httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
        assertEquals("image/jpeg", getString(httpResponse.getValue(ResponseHeader.CONTENT_TYPE)));
    }

    @Test
    public void aHeadRequestDoesNotContainBody() {
        Request httpRequest = new RequestFake(HEAD, "/");
        Response httpResponse = router.route(httpRequest);

        Assert.assertFalse(httpResponse.hasBody());
    }

    @Test
    public void returnsTheCorrectResponseForAnUnknownURL() {
        Request httpRequest = new RequestFake(GET, "/foobar");

        Response httpResponse = router.route(httpRequest);

        assertEquals(404, httpResponse.getStatusCode());
        assertEquals("Not Found", httpResponse.getReasonPhrase());
    }

    @Test
    public void returnsTheCorrectResponseForARedirect() {
        Request httpRequest = new RequestFake(GET, "/redirect");

        Response httpResponse = router.route(httpRequest);

        assertEquals(302, httpResponse.getStatusCode());
        assertEquals("Found", httpResponse.getReasonPhrase());
        assertEquals("http://localhost:5000/", getString(httpResponse.getValue(ResponseHeader.LOCATION)));
    }

    @Test
    public void returnsTheCorrectResponseForAPartialContentGetWithFullRange() {
        RequestFake httpRequest = new RequestFake(GET, "/partial_content.txt");
        httpRequest.setRangeEnd("0");
        httpRequest.setRangeEnd("4");

        Response httpResponse = router.route(httpRequest);

        assertEquals(206, httpResponse.getStatusCode());
        assertEquals("Partial Content", httpResponse.getReasonPhrase());
    }

    @Test
    public void returnsTheCorrectResponseForAPartialContentGetWithRangeEnd() {
        RequestFake httpRequest = new RequestFake(GET, "/partial_content.txt");
        httpRequest.setRangeEnd("6");

        Response httpResponse = router.route(httpRequest);
        String body = new String(httpResponse.getBody(), Charset.defaultCharset());

        assertEquals(206, httpResponse.getStatusCode());
        assertEquals("Partial Content", httpResponse.getReasonPhrase());
        assertEquals(" 206.\n", body);
        assertEquals("6", getString(httpResponse.getValue(ResponseHeader.CONTENT_RANGE)));
    }
}
