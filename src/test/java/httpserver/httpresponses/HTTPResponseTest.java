package httpserver.httpresponses;

import httpserver.Response;
import httpserver.httpresponse.HTTPResponse;
import httpserver.httpresponse.ResponseHeader;
import httpserver.httpresponse.StatusCode;
import httpserver.Resource;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.HashMap;

import static httpserver.httpresponse.ResponseHeader.*;
import static httpserver.httpresponse.ResponseHeader.AUTH;
import static httpserver.httpresponse.ResponseHeader.COOKIE;
import static httpserver.httpresponse.StatusCode.NOTALLOWED;
import static httpserver.httpresponse.StatusCode.OK;
import static httpserver.httpresponse.StatusCode.PARTIAL;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class HTTPResponseTest {

    private HashMap<ResponseHeader, byte[]> headers = new HashMap<>();

    @Test
    public void buildsACoffeeResponse() {
        HTTPResponse httpResponse = HTTPResponse.create(StatusCode.TEAPOT);

        assertEquals(418, httpResponse.getStatusCode());
        assertEquals("I'm a teapot", httpResponse.getReasonPhrase());
        assertNotNull(httpResponse.getValue(DATE));
    }

    @Test
    public void buildsANotFoundResponse() {
        HTTPResponse httpResponse = HTTPResponse.create(StatusCode.NOTFOUND);

        assertEquals(404, httpResponse.getStatusCode());
        assertEquals("Not Found", httpResponse.getReasonPhrase());
        assertFalse(httpResponse.hasBody());
    }

    @Test
    public void buildsARedirectResponse() {
        headers.put(LOCATION, "/".getBytes());
        HTTPResponse httpResponse = HTTPResponse.create(StatusCode.REDIRECT).withHeaders(headers);

        assertEquals(302, httpResponse.getStatusCode());
        assertEquals("Found", httpResponse.getReasonPhrase());
        assertNotNull(httpResponse.getValue(LOCATION));
    }

    @Test
    public void buildsAnOkResponseWithNoBody() {
        HTTPResponse httpResponse = HTTPResponse.create(StatusCode.OK);

        assertEquals(200, httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
        assertFalse(httpResponse.hasBody());
    }

    @Test
    public void buildsAnOkResponseWithBody() {
        ResourceFake resourceFake = new ResourceFake();
        Response httpResponse = HTTPResponse.create(StatusCode.OK).withBody(resourceFake);

        String body = getString(httpResponse.getBody());

        assertEquals("body", body);
        assertNotNull(httpResponse.getValue(CONTENT_RANGE));
    }

    @Test
    public void buildsAPartialResponseWithBody() {
        Resource resource = new ResourceFake();
        Response httpResponse = HTTPResponse.create(PARTIAL).withBody(resource);

        String body = getString(httpResponse.getBody());

        assertEquals(206, httpResponse.getStatusCode());
        assertEquals("Partial Content", httpResponse.getReasonPhrase());
        assertNotNull(httpResponse.getValue(DATE));
        assertEquals("body", body);
    }

    @Test
    public void buildsAMethodNotAllowedResponse() {
        HTTPResponse httpResponse = HTTPResponse.create(NOTALLOWED);

        assertEquals(405, httpResponse.getStatusCode());
        assertEquals("Method Not Allowed", httpResponse.getReasonPhrase());
    }

    @Test
    public void addsLocationHeaderTorResponse() {
        headers.put(LOCATION, "/".getBytes());

        HTTPResponse httpResponse = HTTPResponse.create(OK).withHeaders(headers);
        String value = getStringFromByteArray(httpResponse.getValue(LOCATION));

        assertTrue(httpResponse.hasHeader(LOCATION));
        assertEquals("/", value);
    }

    @Test
    public void addsContentTypeHeaderToResponse() {
        headers.put(CONTENT_TYPE, "text/html".getBytes());

        HTTPResponse httpResponse = HTTPResponse.create(OK).withHeaders(headers);
        String value = getStringFromByteArray(httpResponse.getValue(CONTENT_TYPE));

        assertTrue(httpResponse.hasHeader(CONTENT_TYPE));
        assertEquals("text/html", value);
    }

    @Test
    public void addsAllowHeaderToResponse() {
        headers.put(ALLOW, "GET".getBytes());

        HTTPResponse httpResponse = HTTPResponse.create(OK).withHeaders(headers);
        String value = getStringFromByteArray(httpResponse.getValue(ALLOW));

        assertTrue(httpResponse.hasHeader(ALLOW));
        assertEquals("GET", value);
    }

    private String getStringFromByteArray(byte[] value) {
        return new String(value, Charset.defaultCharset());
    }

    @Test
    public void addsDateHeaderToResponse() {
        headers.put(DATE, "Weds, 05 Oct".getBytes());

        HTTPResponse httpResponse = HTTPResponse.create(OK).withHeaders(headers);
        String value = getStringFromByteArray(httpResponse.getValue(DATE));

        assertTrue(httpResponse.hasHeader(DATE));
        assertEquals("Weds, 05 Oct", value);
    }

    @Test
    public void addsContentRangeToResponse() {
        headers.put(CONTENT_RANGE, "6".getBytes());

        HTTPResponse httpResponse = HTTPResponse.create(OK).withHeaders(headers);
        String value = getStringFromByteArray(httpResponse.getValue(CONTENT_RANGE));

        assertTrue(httpResponse.hasHeader(CONTENT_RANGE));
        assertEquals("6", value);
    }

    @Test
    public void addsCookieHeaderToResponse() {
        headers.put(COOKIE, "type=chocolate".getBytes());

        HTTPResponse httpResponse = HTTPResponse.create(OK).withHeaders(headers);
        String value = getStringFromByteArray(httpResponse.getValue(COOKIE));

        assertTrue(httpResponse.hasHeader(COOKIE));
        assertEquals("type=chocolate", value);
    }

    @Test
    public void addsAuthenticationHeaderToResponse() {
        headers.put(AUTH, "Basic".getBytes());

        HTTPResponse httpResponse = HTTPResponse.create(OK).withHeaders(headers);
        String value = getStringFromByteArray(httpResponse.getValue(AUTH));

        assertTrue(httpResponse.hasHeader(AUTH));
        assertEquals("Basic", value);
    }

    private class ResourceFake implements Resource {

        @Override
        public byte[] getContents() {
            return "body".getBytes();
        }

        @Override
        public String getContentType() {
            return "text/plain";
        }
    }

    public static String getString(byte[] bytes) {
        return new String(bytes, Charset.defaultCharset());
    }
}
