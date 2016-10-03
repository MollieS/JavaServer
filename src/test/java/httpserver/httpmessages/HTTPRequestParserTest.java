package httpserver.httpmessages;

import org.junit.Test;

import java.net.URI;

import static httpserver.routing.Method.BOGUS;
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

    @Test
    public void canParseABogusRequest() {
        String request = "INVALID / HTTP/1.1";

        HTTPRequest httpRequest = httpRequestParser.parse(request);

        assertEquals(BOGUS, httpRequest.getMethod());
    }

    @Test
    public void canGetDataFromARequest() {
        String request = "POST /form \n\n\n\ndata=fatcat";

        HTTPRequest httpRequest = httpRequestParser.parse(request);

        assertEquals(URI.create("/form"), httpRequest.getRequestURI());
        assertEquals("data=fatcat", httpRequest.getData());
    }

    @Test
    public void canGetParametersFromARequest() {
        String request = "GET /parameters?variable_1=parameter";

        HTTPRequest httpRequest = httpRequestParser.parse(request);

        assertEquals("variable_1=parameter", httpRequest.getParams());
    }
}
