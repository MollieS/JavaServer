package httpserver.httpmessages;

import httpserver.resourcemanagement.HTTPResourceHandler;
import httpserver.resourcemanagement.ResourceParser;
import httpserver.routing.Router;
import org.junit.Test;

import java.net.URI;
import java.nio.charset.Charset;

import static httpserver.routing.Method.GET;
import static httpserver.routing.Method.OPTIONS;
import static org.junit.Assert.*;

public class HTTPResponseBuilderTest {

    private final String path = getClass().getClassLoader().getResource("directory").getPath();
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
    public void addsAllowedMethodsToAResponse() {
        HTTPRequest httpRequest = new HTTPRequest(OPTIONS, "/method_options");
        HTTPResponse httpResponse = new HTTPResponse(200, "OK");

        httpResponseBuilder.addAllowedMethods(httpResponse, httpRequest, new Router());

        assertTrue(httpResponse.allowedMethods().contains(OPTIONS));
    }
}
