package httpserver;

import java.net.Socket;

public class ConnectionsSocketServer implements SocketServer {

    private int connections = 0;

    public int getConnections() {
        return connections;
    }

    @Override
    public Socket serve() {
        connections++;
        return new Socket();
    }
}
