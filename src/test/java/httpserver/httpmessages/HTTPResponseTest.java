package httpserver.httpmessages;

import org.junit.Test;

import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class HTTPResponseTest {

    private final HTTPResponse httpResponse = new HTTPResponse(200, "OK");

    @Test
    public void hasAStatusCode() {
        String statusCode = httpResponse.getStatusCode();

        assertEquals("200", statusCode);
    }

    @Test
    public void hasAReasonPhrase() {
        String reasonPhrase = httpResponse.getReasonPhrase();

        assertEquals("OK", reasonPhrase);
    }

    @Test
    public void hasBody() {
        httpResponse.setBody("This is the body".getBytes());

        String body = new String(httpResponse.getBody(), Charset.forName("UTF-8"));

        assertEquals("This is the body", body);
    }

    @Test
    public void knowsIfItDoesNotHaveBody() {
        assertFalse(httpResponse.hasBody());
    }

    @Test
    public void knowsIfItHasBody() {
        httpResponse.setBody("This is the body".getBytes());

        assertTrue(httpResponse.hasBody());
    }

    @Test
    public void knowsTheContentType() {
        httpResponse.setContentType("text/plain");

        assertEquals("text/plain", httpResponse.getContentType());
    }
}
