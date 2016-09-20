package httpserver;

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

    public void start() {
        ClientSocket socket = socketServer.serve();
        socket.sendResponse(new HTTPResponse());
        socket.close();
    }
}
