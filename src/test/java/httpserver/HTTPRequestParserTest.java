package httpserver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HTTPRequestParserTest {

    @Test
    public void returnsAGetMethodFromAString() {
        HTTPRequestParser httpRequestParser = new HTTPRequestParser();

        String request = "GET / HTTP/1.1";
        HTTPRequest httpRequest = httpRequestParser.parse(request);

        assertEquals("GET", httpRequest.getMethod());
    }

    @Test
    public void returnsTheRequestURIFromAString() {
        HTTPRequestParser httpRequestParser = new HTTPRequestParser();

        String request = "GET / HTTP/1.1";
        HTTPRequest httpRequest = httpRequestParser.parse(request);

        assertEquals("/", httpRequest.getRequestURI());
    }
}
