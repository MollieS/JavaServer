package httpserver.httpresponses;

import httpserver.httpresponse.HTTPResponse;
import httpserver.httpresponse.HTTPResponseWriter;
import httpserver.httpresponse.ResponseWriterException;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static httpserver.routing.Method.GET;
import static httpserver.routing.Method.PUT;
import static org.junit.Assert.assertTrue;

public class HTTPResponseWriterTest {

    private final HTTPResponseWriter httpResponseWriter = new HTTPResponseWriter(new ByteArrayOutputStream());
    private HTTPResponse okResponse = new HTTPResponse(200, "OK", new ResponseDateFake());

    @Test
    public void returnsHeaderForAResponseWithNoBody() {

        String response = new String(httpResponseWriter.parse(okResponse), Charset.forName("UTF-8"));

        assertTrue(response.contains("HTTP/1.1 200 OK\n"));
    }

    @Test
    public void returnsResponseForAHTTPResponseWithABody() {
        okResponse.setContentType("text/plain");
        okResponse.setBody("This is the body".getBytes());
        okResponse.setAllowedMethods(Arrays.asList(GET));

        String response = new String(httpResponseWriter.parse(okResponse), Charset.forName("UTF-8"));

        assertTrue(response.contains("Content-Type : text/plain\n\nThis is the body"));
    }

    @Test
    public void returnsAHeaderForA404Response() {
        HTTPResponse httpResponse = new HTTPResponse(404, "Not Found", new ResponseDateFake());

        String response = new String(httpResponseWriter.parse(httpResponse), Charset.forName("UTF-8"));

        assertTrue(response.contains("HTTP/1.1 404 Not Found\n"));
    }

    @Test(expected = ResponseWriterException.class)
    public void throwsAByteWriterExceptionIfCannotWriteHeader() {
        HTTPResponseWriter httpResponseWriter = new HTTPResponseWriter(new ByteArrayThatThrowsException());

        httpResponseWriter.parse(okResponse);
    }

    @Test
    public void addsAllowedMethodsHeader() {
        okResponse.setAllowedMethods(Arrays.asList(PUT, GET));
        okResponse.setBody("HELLO".getBytes());

        String response = new String(httpResponseWriter.parse(okResponse), Charset.forName("UTF-8"));

        assertTrue(response.contains("PUT"));
    }

    @Test
    public void addsLocationHeader() {
        HTTPResponse httpResponse = new HTTPResponse(302, "Found", new ResponseDateFake());
        httpResponse.setLocation("http://localhost:9000/");

        String response = new String(httpResponseWriter.parse(httpResponse), Charset.forName("UTF-8"));

        assertTrue(response.contains("Location : http://localhost:9000/"));
    }

    @Test
    public void addsContentRangeHeader() {
        HTTPResponse httpResponse = new HTTPResponse(206, "Partial Content", new ResponseDateFake());
        httpResponse.setContentRange(6);

        String response = new String(httpResponseWriter.parse(httpResponse), Charset.forName("UTF-8"));

        assertTrue(response.contains("Content-Range : 6"));
    }

    @Test
    public void addsDateHeader() {
        HTTPResponse httpResponse = new HTTPResponse(206, "Partial Content", new ResponseDateFake());
        httpResponse.setContentRange(6);

        String response = new String(httpResponseWriter.parse(httpResponse), Charset.forName("UTF-8"));

        assertTrue(response.contains("Date : Wed, 5 Oct"));
    }

    private class ByteArrayThatThrowsException extends ByteArrayOutputStream {

        @Override
        public void write(byte[] bytes) throws IOException {
            throw new IOException();
        }
    }
}
