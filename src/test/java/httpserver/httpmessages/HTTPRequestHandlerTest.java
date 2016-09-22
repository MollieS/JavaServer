package httpserver.httpmessages;

import httpserver.ResponseBuilder;
import httpserver.server.Router;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HTTPRequestHandlerTest {

    private HTTPRequestHandler httpRequestHandler;

    @Test
    public void returnsA200ResponseForGETToAKnownURI() {
        createRequestHandler(200, "OK");

        HTTPRequest httpRequest = new HTTPRequest("GET", "/");

        HTTPResponse httpResponse = httpRequestHandler.handle(httpRequest);

        assertEquals("200", httpResponse.getStatusCode());
    }

    @Test
    public void returnsA404ToAnUnknownURI() {
        createRequestHandler(404, "Not Found");

        HTTPRequest httpRequest = new HTTPRequest("GET", "/foobar");

        HTTPResponse httpResponse = httpRequestHandler.handle(httpRequest);

        assertEquals("404", httpResponse.getStatusCode());
    }

    @Test
    public void returnsA418ResponseForAGETToCoffee() {
        createRequestHandler(404, "Not Found");

        HTTPRequest httpRequest = new HTTPRequest("GET", "/coffee");

        HTTPResponse httpResponse = httpRequestHandler.handle(httpRequest);

        assertEquals("418", httpResponse.getStatusCode());
        assertEquals("I'm a teapot", httpResponse.getReasonPhrase());
    }

    @Test
    public void returnsA200ResponseForAGETToTea() {
        createRequestHandler(404, "Not Found");

        HTTPRequest httpRequest = new HTTPRequest("GET", "/tea");

        HTTPResponse httpResponse = httpRequestHandler.handle(httpRequest);

        assertEquals("200", httpResponse.getStatusCode());
    }

    private void createRequestHandler(int code, String reason) {
        ResponseBuilderFake responseBuilderFake = new ResponseBuilderFake(code, reason);
        httpRequestHandler = new HTTPRequestHandler(responseBuilderFake, new Router());
    }

    private class ResponseBuilderFake implements ResponseBuilder {

        private final int code;
        private final String statusCode;

        public ResponseBuilderFake(int code, String statusCode) {
            this.code = code;
            this.statusCode = statusCode;
        }

        @Override
        public HTTPResponse buildResponse(String method, String path) {
            return new HTTPResponse(code, statusCode);
        }
    }
}
