package httpserver.httprequests;

import org.junit.Test;

import java.net.URI;
import java.util.HashMap;

import static httpserver.httprequests.RequestHeader.*;
import static httpserver.routing.Method.GET;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class HTTPRequestTest {

    private HTTPRequest httpRequest = HTTPRequest.create(GET, "/");
    private HashMap<RequestHeader, String> headers = new HashMap<>();

    @Test
    public void hasAMethod() {
        assertEquals(GET, httpRequest.getMethod());
    }

    @Test
    public void hasAUri() {
        assertTrue(URI.create("/").equals(httpRequest.getRequestURI()));
    }

    @Test
    public void knowsIfItHasParameters() {
        headers.put(PARAMS, "data=hello");
        httpRequest = httpRequest.withHeaders(headers);

        assertTrue(httpRequest.hasHeader(PARAMS));
        assertEquals("data=hello", httpRequest.getValue(PARAMS));
    }

    @Test
    public void knowsIfRangeStartIsSet() {
        headers.put(RANGE_START, "1");
        httpRequest = httpRequest.withHeaders(headers);

        assertTrue(httpRequest.hasHeader(RANGE_START));
        assertEquals("1", httpRequest.getValue(RANGE_START));
    }

    @Test
    public void knowsIfRangeEndIsSet() {
        headers.put(RANGE_END, "1");
        httpRequest = HTTPRequest.create(GET, "/").withHeaders(headers);

        assertTrue(httpRequest.hasHeader(RANGE_END));
    }

    @Test
    public void returnsNullIfHeaderIsNotSet() {
        httpRequest = HTTPRequest.create(GET, "/");

        assertNull(httpRequest.getValue(PARAMS));
    }
}
