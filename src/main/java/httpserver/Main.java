package httpserver;

import httpserver.resourcemanagement.HTTPResourceHandler;
import httpserver.resourcemanagement.ResourceParser;
import httpserver.routing.FileRoute;
import httpserver.routing.Route;
import httpserver.routing.Router;
import httpserver.server.HTTPLogger;
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
        HTTPResourceHandler resourceHandler = new HTTPResourceHandler(path, new ResourceParser());
        SocketServer socketServer = null;
        List<Route> registeredRoutes = null;
        HTTPLogger logger = null;
            try {
                registeredRoutes = serverRunner.createRoutes(url, resourceHandler, path);
                socketServer = new HTTPSocketServer(new ServerSocket(port));
                logger = serverRunner.createLogger(path + "/logs");
            } catch (IOException e) {
                e.printStackTrace();
            }
        Router router = new Router(new FileRoute(resourceHandler), registeredRoutes);
        HTTPServer httpServer = new HTTPServer(socketServer, router, logger);
        while(true) {
            httpServer.start();
        }
    }
}
