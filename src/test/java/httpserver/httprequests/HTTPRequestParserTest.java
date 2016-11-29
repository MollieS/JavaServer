package httpserver.httprequests;

import org.junit.Test;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Base64;

import static httpserver.httprequests.RequestHeader.*;
import static httpserver.routing.Method.BOGUS;
import static httpserver.routing.Method.GET;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
        String request = "POST /form \n\ndata=fatcat";

        HTTPRequest httpRequest = httpRequestParser.parse(request);

        assertTrue(httpRequest.hasHeader(DATA));
        assertEquals("fatcat", httpRequest.getValue(DATA));
    }

    @Test
    public void canGetParametersFromARequest() {
        String request = "GET /parameters?variable_1=parameter";

        HTTPRequest httpRequest = httpRequestParser.parse(request);

        assertTrue(httpRequest.hasHeader(PARAMS));
        assertEquals("parameter", httpRequest.getValue(PARAMS));
    }

    @Test
    public void canGetRangeFromARequest() {
        String request = "GET /partial_content.txt \n\n Range : bytes=1-4";

        HTTPRequest httpRequest = httpRequestParser.parse(request);

        assertTrue(httpRequest.hasHeader(RANGE));
        assertEquals("1-4", httpRequest.getValue(RANGE));
    }

    @Test
    public void canGetRangeStartFromARequest() {
        String request = "GET /partial_content.txt \n\n Range : bytes=1-4";

        HTTPRequest httpRequest = httpRequestParser.parse(request);

        assertTrue(httpRequest.hasHeader(RANGE_START));
        assertEquals("1", httpRequest.getValue(RANGE_START));
    }

    @Test
    public void canGetRangeEndFromARequest() {
        String request = "GET /partial_content.txt \n\n Range : bytes=1-4";

        HTTPRequest httpRequest = httpRequestParser.parse(request);

        assertTrue(httpRequest.hasHeader(RANGE_END));
        assertEquals("4", httpRequest.getValue(RANGE_END));
    }


    @Test
    public void rangeStartIsNotSetIfNoneGiven() {
        String request = "GET /partial_content.txt \n\n Range : bytes=-4";

        HTTPRequest httpRequest = httpRequestParser.parse(request);

        assertFalse(httpRequest.hasHeader(RANGE_START));
        assertTrue(httpRequest.hasHeader(RANGE));
        assertTrue(httpRequest.hasHeader(RANGE_END));
    }

    @Test
    public void rangeEndIsNotSetIfNoneGiven() {
        String request = "GET /partial_content.txt \n\n Range : bytes=4- ";

        HTTPRequest httpRequest = httpRequestParser.parse(request);

        assertFalse(httpRequest.hasHeader(RANGE_END));
        assertTrue(httpRequest.hasHeader(RANGE));
        assertTrue(httpRequest.hasHeader(RANGE_START));
    }

    @Test
    public void canAddParamsForACookie() {
        String request = "GET /cookie?type=chocolate \n";

        HTTPRequest httpRequest = httpRequestParser.parse(request);

        assertEquals("chocolate", httpRequest.getValue(PARAMS));
    }

    @Test
    public void canAddCookie() {
        String request = "GET /eat_cookie \n\n Cookie : type=chocolate\n";

        HTTPRequest httpRequest = httpRequestParser.parse(request);

        assertEquals("chocolate", httpRequest.getValue(COOKIE));
    }

    @Test
    public void canAddAuthorization() {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] details = encoder.encode("aladdin : carpet".getBytes());
        String codedDetails = new String(details, Charset.defaultCharset());
        String request = "GET /logs \n\n Authorization : Basic " + codedDetails + "\n";

        HTTPRequest httpRequest = httpRequestParser.parse(request);

        assertEquals(codedDetails, httpRequest.getValue(AUTHORIZATION));
    }

    @Test
    public void canAddEtag() {
        String request = "PATCH /partial-content.txt \n\n If-Matches : etag";

        HTTPRequest httpRequest = httpRequestParser.parse(request);

        assertTrue(httpRequest.hasHeader(ETAG));
        assertEquals("etag", httpRequest.getValue(ETAG));
    }

}
