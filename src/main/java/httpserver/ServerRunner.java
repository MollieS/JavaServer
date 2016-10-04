package httpserver;

import httpserver.routing.*;

import java.util.ArrayList;
import java.util.List;

import static httpserver.routing.Method.*;

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
            if(arguments[i].equals("-d")) {
                String path = arguments[i + 1];
                if (path.endsWith("/")) {
                    return path.substring(0, path.length() - 1);
                }
                return arguments[i + 1];
            }
        }
        return PUBLIC_DIR;
    }

    public List<Route> createRoutes(String location, String resourcesPath) {
        List<Route> registeredRoutes = new ArrayList<>();
        registeredRoutes.add(new CoffeeRoute("/coffee", GET));
        registeredRoutes.add(new TeaRoute("/tea", GET));
        registeredRoutes.add(new MethodOptionsRoute("/method_options", GET, POST, PUT, OPTIONS, HEAD));
        registeredRoutes.add(new FormRoute("/form", resourcesPath, GET, POST, PUT, DELETE));
        registeredRoutes.add(new ParameterRoute("/parameters", GET));
        registeredRoutes.add(new RedirectRoute("/redirect", location, GET));
        return registeredRoutes;
    }

    public String buildUrl(String[] args) {
        int port = parsePort(args);
        return URL + port;
    }
}
