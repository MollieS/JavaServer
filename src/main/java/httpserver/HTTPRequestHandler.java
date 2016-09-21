package httpserver;

public class HTTPRequestHandler {

    public static HTTPResponse handle(HTTPRequest httpRequest) {
        if (httpRequest.getRequestURI() == "/") {
            return new HTTPResponse(200, "OK");
        }
        return new HTTPResponse(404, "OK");
    }
}
