package httpserver.httpmessages;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static httpserver.routing.Method.GET;
import static httpserver.routing.Method.PUT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HTTPResponseWriterTest {

    private final HTTPResponseWriter httpResponseWriter = new HTTPResponseWriter(new ByteArrayOutputStream());

    @Test
    public void returnsHeaderForAResponseWithNoBody() {
        HTTPResponse httpResponse = new HTTPResponse(200, "OK");

        String response = new String(httpResponseWriter.parse(httpResponse), Charset.forName("UTF-8"));

        assertEquals("HTTP/1.1 200 OK\n", response);
    }

    @Test
    public void returnsResponseForAHTTPResponseWithABody() {
        HTTPResponse httpResponse = new HTTPResponse(200, "OK");
        httpResponse.setContentType("text/plain");
        httpResponse.setBody("This is the body".getBytes());
        httpResponse.setAllowedMethods(Arrays.asList(GET));

        String response = new String(httpResponseWriter.parse(httpResponse), Charset.forName("UTF-8"));

        assertEquals("HTTP/1.1 200 OK\nAllow : GET,\nContent-Type : text/plain\n\nThis is the body", response);
    }

    @Test
    public void returnsAHeaderForA404Response() {
        HTTPResponse httpResponse = new HTTPResponse(404, "Not Found");

        String response = new String(httpResponseWriter.parse(httpResponse), Charset.forName("UTF-8"));

        assertEquals("HTTP/1.1 404 Not Found\n", response);
    }

    @Test(expected = ByteWriterException.class)
    public void throwsAByteWriterExceptionIfCannotWriteHeader() {
        HTTPResponseWriter httpResponseWriter = new HTTPResponseWriter(new ByteArrayThatThrowsException());
        HTTPResponse httpResponse = new HTTPResponse(200, "OK");

        httpResponseWriter.parse(httpResponse);
    }

    @Test
    public void addsAllowedMethodsHeader() {
        HTTPResponse httpResponse = new HTTPResponse(200, "OK");
        httpResponse.setAllowedMethods(Arrays.asList(PUT, GET));
        httpResponse.setBody("HELLO".getBytes());

        String response = new String(httpResponseWriter.parse(httpResponse), Charset.forName("UTF-8"));

        assertTrue(response.contains("PUT"));
    }

    private class ByteArrayThatThrowsException extends ByteArrayOutputStream {

        @Override
        public void write(byte[] bytes) throws IOException {
            throw new IOException();
        }
    }
}
