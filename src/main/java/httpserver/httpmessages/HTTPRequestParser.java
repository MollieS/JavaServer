package httpserver.httpmessages;

import httpserver.routing.Method;

import java.net.URI;

public class HTTPRequestParser {

    public HTTPRequest parse(String request) {
        System.out.println(request);
        String[] requestElements = request.split(" ");
        Method requestMethod = getMethod(requestElements[0]);
        URI uri = URI.create(requestElements[1]);
        String path = uri.getPath();
        String query = uri.getQuery();
        System.out.println(query);
        HTTPRequest httpRequest = new HTTPRequest(requestMethod, path);
        httpRequest.setData(query);
        return httpRequest;
    }

    private Method getMethod(String requestMethod) {
        for (Method method : Method.values()) {
            if (method.name().equals(requestMethod)) {
                return method;
            }
        }
        return Method.BOGUS;
    }
}
