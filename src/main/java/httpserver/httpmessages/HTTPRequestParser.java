package httpserver.httpmessages;

import httpserver.routing.Method;

import java.net.URI;

public class HTTPRequestParser {

    public HTTPRequest parse(String request) {
        String[] requestElements = request.split(" ");
        Method requestMethod = getMethod(requestElements[0]);
        URI uri = URI.create(requestElements[1]);
        HTTPRequest httpRequest = new HTTPRequest(requestMethod, uri.getPath());
        if (request.contains("data=")) {
            parseData(request, httpRequest);
        }
        if (uri.getQuery() != null) {
            httpRequest.setParams(uri.getQuery());
        }
        return httpRequest;
    }

    private void parseData(String request, HTTPRequest httpRequest) {
        String[] lines = request.split("\n");
        String lastLine = lines[lines.length - 1];
        httpRequest.setData(lastLine.trim());
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
