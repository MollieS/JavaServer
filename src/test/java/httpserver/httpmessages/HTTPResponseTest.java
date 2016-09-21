package httpserver.httpmessages;

import org.junit.Test;

import static org.junit.Assert.*;

public class HTTPResponseTest {

    private HTTPResponse httpResponse = new HTTPResponse(200, "OK");

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
        httpResponse.setBody("This is the body");

        assertEquals("This is the body", httpResponse.getBody());
    }

    @Test
    public void knowsIfItDoesNotHaveBody() {
        assertFalse(httpResponse.hasBody());
    }

    @Test
    public void knowsIfItHasBody() {
        httpResponse.setBody("This is the body");

        assertTrue(httpResponse.hasBody());
    }
}
