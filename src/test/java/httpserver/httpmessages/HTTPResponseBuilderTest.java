package httpserver.httpmessages;

import httpserver.resourcemanagement.HTTPResourceHandler;
import httpserver.resourcemanagement.ResourceParser;
import org.junit.Test;

import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class HTTPResponseBuilderTest {

    private final String path = getClass().getClassLoader().getResource("directory").getPath();
    private final HTTPResourceHandler resourceHandler = new HTTPResourceHandler(path, new ResourceParser());
    private final HTTPResponseBuilder httpResponseBuilder = new HTTPResponseBuilder(resourceHandler);

    @Test
    public void buildsTheCorrectResponseForAGETRequestToRoot() {
        HTTPResponse httpResponse = httpResponseBuilder.buildResponse("GET", "/");

        assertEquals("200", httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
        assertEquals("text/plain", httpResponse.getContentType());
        assertTrue(httpResponse.hasBody());
        String body = new String(httpResponse.getBody(), Charset.forName("UTF-8"));
        assertTrue(body.contains("file1"));
    }

    @Test
    public void buildsTheCorrectResponseForAGETRequestToAnInvalidFile() {
        HTTPResponse httpResponse = httpResponseBuilder.buildResponse("GET", "/foobar");

        assertEquals("404", httpResponse.getStatusCode());
        assertEquals("Not Found", httpResponse.getReasonPhrase());
        assertFalse(httpResponse.hasBody());
    }

    @Test
    public void buildsTheCorrectResponseForAGETRequestToAJPEG() {
        HTTPResponse httpResponse = httpResponseBuilder.buildResponse("GET", "/image.jpeg");

        assertEquals("200", httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
        assertEquals("image/jpeg", httpResponse.getContentType());
    }
}
