package httpserver.server;

import httpserver.SocketConnectionException;
import httpserver.httpmessages.HTTPRequest;
import httpserver.httpmessages.HTTPRequestParser;
import httpserver.httpmessages.HTTPResponse;
import httpserver.httpmessages.HTTPResponseWriter;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HTTPSocketTest {

    private final SocketSpy socketSpy = new SocketSpy();
    private final SocketThatThrowsException socketThatThrowsException = new SocketThatThrowsException();

    @Test
    public void closesTheSocket() {
        HTTPSocket httpSocket = createSocket(socketSpy);

        httpSocket.close();

        assertTrue(socketSpy.isClosed);
    }

    @Test(expected = SocketConnectionException.class)
    public void throwsASocketConnectionExceptionIfErrorClosing() {
        HTTPSocket httpSocket = createSocket(socketThatThrowsException);

        httpSocket.close();
    }

    @Test
    public void getsTheOutputStreamFromTheSocket() {
        HTTPSocket httpSocket = createSocket(socketSpy);

        httpSocket.sendResponse(new HTTPResponse(200, "OK"));

        assertTrue(socketSpy.getOutputStreamHasBeenCalled);
    }

    @Test
    public void writesTheHTTPResponseToTheSocketOutputStream() {
        HTTPSocket httpSocket = createSocket(socketSpy);

        httpSocket.sendResponse(new HTTPResponse(200, "OK"));
        OutputStream outputStream = socketSpy.getOutputStream();

        assertEquals("HTTP/1.1 200 OK\n", outputStream.toString());
    }

    @Test(expected = SocketConnectionException.class)
    public void throwsASocketConnectionExceptionIfOutputStreamCannotBeRetrieved() {
        HTTPSocket httpSocket = createSocket(socketThatThrowsException);

        httpSocket.sendResponse(new HTTPResponse(200, "OK"));
    }

    @Test
    public void getsTheInputStreamFromTheSocket() {
        HTTPSocket httpSocket = createSocket(socketSpy);

        httpSocket.getRequest();

        assertTrue(socketSpy.getInputStreamHasBeenCalled);
    }

    @Test
    public void getsTheRequestFromInputStream() {
        HTTPSocket httpSocket = createSocket(socketSpy);

        HTTPRequest request = httpSocket.getRequest();

        assertEquals("GET", request.getMethod());
    }

    @Test(expected = SocketConnectionException.class)
    public void throwsASocketConnectionExceptionIfInputStreamCannotBeRetrieved() {
        HTTPSocket httpSocket = createSocket(socketThatThrowsException);

        httpSocket.getRequest();
    }

    @Test
    public void sendsTheBodyOfTheResponseIfThereIsBody() {
        HTTPResponse httpResponse = new HTTPResponse(200, "OK");
        httpResponse.setBody("This is the body".getBytes());
        HTTPSocket httpSocket = createSocket(socketSpy);

        httpSocket.sendResponse(httpResponse);
        OutputStream outputStream = socketSpy.getOutputStream();

        assertTrue(outputStream.toString().contains("This is the body"));
    }

    private HTTPSocket createSocket(Socket socket) {
        return new HTTPSocket(socket, new HTTPResponseWriter(new ByteArrayOutputStream()), new HTTPRequestParser());
    }


    private class SocketSpy extends Socket {

        boolean isClosed = false;
        boolean getOutputStreamHasBeenCalled = false;
        boolean getInputStreamHasBeenCalled = false;
        private final OutputStream outputStream = new ByteArrayOutputStream();

        @Override
        public void close() {
            isClosed = true;
        }

        @Override
        public OutputStream getOutputStream() {
            getOutputStreamHasBeenCalled = true;
            return outputStream;
        }

        @Override
        public InputStream getInputStream() {
            getInputStreamHasBeenCalled = true;
            return new ByteArrayInputStream(("GET / HTTP/1.1").getBytes());
        }
    }

    private class SocketThatThrowsException extends Socket {

        @Override
        public void close() throws IOException {
            throw new IOException();
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            throw new IOException();
        }

        @Override
        public InputStream getInputStream() throws IOException {
            throw new IOException();
        }
    }
}
