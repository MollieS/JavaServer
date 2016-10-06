package httpserver.routing;

import httpserver.Response;
import httpserver.httprequests.HTTPRequest;
import httpserver.httpresponse.ResponseHeader;
import org.junit.Test;

import java.util.Base64;

import static httpserver.ByteArrayConverter.getString;
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

        assertEquals("Basic realm=/logs", getString(httpResponse.getValue(ResponseHeader.AUTH)));
    }

    @Test
    public void sendsA200PasswordIfCredentialsMatch() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/logs");
        byte[] codedCredentials = Base64.getEncoder().encode("admin:hunter2".getBytes());
        String credentials = getString(codedCredentials);
        httpRequest.setAuthorization(credentials);

        Response response = logsRoute.performAction(httpRequest);

        assertEquals(200, response.getStatusCode());
    }
}
