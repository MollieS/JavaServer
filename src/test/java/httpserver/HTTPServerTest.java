package httpserver;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class HTTPServerTest {

    @Test
    public void canSetThePort() throws IOException {
        ConnectionsSocketServer connectionsSocketServer = new ConnectionsSocketServer();
        HTTPServer httpServer = new HTTPServer(9000, connectionsSocketServer);
        assertEquals(9000, httpServer.getPort());
    }

    @Test
    public void canAcceptConnectionsOnThePortWhenListening() throws IOException {
        ConnectionsSocketServer connectionsSocketServer = new ConnectionsSocketServer();
        HTTPServer httpServer = new HTTPServer(9000, connectionsSocketServer);
        httpServer.start();

        assertEquals(1, connectionsSocketServer.getConnections());
    }

    @Test
    public void addsResponseToSocket() {
    }
}
