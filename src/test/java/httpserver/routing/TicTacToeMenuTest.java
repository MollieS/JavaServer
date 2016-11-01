package httpserver.routing;

import httpserver.Request;
import httpserver.Response;
import httpserver.httprequests.RequestFake;
import org.junit.Test;

import static httpserver.httpresponse.ResponseHeader.CONTENT_TYPE;
import static httpserver.httpresponses.HTTPResponseTest.getString;
import static httpserver.routing.Method.GET;
import static org.junit.Assert.assertEquals;

public class TicTacToeMenuTest {

    @Test
    public void sendsA200Response() {
        Request request = new RequestFake(GET, "/ttt-menu");
        TicTacToeMenuRoute ticTacToeMenuRoute = new TicTacToeMenuRoute("/ttt-menu", GET);

        Response response = ticTacToeMenuRoute.performAction(request);

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void hasHTMLBody() {
        Request request = new RequestFake(GET, "/ttt-menu");
        TicTacToeMenuRoute ticTacToeMenuRoute = new TicTacToeMenuRoute("/ttt-menu", GET);

        Response response = ticTacToeMenuRoute.performAction(request);
        String contentType = getString(response.getValue(CONTENT_TYPE));

        assertEquals("text/html", contentType);
    }
}
