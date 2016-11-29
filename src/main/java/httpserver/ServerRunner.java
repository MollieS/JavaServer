package httpserver;

import httpserver.routing.Route;
import httpserver.server.HTTPLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerRunner {

    private final static String PUBLIC_DIR = "/Users/molliestephenson/Java/Server/cob_spec/public";
    private final static int DEFAULT_PORT = 5000;
    private final static String URL = "http://localhost:";

    public int parsePort(String[] arguments) {
        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i].equals("-p")) {
                return Integer.valueOf(arguments[i + 1]);
            }
        }
        return DEFAULT_PORT;
    }

    public String parseDirectoryPath(String[] arguments) {
        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i].equals("-d")) {
                String path = arguments[i + 1];
                if (path.endsWith("/")) {
                    return path.substring(0, path.length() - 1);
                }
                return arguments[i + 1];
            }
        }
        return PUBLIC_DIR;
    }

    public HTTPLogger createLogger(String path) {
        return new HTTPLogger(path);
    }

    public List<Route> createRoutes(String location, ResourceHandler resourceHandler, String path) throws IOException {
        List<Route> registeredRoutes = new ArrayList<>();
        return registeredRoutes;
    }

    public String buildUrl(String[] args) {
        int port = parsePort(args);
        return URL + port;
    }
}
