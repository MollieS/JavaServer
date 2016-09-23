package httpserver.routing;

import httpserver.httpmessages.HTTPRequest;
import httpserver.httpmessages.HTTPResponse;
import org.junit.Test;

import java.nio.charset.Charset;

import static httpserver.routing.Method.*;
import static org.junit.Assert.assertEquals;

public class FormRouteTest {

    private String path = getClass().getClassLoader().getResource("directory").getPath() + ("form");
    private FormRoute formRoute = new FormRoute("/form", path, GET, POST, PUT, DELETE);

    @Test
    public void canPostToFormAndGetA200Response() {
        HTTPRequest httpRequest = new HTTPRequest(POST, "/form");
        httpRequest.setData("data=fatcat");

        HTTPResponse httpResponse = formRoute.performAction(httpRequest);

        assertEquals("200", httpResponse.getStatusCode());
    }

    @Test
    public void canPostToFormAndGetDataInBodyOfGetResponse() {
        HTTPRequest httpRequest = new HTTPRequest(POST, "/form");
        httpRequest.setData("data=fatcat");

        formRoute.performAction(httpRequest);

        HTTPRequest httpRequest2 = new HTTPRequest(GET, "/form");

        HTTPResponse httpResponse = formRoute.performAction(httpRequest2);
        String body = new String(httpResponse.getBody(), Charset.forName("UTF-8"));

        assertEquals("data=fatcat", body);
    }

    @Test
    public void canPutToFormAndGetDataInBodyOfGetResponse() {
        HTTPRequest httpRequest = new HTTPRequest(POST, "/form");
        httpRequest.setData("data=hello");

        formRoute.performAction(httpRequest);

        HTTPRequest httpRequest2 = new HTTPRequest(PUT, "/");
        httpRequest2.setData("data=goodbye");

        formRoute.performAction(httpRequest2);

        HTTPRequest httpRequest3 = new HTTPRequest(GET, "/form");

        HTTPResponse httpResponse = formRoute.performAction(httpRequest3);
        String body = new String(httpResponse.getBody(), Charset.forName("UTF-8"));

        assertEquals("data=goodbye", body);
    }

}
