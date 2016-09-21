package httpserver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HTTPRequestParserTest {

    @Test
    public void returnsAGetMethodFromAString() {
        String request = "GET / HTTP/1.1";

        HTTPRequest httpRequest = HTTPRequestParser.parse(request);

        assertEquals("GET", httpRequest.getMethod());
    }

    @Test
    public void returnsTheRequestURIFromAString() {
        String request = "GET / HTTP/1.1";

        HTTPRequest httpRequest = HTTPRequestParser.parse(request);

        assertEquals("/", httpRequest.getRequestURI());
    }
}
