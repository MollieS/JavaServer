package httpserver.server;

import httpserver.RequestHandler;
import httpserver.httpmessages.HTTPRequest;
import httpserver.httpmessages.HTTPResponse;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class HTTPServerTest {

    private final ConnectionsSocketServer connectionsSocketServer = new ConnectionsSocketServer();

    @Test
    public void canAcceptConnectionsOnThePortWhenListening() throws IOException {
        HTTPServer httpServer = new HTTPServer(connectionsSocketServer, new RequestHandlerFake());

        httpServer.start();

        assertEquals(1, connectionsSocketServer.getConnections());
    }

    private class RequestHandlerFake implements RequestHandler {

        @Override
        public HTTPResponse handle(HTTPRequest httpRequest) {
            return new HTTPResponse(200, "OK");
        }
    }
}

