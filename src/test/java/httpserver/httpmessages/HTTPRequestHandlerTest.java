package httpserver.httpmessages;

import httpserver.ResponseBuilder;
import httpserver.routing.Method;
import httpserver.routing.Router;
import org.junit.Test;

import java.net.URI;

import static httpserver.routing.Method.GET;
import static httpserver.routing.Method.OPTIONS;
import static httpserver.routing.Method.POST;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HTTPRequestHandlerTest {

    private HTTPRequestHandler httpRequestHandler;

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
        HTTPRequestHandler httpRequestHandler = new HTTPRequestHandler(new ResponseBuilderDummy(), new Router());

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
        HTTPRequestHandler httpRequestHandler = new HTTPRequestHandler(new ResponseBuilderDummy(), new Router());

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

    @Test
    public void anOptionsRequestReturnsAllowedMethods() {
        ResponseBuilderSpy spy = new ResponseBuilderSpy();
        HTTPRequestHandler httpRequestHandler = new HTTPRequestHandler(spy, new Router());

        HTTPRequest httpRequest = new HTTPRequest(OPTIONS, "/method_options");

        httpRequestHandler.handle(httpRequest);

        assertTrue(spy.addAllowedMethodsHasBeenCalled);
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
        public HTTPResponse buildResponse(Method method, URI path) {
            return new HTTPResponse(code, statusCode);
        }

        @Override
        public void addAllowedMethods(HTTPResponse httpResponse, HTTPRequest httpRequest, Router router) {

        }
    }

    private class ResponseBuilderSpy implements ResponseBuilder {

        public boolean addAllowedMethodsHasBeenCalled = false;

        @Override
        public HTTPResponse buildResponse(Method method, URI path) {
            return new HTTPResponse(200, "OK");
        }

        @Override
        public void addAllowedMethods(HTTPResponse httpResponse, HTTPRequest httpRequest, Router router) {
            addAllowedMethodsHasBeenCalled = true;
        }
    }

    private class ResponseBuilderDummy implements ResponseBuilder {

        @Override
        public HTTPResponse buildResponse(Method method, URI path) {
            return null;
        }

        @Override
        public void addAllowedMethods(HTTPResponse httpResponse, HTTPRequest httpRequest, Router router) {

        }
    }
}
