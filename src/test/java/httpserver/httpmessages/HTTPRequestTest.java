package httpserver.httpmessages;

import org.junit.Test;

import java.net.URI;

import static httpserver.routing.Method.GET;
import static org.junit.Assert.assertEquals;

public class HTTPRequestTest {

    @Test
    public void hasAMethod() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/");

        assertEquals(GET, httpRequest.getMethod());
    }

    @Test
    public void hasAnRequestUri() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/");

        assertEquals(URI.create("/"), httpRequest.getRequestURI());
    }
}
