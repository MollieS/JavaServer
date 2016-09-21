package httpserver.httpmessages;

import httpserver.ResponseBuilder;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HTTPRequestHandlerTest {

    private ResponseBuilderSpy responseBuilderSpy = new ResponseBuilderSpy();
    private HTTPRequestHandler httpRequestHandler = new HTTPRequestHandler(responseBuilderSpy);

    @Test
    public void returnsA200ResponseForGETToAKnownURI() {
        HTTPRequest httpRequest = new HTTPRequest("GET", "/");

        httpRequestHandler.handle(httpRequest);

        assertTrue(responseBuilderSpy.buildResponseWasCalled);
    }

    @Test
    public void returnsA404ResponseForAGETToUnknownURI() {
        HTTPRequest httpRequest = new HTTPRequest("GET", "/foobar");

        httpRequestHandler.handle(httpRequest);

        assertTrue(responseBuilderSpy.buildResponseWasCalled);
    }

    private class ResponseBuilderSpy implements ResponseBuilder {

        private boolean buildResponseWasCalled = false;

        @Override
        public HTTPResponse buildResponse(String method, String path) {
            buildResponseWasCalled = true;
            return null;
        }
    }
}
