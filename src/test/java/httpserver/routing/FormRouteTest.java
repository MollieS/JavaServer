package httpserver.routing;

import httpserver.Request;
import httpserver.Response;
import httpserver.httprequests.RequestFake;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static httpserver.routing.Method.*;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

public class FormRouteTest {

    private String path = getClass().getClassLoader().getResource("directory").getPath() + ("/form");
    private FormRoute formRoute = new FormRoute(path, GET, POST, PUT, DELETE);
    private RequestFake postRequest = new RequestFake(POST, "/form");
    private RequestFake getRequest = new RequestFake(GET, "/form");

    @Before
    public void setUp() throws IOException {
        Files.write(Paths.get(path), "".getBytes());
    }

    @After
    public void tearDown() throws IOException {
        if (new File(path).exists()) {
            Files.delete(Paths.get(path));
        }
    }

    @Test
    public void canPostToFormAndGetA200Response() {
        postRequest.setData("data=fatcat");

        Response httpResponse = formRoute.performAction(postRequest);

        assertEquals(200, httpResponse.getStatusCode());
    }

    @Test
    public void canPostToFormAndGetDataInBodyOfGetResponse() {
        postRequest.setData("data=fatcat");

        formRoute.performAction(postRequest);

        Response httpResponse = formRoute.performAction(getRequest);
        String body = new String(httpResponse.getBody(), Charset.forName("UTF-8"));

        assertEquals("data=fatcat", body);
    }

    @Test
    public void canPutToFormAndGetDataInBodyOfGetResponse() {
        postRequest.setData("data=hello");

        formRoute.performAction(postRequest);

        RequestFake httpRequest2 = new RequestFake(PUT, "/");
        httpRequest2.setData("data=goodbye");

        formRoute.performAction(httpRequest2);

        Response httpResponse = formRoute.performAction(getRequest);
        String body = new String(httpResponse.getBody(), Charset.forName("UTF-8"));

        assertEquals("data=goodbye", body);
    }

    @Test
    public void canAcceptADeleteRequest() {
        postRequest.setData("data=hello");
        formRoute.performAction(postRequest);

        RequestFake httpRequest2 = new RequestFake(DELETE, "/form");
        Response httpResponse = formRoute.performAction(httpRequest2);

        assertEquals(200, httpResponse.getStatusCode());
    }

    @Test
    public void canDeleteFromAForm() {
        postRequest.setData("data=hello");
        formRoute.performAction(postRequest);

        RequestFake httpRequest2 = new RequestFake(DELETE, "/form");
        Response httpResponse = formRoute.performAction(httpRequest2);

        assertNull(httpResponse.getBody());
    }

    @Test
    public void canGiveAMethodNotAllowedResponse() {
        Request httpRequest = new RequestFake(BOGUS, "/form");

        Response httpResponse = formRoute.performAction(httpRequest);

        assertEquals(405, httpResponse.getStatusCode());
    }

    @Test(expected = FormManagerException.class)
    public void throwsAnExceptionIfFormFileCannotBeAccessed() {
        FormRoute formRoute = new FormRoute("/bad/path", GET);

        Request httpRequest = new RequestFake(GET, "/form");

        formRoute.performAction(httpRequest);
    }
}
