package httpserver.server;

import httpserver.ClientSocket;
import httpserver.SocketConnectionException;
import httpserver.SocketServer;
import httpserver.httpmessages.HTTPResponseParser;

import java.io.IOException;
import java.net.ServerSocket;

public class HTTPSocketServer implements SocketServer {

    private final ServerSocket serverSocket;

    public HTTPSocketServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public ClientSocket serve() {
        try {
            return new HTTPSocket(serverSocket.accept(), new HTTPResponseParser());
        } catch (IOException e) {
            throw new SocketConnectionException("Error accepting requests: ", e.getCause());
        }
    }
}
