package httpserver;

import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HTTPSocketTest {

    private SocketSpy socketSpy = new SocketSpy();
    private SocketThatThrowsException socketThatThrowsException = new SocketThatThrowsException();

    @Test
    public void closesTheSocket() {
        HTTPSocket httpSocket = new HTTPSocket(socketSpy);

        httpSocket.close();

        assertTrue(socketSpy.isClosed);
    }

    @Test(expected = SocketConnectionException.class)
    public void throwsASocketConnectionExceptionIfErrorClosing() {
        HTTPSocket httpSocket = new HTTPSocket(socketThatThrowsException);

        httpSocket.close();
    }

    @Test
    public void getsTheOutputStreamFromTheSocket() {
        HTTPSocket httpSocket = new HTTPSocket(socketSpy);

        httpSocket.sendResponse(new HTTPResponse(200, "OK"));

        assertTrue(socketSpy.getOutputStreamHasBeenCalled);
    }

    @Test
    public void writesTheHTTPResponseToTheSocketOutputStream() {
        HTTPSocket httpSocket = new HTTPSocket(socketSpy);

        httpSocket.sendResponse(new HTTPResponse(200, "OK"));
        OutputStream outputStream = socketSpy.getOutputStream();

        assertEquals("HTTP/1.1 200 OK", outputStream.toString());
    }

    @Test(expected = SocketConnectionException.class)
    public void throwsASocketConnectionExceptionIfOutputStreamCannotBeRetrieved() {
        HTTPSocket httpSocket = new HTTPSocket(socketThatThrowsException);

        httpSocket.sendResponse(new HTTPResponse(200, "OK"));
    }

    @Test
    public void getsTheInputStreamFromTheSocket() {
        HTTPSocket httpSocket = new HTTPSocket(socketSpy);

        httpSocket.getRequest();

        assertTrue(socketSpy.getInputStreamHasBeenCalled);
    }

    @Test
    public void getsTheRequestFromInputStream() {
        HTTPSocket httpSocket = new HTTPSocket(socketSpy);

        String request = httpSocket.getRequest();

        assertEquals("GET / HTTP/1.1", request);
    }

    @Test(expected = SocketConnectionException.class)
    public void throwsASocketConnectionExceptionIfInputStreamCannotBeRetrieved() {
        HTTPSocket httpSocket = new HTTPSocket(socketThatThrowsException);

        httpSocket.getRequest();
    }

    private class SocketSpy extends Socket {

        boolean isClosed = false;
        boolean getOutputStreamHasBeenCalled = false;
        boolean getInputStreamHasBeenCalled = false;
        private OutputStream outputStream = new ByteArrayOutputStream();

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
