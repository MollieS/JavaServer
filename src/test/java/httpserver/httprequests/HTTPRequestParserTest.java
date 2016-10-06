package httpserver.httprequests;

import org.junit.Test;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Base64;

import static httpserver.routing.Method.BOGUS;
import static httpserver.routing.Method.GET;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void canGetRangeStartFromARequest() {
        String request = "GET /partial_content.txt \n\n Range : bytes=1-4";

        HTTPRequest httpRequest = httpRequestParser.parse(request);

        assertEquals(1, httpRequest.getRangeStart());
        assertTrue(httpRequest.hasRange());
    }

    @Test
    public void canGetRangeEndFromARequest() {
        String request = "GET /partial_content.txt \n\n Range : bytes=0-4";

        HTTPRequest httpRequest = httpRequestParser.parse(request);

        assertEquals(5, httpRequest.getRangeEnd());
        assertTrue(httpRequest.hasRange());
    }

    @Test
    public void rangeStartIsZeroIfNoneGiven() {
        String request = "GET /partial_content.txt \n\n Range : bytes=-4";

        HTTPRequest httpRequest = httpRequestParser.parse(request);

        assertEquals(0, httpRequest.getRangeStart());
        assertTrue(httpRequest.hasRange());
    }

    @Test
    public void rangeEndIsZeroIfNoneGiven() {
        String request = "GET /partial_content.txt \n\n Range : bytes=4- ";

        HTTPRequest httpRequest = httpRequestParser.parse(request);

        assertEquals(0, httpRequest.getRangeEnd());
        assertTrue(httpRequest.hasRange());
    }

    @Test
    public void canAddParamsForACookie() {
        String request = "GET /cookie?type=chocolate \n";

        HTTPRequest httpRequest = httpRequestParser.parse(request);

        assertEquals("type=chocolate", httpRequest.getParams());
    }

    @Test
    public void canAddCookie() {
        String request = "GET /eat_cookie \n\n Cookie : type=chocolate\n";

        HTTPRequest httpRequest = httpRequestParser.parse(request);

        assertEquals("type=chocolate", httpRequest.getCookie());
    }

    @Test
    public void canAddAuthorization() {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] details = encoder.encode("aladdin : carpet".getBytes());
        String codedDetails = new String(details, Charset.defaultCharset());
        String request = "GET /logs \n\n Authorization : Basic " + codedDetails + "\n";

        HTTPRequest httpRequest = httpRequestParser.parse(request);

        assertEquals(codedDetails, httpRequest.getAuthorization());
    }
}
