package httpserver.httpmessages;

import httpserver.resourcemanagement.HTTPResourceHandler;
import httpserver.resourcemanagement.ResourceParser;
import org.junit.Test;

import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HTTPResponseBuilderTest {

    @Test
    public void buildsTheCorrectResponseForAGETRequestToRoot() {
        String path = getClass().getClassLoader().getResource("directory").getPath();
        HTTPResourceHandler resourceHandler = new HTTPResourceHandler(path, new ResourceParser());
        HTTPResponseBuilder httpResponseBuilder = new HTTPResponseBuilder(resourceHandler);

        HTTPResponse httpResponse = httpResponseBuilder.buildResponse("GET", "/");

        assertEquals("200", httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
        assertTrue(httpResponse.hasBody());
        String body = new String(httpResponse.getBody(), Charset.forName("UTF-8"));
        assertEquals("file1\nfile2\nimage.jpeg\n", body);
    }

    @Test
    public void buildsTheCorrectResponseForAGETRequestToAnInvalidFile() {
        String path = getClass().getClassLoader().getResource("directory").getPath();
        HTTPResourceHandler resourceHandler = new HTTPResourceHandler(path, new ResourceParser());
        HTTPResponseBuilder httpResponseBuilder = new HTTPResponseBuilder(resourceHandler);

        HTTPResponse httpResponse = httpResponseBuilder.buildResponse("GET", "/foobar");

        assertEquals("404", httpResponse.getStatusCode());
        assertEquals("Not Found", httpResponse.getReasonPhrase());
        assertFalse(httpResponse.hasBody());
    }

}
