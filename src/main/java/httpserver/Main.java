package httpserver;

import httpserver.httpmessages.HTTPRequestHandler;
import httpserver.httpmessages.HTTPRequestParser;
import httpserver.httpmessages.HTTPResponseBuilder;
import httpserver.resourcemanagement.HTTPResourceHandler;
import httpserver.resourcemanagement.ResourceParser;
import httpserver.routing.CoffeeRoute;
import httpserver.routing.Route;
import httpserver.routing.Router;
import httpserver.routing.TeaRoute;
import httpserver.server.HTTPServer;
import httpserver.server.HTTPSocketServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.List;

import static httpserver.routing.Method.GET;

public class Main {

    public static void main(String[] args) {
        HTTPServer httpServer = null;
            try {
                SocketServer socketServer = new HTTPSocketServer(new ServerSocket(5000));
                String path = "/Users/molliestephenson/Java/Server/cob_spec/public";
                HTTPResourceHandler resourceHandler = new HTTPResourceHandler(path, new ResourceParser());
                List<Route> registeredRoutes = Arrays.asList(new CoffeeRoute("/coffee", GET), new TeaRoute("/tea", GET));
                HTTPRequestHandler httpRequestHandler = new HTTPRequestHandler(new HTTPResponseBuilder(resourceHandler), new Router(registeredRoutes));
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
