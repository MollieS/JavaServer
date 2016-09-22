package httpserver.httpmessages;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HTTPResponseParserTest {

    private HTTPResponseParser httpResponseParser = new HTTPResponseParser();
    @Test
    public void returnsHeaderForAResponseWithNoBody() {
        HTTPResponse httpResponse = new HTTPResponse(200, "OK");

        String response = httpResponseParser.parse(httpResponse);

        assertEquals("HTTP/1.1 200 OK\n", response);
    }

    @Test
    public void returnsResponseForAHTTPResponseWithABody() {
        HTTPResponse httpResponse = new HTTPResponse(200, "OK");
        httpResponse.setBody("This is the body".getBytes());

        String response = httpResponseParser.parse(httpResponse);

        assertEquals("HTTP/1.1 200 OK\n\nThis is the body\n", response);
    }

    @Test
    public void returnsAHeaderForA404Response() {
        HTTPResponse httpResponse = new HTTPResponse(404, "Not Found");

        String response = httpResponseParser.parse(httpResponse);

        assertEquals("HTTP/1.1 404 Not Found\n", response);
    }
}
