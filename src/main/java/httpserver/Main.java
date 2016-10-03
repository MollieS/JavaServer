package httpserver;

import httpserver.httpmessages.HTTPRequestHandler;
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
                CoffeeRoute coffeeRoute = new CoffeeRoute("/coffee", GET);
                TeaRoute teaRoute = new TeaRoute("/tea", GET);
                MethodOptionsRoute methodOptionsRoute = new MethodOptionsRoute("/method_options", GET, POST, PUT, OPTIONS, HEAD);
                FormRoute formRoute = new FormRoute("/form", resources, GET, POST, PUT, DELETE);
                ParameterRoute parameterRoute = new ParameterRoute("/parameters", GET);
                List<Route> registeredRoutes = Arrays.asList(coffeeRoute, teaRoute, methodOptionsRoute, formRoute, parameterRoute);
                HTTPRequestHandler httpRequestHandler = new HTTPRequestHandler(resourceHandler, new Router(registeredRoutes));
                httpServer = new HTTPServer(socketServer, httpRequestHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        while(true) {
            httpServer.start();
        }
    }
}
