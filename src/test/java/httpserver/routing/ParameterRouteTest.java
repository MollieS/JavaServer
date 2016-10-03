package httpserver.routing;

import httpserver.httpmessages.HTTPRequest;
import httpserver.httpmessages.HTTPResponse;
import org.junit.Test;

import java.nio.charset.Charset;

import static httpserver.routing.Method.GET;
import static httpserver.routing.Method.POST;
import static org.junit.Assert.assertEquals;

public class ParameterRouteTest {

    private ParameterRoute parameterRoute = new ParameterRoute("/parameters", GET);

    @Test
    public void addsParametersToResponseBody() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/parameters");
        httpRequest.setParams("variable_1=parameter");

        HTTPResponse httpResponse = parameterRoute.performAction(httpRequest);
        String body = new String(httpResponse.getBody(), Charset.forName("UTF-8"));

        assertEquals("variable_1 = parameter", body);
    }

    @Test
    public void addsMultipleParametersToResponseBody() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/parameters");
        httpRequest.setParams("variable_1=parameter&variable_2=parameter2");

        HTTPResponse httpResponse = parameterRoute.performAction(httpRequest);
        String body = new String(httpResponse.getBody(), Charset.forName("UTF-8"));

        assertEquals("variable_1 = parameter\nvariable_2 = parameter2", body);
    }

    @Test
    public void canFormatSpecialCharacters() {
        HTTPRequest httpRequest = new HTTPRequest(GET, "/parameters");
        httpRequest.setParams("variable_1=&,=!&variable_2=stuff");

        HTTPResponse httpResponse = parameterRoute.performAction(httpRequest);
        String body = new String(httpResponse.getBody(), Charset.forName("UTF-8"));

        assertEquals("variable_1 = &,=!\nvariable_2 = stuff", body);
    }

    @Test
    public void canGiveA405ForMethodNotAllowed() {
        HTTPRequest httpRequest = new HTTPRequest(POST, "/parameters");

        HTTPResponse httpResponse = parameterRoute.performAction(httpRequest);

        assertEquals(405, httpResponse.getStatusCode());
    }
}
