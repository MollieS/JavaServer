package httpserver.server;

import httpserver.ClientSocket;
import httpserver.HTTPRouter;
import httpserver.Response;
import httpserver.SocketServer;
import httpserver.httprequests.HTTPRequest;

import java.util.concurrent.ExecutorService;

public class HTTPServer {

    private final SocketServer socketServer;
    private final HTTPRouter router;
    private final HTTPLogger logger;

    public HTTPServer(SocketServer socketServer, HTTPRouter router, HTTPLogger logger) {
        this.socketServer = socketServer;
        this.router = router;
        this.logger = logger;
    }

    public void start(ExecutorService executorService) {
        ClientSocket httpSocket;
        while ((httpSocket = socketServer.serve()) != null) {
            executorService.execute(new ServerTask(httpSocket));
        }
    }

    private void processRequest(ClientSocket httpSocket) {
        HTTPRequest httpRequest = httpSocket.getRequest();
        logger.log(httpRequest.getStatusHeader());
        Response httpResponse = router.route(httpRequest);
        httpSocket.sendResponse(httpResponse);
    }

    private class ServerTask implements Runnable {

        private final ClientSocket socket;

        public ServerTask(ClientSocket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            processRequest(socket);
        }
    }
}

