package httpserver;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

        httpSocket.sendResponse(new HTTPResponse(200));

        assertTrue(socketSpy.getOutputStreamHasBeenCalled);
    }

    @Test
    public void writesTheHTTPResponseToTheSocketOutputStream() {
        HTTPSocket httpSocket = new HTTPSocket(socketSpy);

        httpSocket.sendResponse(new HTTPResponse(200));
        OutputStream outputStream = socketSpy.getOutputStream();

        assertEquals("200 OK", outputStream.toString());
    }

    @Test(expected = SocketConnectionException.class)
    public void throwsASocketConnectionExceptionIfOutputStreamCannotBeRetrieved() {
        HTTPSocket httpSocket = new HTTPSocket(socketThatThrowsException);

        httpSocket.sendResponse(new HTTPResponse(200));
    }

    private class SocketSpy extends Socket {

        boolean isClosed = false;
        boolean getOutputStreamHasBeenCalled = false;
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
    }
}
