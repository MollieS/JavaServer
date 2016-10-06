package httpserver.routing;

import httpserver.Response;
import httpserver.httprequests.HTTPRequest;
import httpserver.httpresponse.ResponseHeader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import static httpserver.ByteArrayConverter.getString;
import static httpserver.routing.Method.GET;
import static org.junit.Assert.assertEquals;

public class LogsRouteTest {

    private String logsPath = getClass().getClassLoader().getResource("directory").getPath() + "/logs";
    private LogsRoute logsRoute = new LogsRoute(logsPath, GET);
    private File file = new File(logsPath);

    @Before
    public void setUp() throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    @After
    public void tearDown() throws IOException {
        if (file.exists()) {
            file.delete();
        }
    }

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
        addAuthorization(httpRequest);

        Response response = logsRoute.performAction(httpRequest);

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void readsContentsOfLogsAnReturnsThemInBody() throws IOException {
        Files.write(file.toPath(), "GET / HTTP/1.1".getBytes());
        HTTPRequest httpRequest = new HTTPRequest(GET, "/logs");
        addAuthorization(httpRequest);

        Response response = logsRoute.performAction(httpRequest);
        String body = getString(response.getBody());

        assertEquals(body, "GET / HTTP/1.1");
    }

    @Test
    public void returnsAMessageInBodyIfLogsCannotBeRead() {
        file.delete();
        HTTPRequest httpRequest = new HTTPRequest(GET, "/logs");
        addAuthorization(httpRequest);

        Response response = logsRoute.performAction(httpRequest);
        String body = getString(response.getBody());

        assertEquals(body, "Cannot read logs");
    }

    private void addAuthorization(HTTPRequest httpRequest) {
        byte[] codedCredentials = Base64.getEncoder().encode("admin:hunter2".getBytes());
        String credentials = getString(codedCredentials);
        httpRequest.setAuthorization(credentials);
    }

}
