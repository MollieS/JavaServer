package httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPSocketServer implements SocketServer {

    private final ServerSocket serverSocket;

    public HTTPSocketServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public Socket serve() {
        try {
            return serverSocket.accept();
        } catch (IOException e) {
            throw new SocketConnectionException("Error accepting requests: ", e.getCause());
        }
    }
}
