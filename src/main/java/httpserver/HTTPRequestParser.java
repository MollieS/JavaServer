package httpserver;

public class HTTPRequestParser {

    public static HTTPRequest parse(String request) {
        String[] requestElements = request.split(" ");
        String method = requestElements[0];
        String requestURI = requestElements[1];
        return new HTTPRequest(method, requestURI);
    }
}
