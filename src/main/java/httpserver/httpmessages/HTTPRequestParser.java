package httpserver.httpmessages;

public class HTTPRequestParser {

    public HTTPRequest parse(String request) {
        String[] requestElements = request.split(" ");
        String method = requestElements[0];
        String requestURI = requestElements[1];
        return new HTTPRequest(method, requestURI);
    }
}
