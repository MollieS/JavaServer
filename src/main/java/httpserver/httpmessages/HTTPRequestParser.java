package httpserver.httpmessages;

import httpserver.routing.Method;

public class HTTPRequestParser {

    public HTTPRequest parse(String request) {
        String[] requestElements = request.split(" ");
        Method method = Method.valueOf(requestElements[0]);
        String requestURI = requestElements[1];
        return new HTTPRequest(method, requestURI);
    }
}
