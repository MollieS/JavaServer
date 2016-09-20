package httpserver;

import java.io.IOException;
import java.net.Socket;

public class HTTPServer {

    private final SocketServer socketServer;
    private final int port;

    public HTTPServer(int port, SocketServer socketServer) {
        this.port = port;
        this.socketServer = socketServer;
    }

    public int getPort() {
        return port;
    }

    public void start() throws IOException {
        Socket socket = socketServer.serve();
        socket.close();
    }
}
