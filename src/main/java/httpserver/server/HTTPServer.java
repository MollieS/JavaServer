package httpserver.server;

import httpserver.ClientSocket;
import httpserver.HTTPRouter;
import httpserver.Response;
import httpserver.SocketServer;
import httpserver.httprequests.HTTPRequest;

public class HTTPServer {

    private final SocketServer socketServer;
    private final HTTPRouter router;
    private final HTTPLogger logger;

    public HTTPServer(SocketServer socketServer, HTTPRouter router, HTTPLogger logger) {
        this.socketServer = socketServer;
        this.router = router;
        this.logger = logger;
    }

    public void start() {
        ClientSocket socket = socketServer.serve();
        HTTPRequest httpRequest = socket.getRequest();
        logger.log(httpRequest.getStatusHeader());
        Response httpResponse = router.route(httpRequest);
        socket.sendResponse(httpResponse);
        socket.close();
    }
}
