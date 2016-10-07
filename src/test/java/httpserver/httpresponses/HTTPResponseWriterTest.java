package httpserver.httpresponses;

import httpserver.Response;
import httpserver.httpresponse.HTTPResponseWriter;
import httpserver.httpresponse.ResponseWriterException;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static httpserver.httpresponse.StatusCode.*;
import static httpserver.routing.Method.GET;
import static httpserver.routing.Method.PUT;
import static org.junit.Assert.assertTrue;

public class HTTPResponseWriterTest {

    private final HTTPResponseWriter httpResponseWriter = new HTTPResponseWriter(new ByteArrayOutputStream());
    private HTTPResponseFake okResponse = new HTTPResponseFake(OK);

    @Test
    public void returnsHeaderForAResponseWithNoBody() {
        String response = new String(httpResponseWriter.parse(okResponse), Charset.forName("UTF-8"));

        assertTrue(response.contains("HTTP/1.1 200 OK\n"));
    }

    @Test
    public void returnsAHeaderForA404Response() {
        Response httpResponse = new HTTPResponseFake(NOTFOUND);

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
        okResponse.addAllowedMethod(Arrays.asList(PUT, GET));

        String response = new String(httpResponseWriter.parse(okResponse), Charset.forName("UTF-8"));

        assertTrue(response.contains("PUT"));
        assertTrue(response.contains("GET"));
    }

    @Test
    public void addsLocationHeader() {
        HTTPResponseFake httpResponseFake = new HTTPResponseFake(REDIRECT);
        httpResponseFake.addLocation("http://localhost:9000/");

        String response = new String(httpResponseWriter.parse(httpResponseFake), Charset.forName("UTF-8"));

        assertTrue(response.contains("Location : http://localhost:9000/"));
    }

    @Test
    public void addsContentRangeHeader() {
        HTTPResponseFake httpResponseFake = new HTTPResponseFake(PARTIAL);
        httpResponseFake.addBody("hello");
        httpResponseFake.setContentRange(6);

        String response = new String(httpResponseWriter.parse(httpResponseFake), Charset.forName("UTF-8"));

        assertTrue(response.contains("Content-Range : 6"));
    }

    @Test
    public void addsDateHeader() {
        Response responseFake = new HTTPResponseFake(PARTIAL);

        String response = new String(httpResponseWriter.parse(responseFake), Charset.forName("UTF-8"));

        assertTrue(response.contains("Date : Wed, 5 Oct"));
    }

    private class ByteArrayThatThrowsException extends ByteArrayOutputStream {

        @Override
        public void write(byte[] bytes) throws IOException {
            throw new IOException();
        }
    }

}
