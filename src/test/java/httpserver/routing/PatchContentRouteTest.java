package httpserver.routing;

import httpserver.ResourceHandler;
import httpserver.Response;
import httpserver.httprequests.RequestFake;
import httpserver.resourcemanagement.HTTPResourceHandler;
import httpserver.resourcemanagement.ResourceParser;
import org.junit.Test;

import static httpserver.httpresponses.HTTPResponseTest.getString;
import static httpserver.routing.Method.GET;
import static httpserver.routing.Method.PATCH;
import static org.junit.Assert.assertEquals;

public class PatchContentRouteTest {

    String path = getClass().getClassLoader().getResource("directory").getPath();
    ResourceHandler resourceHandler = new HTTPResourceHandler(path, new ResourceParser());

    @Test
    public void returnsA200ForAGetRequest() {
        RequestFake requestFake = new RequestFake(GET, "/partial-content.txt");

        PatchContentRoute patchContentRoute = new PatchContentRoute(resourceHandler, GET, PATCH);
        Response httpResponse = patchContentRoute.performAction(requestFake);

        assertEquals(200, httpResponse.getStatusCode());
        assertEquals("OK", httpResponse.getReasonPhrase());
    }

    @Test
    public void sendsA204ForAPatch() {
        RequestFake requestFake = new RequestFake(PATCH, "/partial-content.txt");

        PatchContentRoute patchContentRoute = new PatchContentRoute(resourceHandler, GET, PATCH);
        Response httpResponse = patchContentRoute.performAction(requestFake);

        assertEquals(204, httpResponse.getStatusCode());
        assertEquals("No Content", httpResponse.getReasonPhrase());
    }

    @Test
    public void sendsUpdatedBodyAfterPatch() {
        RequestFake patchRequest = new RequestFake(PATCH, "/partial-content.txt");
        patchRequest.addEtag("etag");
        patchRequest.addBody("new content");

        PatchContentRoute patchContentRoute = new PatchContentRoute(resourceHandler, GET, PATCH);
        patchContentRoute.performAction(patchRequest);

        RequestFake getRequest = new RequestFake(GET, "/partial-content.txt");
        Response httpResponse = patchContentRoute.performAction(getRequest);

        String body = getString(httpResponse.getBody());

        assertEquals("new content", body);
    }

}
