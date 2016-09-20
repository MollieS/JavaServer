package httpserver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HTTPResponseTest {

    @Test
    public void hasAStatusCode() {
        HTTPResponse httpResponse = new HTTPResponse(200);

        String statusCode = httpResponse.getStatusCode();

        assertEquals("200", statusCode);
    }

    @Test
    public void hasAReasonPhrase() {
        HTTPResponse httpResponse = new HTTPResponse(200);

        String reasonPhrase = httpResponse.getReasonPhrase();

        assertEquals("OK", reasonPhrase);
    }
}
