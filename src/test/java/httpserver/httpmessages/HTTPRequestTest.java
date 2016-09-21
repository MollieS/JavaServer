package httpserver.httpmessages;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HTTPRequestTest {

    @Test
    public void hasAMethod() {
        HTTPRequest httpRequest = new HTTPRequest("GET", "/");

        assertEquals("GET", httpRequest.getMethod());
    }

    @Test
    public void hasAnRequestUri() {
        HTTPRequest httpRequest = new HTTPRequest("GET", "/");

        assertEquals("/", httpRequest.getRequestURI());
    }
}
