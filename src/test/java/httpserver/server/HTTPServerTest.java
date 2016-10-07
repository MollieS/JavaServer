package httpserver.server;

import httpserver.HTTPRouter;
import httpserver.Request;
import httpserver.Response;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class HTTPServerTest {

    private final ConnectionsSocketServer connectionsSocketServer = new ConnectionsSocketServer();

    @Test
    public void canAcceptConnectionsOnThePortWhenListening() throws IOException {
        HTTPServer httpServer = new HTTPServer(connectionsSocketServer, new RouterDummy(), new LoggerDummy());

        httpServer.start();

        assertEquals(1, connectionsSocketServer.getConnections());
    }

    private class RouterDummy implements HTTPRouter {

        @Override
        public Response route(Request httpRequest) {
            return null;
        }
    }

    private class LoggerDummy extends HTTPLogger {

        public LoggerDummy() {
            super("/path");
        }

        @Override
        public void log(String request) {
        }
    }
}

