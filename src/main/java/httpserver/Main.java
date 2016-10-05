package httpserver;

import httpserver.httpmessages.HTTPRequestHandler;
import httpserver.resourcemanagement.HTTPResourceHandler;
import httpserver.resourcemanagement.ResourceParser;
import httpserver.routing.Route;
import httpserver.routing.Router;
import httpserver.server.HTTPServer;
import httpserver.server.HTTPSocketServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ServerRunner serverRunner = new ServerRunner();
        int port = serverRunner.parsePort(args);
        String path = serverRunner.parseDirectoryPath(args);
        String url = serverRunner.buildUrl(args);
        String resources = "/Users/molliestephenson/Java/Server/HTTPServer/src/main/resources/form";
        HTTPResourceHandler resourceHandler = new HTTPResourceHandler(path, new ResourceParser());
        List<Route> registeredRoutes = serverRunner.createRoutes(url, resources, resourceHandler);
        HTTPRequestHandler httpRequestHandler = new HTTPRequestHandler(resourceHandler, new Router(registeredRoutes));
        SocketServer socketServer = null;
            try {
                socketServer = new HTTPSocketServer(new ServerSocket(port));
            } catch (IOException e) {
                e.printStackTrace();
            }
        HTTPServer httpServer = new HTTPServer(socketServer, httpRequestHandler);
        while(true) {
            httpServer.start();
        }
    }
}
