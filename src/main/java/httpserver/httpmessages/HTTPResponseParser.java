package httpserver.httpmessages;

public class HTTPResponseParser {

    private static final String PROTOCOL_VERSION = "HTTP/1.1";

    public String parse(HTTPResponse httpResponse) {
        String response = PROTOCOL_VERSION;
        response = addHeader(httpResponse, response);
        response = addBody(httpResponse, response);
        response += "\n";
        return response;
    }

    private String addBody(HTTPResponse httpResponse, String response) {
        if (httpResponse.hasBody()) {
            response += "\n\n";
            response += httpResponse.getBody();
        }
        return response;
    }

    private String addHeader(HTTPResponse httpResponse, String response) {
        response += " " + httpResponse.getStatusCode();
        response += " " + httpResponse.getReasonPhrase();
        return response;
    }
}
