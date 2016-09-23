package httpserver.httpmessages;

import httpserver.ResponseBuilder;
import httpserver.routing.*;
import org.junit.Test;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static httpserver.routing.Method.*;
import static org.junit.Assert.assertEquals;

public class HTTPRequestHandlerTest {

    private HTTPRequestHandler httpRequestHandler;
    private List<Route> routes = Arrays.asList(new CoffeeRoute("/coffee", GET), new TeaRoute("/tea", GET), new MethodOptionsRoute("/method_options", GET, POST, PUT, OPTIONS, HEAD));
    private Router router = new Router(routes);

    @Test
    public void returnsA200ResponseForGETToAKnownURI() {
        createRequestHandler(200, "OK");

        HTTPRequest httpRequest = new HTTPRequest(GET, "/");

        HTTPResponse httpResponse = httpRequestHandler.handle(httpRequest);

        assertEquals("200", httpResponse.getStatusCode());
    }

    @Test
    public void returnsA404ToAnUnknownURI() {
        createRequestHandler(404, "Not Found");

        HTTPRequest httpRequest = new HTTPRequest(GET, "/foobar");

        HTTPResponse httpResponse = httpRequestHandler.handle(httpRequest);

        assertEquals("404", httpResponse.getStatusCode());
    }

    @Test
    public void returnsA418ResponseForAGETToCoffee() {
        HTTPRequestHandler httpRequestHandler = new HTTPRequestHandler(new ResponseBuilderDummy(), router);

        HTTPRequest httpRequest = new HTTPRequest(GET, "/coffee");

        HTTPResponse httpResponse = httpRequestHandler.handle(httpRequest);

        assertEquals("418", httpResponse.getStatusCode());
        assertEquals("I'm a teapot", httpResponse.getReasonPhrase());
    }

    @Test
    public void returnsA200ResponseForAGETToTea() {
        createRequestHandler(404, "Not Found");

        HTTPRequest httpRequest = new HTTPRequest(GET, "/tea");

        HTTPResponse httpResponse = httpRequestHandler.handle(httpRequest);

        assertEquals("200", httpResponse.getStatusCode());
    }

    @Test
    public void returnsA405ResponseForAPOST() {
        HTTPRequestHandler httpRequestHandler = new HTTPRequestHandler(new ResponseBuilderDummy(), router);

        HTTPRequest httpRequest = new HTTPRequest(POST, "/file1");

        HTTPResponse httpResponse = httpRequestHandler.handle(httpRequest);

        assertEquals("405", httpResponse.getStatusCode());
    }

    @Test
    public void returnsA200ResponseForOptionsRequestToMethodOptions() {
        createRequestHandler(404, "Not Found");

        HTTPRequest httpRequest = new HTTPRequest(OPTIONS, "/method_options");

        HTTPResponse httpResponse = httpRequestHandler.handle(httpRequest);

        assertEquals("200", httpResponse.getStatusCode());
    }

    private void createRequestHandler(int code, String reason) {
        ResponseBuilderFake responseBuilderFake = new ResponseBuilderFake(code, reason);
        httpRequestHandler = new HTTPRequestHandler(responseBuilderFake, router);
    }

    private class ResponseBuilderFake implements ResponseBuilder {

        private final int code;
        private final String statusCode;

        public ResponseBuilderFake(int code, String statusCode) {
            this.code = code;
            this.statusCode = statusCode;
        }

        @Override
        public HTTPResponse buildResponse(Method method, URI path) {
            return new HTTPResponse(code, statusCode);
        }

    }

    private class ResponseBuilderDummy implements ResponseBuilder {

        @Override
        public HTTPResponse buildResponse(Method method, URI path) {
            return null;
        }
    }
}
