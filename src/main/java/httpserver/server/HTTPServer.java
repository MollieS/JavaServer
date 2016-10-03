package httpserver.server;

import httpserver.ClientSocket;
import httpserver.RequestHandler;
import httpserver.SocketServer;
import httpserver.httpmessages.HTTPRequest;
import httpserver.httpmessages.HTTPRequestParser;
import httpserver.httpmessages.HTTPResponse;

public class HTTPServer {

    private final SocketServer socketServer;
    private final RequestHandler requestHandler;
    private final HTTPRequestParser httpRequestParser;

    public HTTPServer(SocketServer socketServer, RequestHandler requestHandler, HTTPRequestParser httpRequestParser) {
        this.socketServer = socketServer;
        this.requestHandler = requestHandler;
        this.httpRequestParser = httpRequestParser;
    }

    public void start() {
        ClientSocket socket = socketServer.serve();
        HTTPRequest httpRequest = socket.getRequest();
        HTTPResponse httpResponse = requestHandler.handle(httpRequest);
        socket.sendResponse(httpResponse);
        socket.close();
    }
}
