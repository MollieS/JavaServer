package httpserver.httpmessages;

public class HTTPResponseParser {

    private static final String PROTOCOL_VERSION = "HTTP/1.1";

    public String parse(HTTPResponse httpResponse) {
        String response = PROTOCOL_VERSION;
        response += " " + httpResponse.getStatusCode();
        response += " " + httpResponse.getReasonPhrase();
        if (httpResponse.hasBody()) {
            response += "\n\n";
            response += httpResponse.getBody();
        }
        response += "\n";
        return response;
    }
}
