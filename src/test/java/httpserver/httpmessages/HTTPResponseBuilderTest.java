package httpserver.httpmessages;

import httpserver.resourcemanagement.HTTPResourceHandler;
import httpserver.resourcemanagement.ResourceParser;
import httpserver.routing.CoffeeRoute;
import httpserver.routing.Router;
import httpserver.routing.TeaRoute;
import org.junit.Test;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;

import static httpserver.routing.Method.GET;
import static httpserver.routing.Method.HEAD;
import static org.junit.Assert.*;

public class HTTPResponseBuilderTest {

    private final String path = getClass().getClassLoader().getResource("directory").getPath();
    private final Router router = new Router(Arrays.asList(new TeaRoute("/tea", GET), new CoffeeRoute("/coffee", GET)));
    private final HTTPResourceHandler resourceHandler = new HTTPResourceHandler(path, new ResourceParser());
    private final HTTPResponseBuilder httpResponseBuilder = new HTTPResponseBuilder(resourceHandler);

    @Test
    public void buildsTheCorrectResponseForAGETRequestToRoot() {
        HTTPResponse httpResponse = httpResponseBuilder.buildResponse(GET, URI.create("/"));

        assertEquals("200", httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
        assertEquals("text/plain", httpResponse.getContentType());
        assertTrue(httpResponse.hasBody());
        String body = new String(httpResponse.getBody(), Charset.forName("UTF-8"));
        assertTrue(body.contains("file1"));
    }

    @Test
    public void buildsTheCorrectResponseForAGETRequestToAnInvalidFile() {
        HTTPResponse httpResponse = httpResponseBuilder.buildResponse(GET, URI.create("/foobar"));

        assertEquals("404", httpResponse.getStatusCode());
        assertEquals("Not Found", httpResponse.getReasonPhrase());
        assertFalse(httpResponse.hasBody());
    }

    @Test
    public void buildsTheCorrectResponseForAGETRequestToAJPEG() {
        HTTPResponse httpResponse = httpResponseBuilder.buildResponse(GET, URI.create("/image.jpeg"));

        assertEquals("200", httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
        assertEquals("image/jpeg", httpResponse.getContentType());
    }

    @Test
    public void aHeadRequestDoesNotContainBody() {
        HTTPResponse httpResponse = httpResponseBuilder.buildResponse(HEAD, URI.create("/"));

        assertFalse(httpResponse.hasBody());
    }
}
