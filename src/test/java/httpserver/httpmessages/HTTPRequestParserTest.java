package httpserver.httpmessages;

import org.junit.Test;

import java.net.URI;

import static httpserver.routing.Method.GET;
import static org.junit.Assert.assertEquals;

public class HTTPRequestParserTest {

    private final HTTPRequestParser httpRequestParser = new HTTPRequestParser();

    @Test
    public void returnsAGetMethodFromAString() {
        String request = "GET / HTTP/1.1";

        HTTPRequest httpRequest = httpRequestParser.parse(request);

        assertEquals(GET, httpRequest.getMethod());
    }

    @Test
    public void returnsTheRequestURIFromAString() {
        String request = "GET / HTTP/1.1";

        HTTPRequest httpRequest = httpRequestParser.parse(request);

        assertEquals(URI.create("/"), httpRequest.getRequestURI());
    }
}
