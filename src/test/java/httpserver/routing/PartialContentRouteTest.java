package httpserver.routing;

import httpserver.ResourceHandler;
import httpserver.httpmessages.HTTPRequest;
import httpserver.httpmessages.HTTPResponse;
import httpserver.resourcemanagement.HTTPResourceHandler;
import httpserver.resourcemanagement.ResourceParser;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static httpserver.routing.Method.GET;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PartialContentRouteTest {

    private String path = getClass().getClassLoader().getResource("directory").getPath();
    private ResourceHandler resourceHandler = new HTTPResourceHandler(path, new ResourceParser());
    private PartialContentRoute partialContentRoute = new PartialContentRoute(resourceHandler, GET);

    @Test
    public void sendsTheCorrectResponseForAGETRequest() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/partial_content.txt");

        HTTPResponse httpResponse = partialContentRoute.performAction(httpRequest);
        String body = new String(httpResponse.getBody(), Charset.defaultCharset());

        assertEquals(200, httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
        assertTrue(body.contains("206"));
        assertEquals("text/plain", httpResponse.getContentType());
    }

    @Test
    public void sendsTheCorrectHeaderResponseForAPartialGetRequest() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/partial_content.txt");
        httpRequest.setRangeStart(0);
        httpRequest.setRangeEnd(4);

        HTTPResponse httpResponse = partialContentRoute.performAction(httpRequest);

        assertEquals(206, httpResponse.getStatusCode());
        assertEquals("Found", httpResponse.getReasonPhrase());
    }

    @Test
    public void sendsTheCorrectResponseWithBodyForAPartialGetRequest() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/partial_content.txt");
        httpRequest.setRangeStart(0);
        httpRequest.setRangeEnd(4);

        HTTPResponse httpResponse = partialContentRoute.performAction(httpRequest);
        String body = new String(httpResponse.getBody(), Charset.defaultCharset());

        assertEquals("This ", body);
        assertEquals("text/plain", httpResponse.getContentType());
        assertEquals(5, httpResponse.getContentRange());
    }

    @Test
    public void sendsTheCorrectResponseForAPartialGetRequestWithNoRangeStart() throws IOException {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/partial_content.txt");
        httpRequest.setRangeEnd(6);

        Path path = Paths.get("/Users/molliestephenson/Java/Server/cob_spec/public/partial_content.txt");
        byte[] file = Files.readAllBytes(path);
        byte[] expectedBytes = Arrays.copyOfRange(file, 71, 77);


        HTTPResponse httpResponse = partialContentRoute.performAction(httpRequest);
        String body = new String(httpResponse.getBody(), Charset.defaultCharset());

        assertEquals(206, httpResponse.getStatusCode());
        assertEquals("Found", httpResponse.getReasonPhrase());
        assertEquals(" 206.\n", body);
        assertEquals("text/plain", httpResponse.getContentType());
        assertEquals(6, httpResponse.getContentRange());
        assertTrue(Arrays.equals(expectedBytes, httpResponse.getBody()));
    }

    @Test
    public void sendsTheCorrectResponseForAPartialGetRequestWithNoRangeEnd() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/partial_content.txt");
        httpRequest.setRangeStart(70);

        HTTPResponse httpResponse = partialContentRoute.performAction(httpRequest);
        String body = new String(httpResponse.getBody(), Charset.defaultCharset());

        assertEquals(206, httpResponse.getStatusCode());
        assertEquals("Found", httpResponse.getReasonPhrase());
        assertEquals("a 206.\n", body);
        assertEquals("text/plain", httpResponse.getContentType());
        assertEquals(7, httpResponse.getContentRange());
    }
}
