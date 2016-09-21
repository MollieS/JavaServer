package httpserver.httpmessages;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HTTPResponseParserTest {

    @Test
    public void returnsHeaderForAResponseWithNoBody() {
        HTTPResponseParser httpResponseParser = new HTTPResponseParser();

        HTTPResponse httpResponse = new HTTPResponse(200, "OK");

        String response = httpResponseParser.parse(httpResponse);

        assertEquals("HTTP/1.1 200 OK\n", response);
    }

    @Test
    public void returnsResponseForAHTTPResponseWithABody() {
        HTTPResponseParser httpResponseParser = new HTTPResponseParser();

        HTTPResponse httpResponse = new HTTPResponse(200, "OK");
        httpResponse.setBody("This is the body");

        String response = httpResponseParser.parse(httpResponse);

        assertEquals("HTTP/1.1 200 OK\n\nThis is the body\n", response);
    }
}
