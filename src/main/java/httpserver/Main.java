package httpserver;

import httpserver.httpmessages.HTTPRequestHandler;
import httpserver.httpmessages.HTTPRequestParser;
import httpserver.httpmessages.HTTPResponseBuilder;
import httpserver.resourcemanagement.HTTPResourceHandler;
import httpserver.resourcemanagement.ResourceParser;
import httpserver.routing.*;
import httpserver.server.HTTPServer;
import httpserver.server.HTTPSocketServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.List;

import static httpserver.routing.Method.*;

public class Main {

    public static void main(String[] args) {
        HTTPServer httpServer = null;
            try {
                SocketServer socketServer = new HTTPSocketServer(new ServerSocket(5000));
                String path = "/Users/molliestephenson/Java/Server/cob_spec/public";
                String resources = "/Users/molliestephenson/Java/Server/HTTPServer/src/main/resources/form";
                HTTPResourceHandler resourceHandler = new HTTPResourceHandler(path, new ResourceParser());
                List<Route> registeredRoutes = Arrays.asList(new CoffeeRoute("/coffee", GET), new TeaRoute("/tea", GET), new MethodOptionsRoute("/method_options", GET, POST, PUT, OPTIONS, HEAD), new FormRoute("/form", resources, GET, POST, PUT, DELETE));
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
