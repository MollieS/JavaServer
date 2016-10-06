package httpserver.server;

import httpserver.ClientSocket;
import httpserver.RequestHandler;
import httpserver.SocketServer;
import httpserver.httpmessages.HTTPRequest;
import httpserver.httpmessages.HTTPResponse;

public class HTTPServer {

    private final SocketServer socketServer;
    private final RequestHandler requestHandler;

    public HTTPServer(SocketServer socketServer, RequestHandler requestHandler) {
        this.socketServer = socketServer;
        this.requestHandler = requestHandler;
    }

    public void start() {
        ClientSocket socket = socketServer.serve();
        HTTPRequest httpRequest = socket.getRequest();
        HTTPResponse httpResponse = requestHandler.handle(httpRequest);
        socket.sendResponse(httpResponse);
        socket.close();
    }
}
