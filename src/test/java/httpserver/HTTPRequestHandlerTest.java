package httpserver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HTTPRequestHandlerTest {

    @Test
    public void returnsA200ResponseForGETToAKnownURI() {
        HTTPRequest httpRequest = new HTTPRequest("GET", "/");

        HTTPResponse httpResponse = HTTPRequestHandler.handle(httpRequest);

        assertEquals("200", httpResponse.getStatusCode());
    }

    @Test
    public void returnsA404ResponseForAGETToUnknownURI() {
        HTTPRequest httpRequest = new HTTPRequest("GET", "/foobar");

        HTTPResponse httpResponse = HTTPRequestHandler.handle(httpRequest);

        assertEquals("404", httpResponse.getStatusCode());

    }
}
