package httpserver;

public class HTTPRequestParser {

    public static HTTPRequest handle(String request) {
        String[] requestElements = request.split(" ");
        String method = requestElements[0];
        String requestURI = requestElements[1];
        return new HTTPRequest(method, requestURI);
    }
}
