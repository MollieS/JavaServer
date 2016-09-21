package httpserver.server;

import httpserver.ClientSocket;
import httpserver.SocketServer;

public class ConnectionsSocketServer implements SocketServer {

    private int connections = 0;

    public int getConnections() {
        return connections;
    }

    @Override
    public ClientSocket serve() {
        connections++;
        return new SocketFake();
    }
}
