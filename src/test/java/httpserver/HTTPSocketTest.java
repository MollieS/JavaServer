package httpserver;

import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

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

    private class SocketSpy extends Socket {

        public boolean isClosed = false;

        @Override
        public void close() {
            isClosed = true;
        }

    }

    private class SocketThatThrowsException extends Socket {

        @Override
        public void close() throws IOException {
            throw new IOException();
        }
    }
}
