package httpserver.routing;

import httpserver.Response;
import httpserver.httprequests.HTTPRequest;
import httpserver.httpresponse.ResponseHeader;
import org.junit.Test;

import static httpserver.routing.Method.GET;
import static org.junit.Assert.assertEquals;

public class LogsRouteTest {

    private LogsRoute logsRoute = new LogsRoute(GET);

    @Test
    public void sendsA401IfNotAuthIsGiven() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/logs");

        Response httpResponse = logsRoute.performAction(httpRequest);

        assertEquals(401, httpResponse.getStatusCode());
        assertEquals("Unauthorized", httpResponse.getReasonPhrase());
    }

    @Test
    public void hasAnAuthenticationChallenge() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/logs");

        Response httpResponse = logsRoute.performAction(httpRequest);

        assertEquals("Basic realm=/logs", httpResponse.getValue(ResponseHeader.AUTH));
    }
}
