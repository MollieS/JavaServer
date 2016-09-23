package httpserver;

import httpserver.httpmessages.HTTPRequestHandler;
import httpserver.httpmessages.HTTPRequestParser;
import httpserver.httpmessages.HTTPResponseBuilder;
import httpserver.resourcemanagement.HTTPResourceHandler;
import httpserver.resourcemanagement.ResourceParser;
import httpserver.server.HTTPServer;
import httpserver.server.HTTPSocketServer;
import httpserver.routing.Router;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) {
        HTTPServer httpServer = null;
            try {
                SocketServer socketServer = new HTTPSocketServer(new ServerSocket(5000));
                String path = "/Users/molliestephenson/Java/Server/cob_spec/public";
                HTTPResourceHandler resourceHandler = new HTTPResourceHandler(path, new ResourceParser());
                HTTPRequestHandler httpRequestHandler = new HTTPRequestHandler(new HTTPResponseBuilder(resourceHandler), new Router());
                HTTPRequestParser httpRequestParser = new HTTPRequestParser();
                httpServer = new HTTPServer(socketServer, httpRequestHandler, httpRequestParser);
            } catch (IOException e) {
                e.printStackTrace();
            }
        while(true) {
            httpServer.start();
        }
    }
}
