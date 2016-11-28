package httpserver.routing;

import httpserver.Request;
import httpserver.Resource;
import httpserver.Response;
import httpserver.httprequests.RequestHeader;
import httpserver.httpresponse.HTTPResponse;
import httpserver.httpresponse.ResponseHeader;
import httpserver.resourcemanagement.HTMLResource;

import java.util.HashMap;
import java.util.Random;

import static httpserver.httpresponse.ResponseHeader.COOKIE;
import static httpserver.httpresponse.StatusCode.OK;

public class TicTacToeMenuRoute extends Route {

    private HashMap<ResponseHeader, byte[]> headers = new HashMap<>();

    public TicTacToeMenuRoute(String uri, Method... methods) {
        super(uri, methods);
    }

    @Override
    public Response performAction(Request request) {
        Resource htmlResource = new HTMLResource("/ttt-menu.html");
        if (request.hasHeader(RequestHeader.COOKIE)) {
            return HTTPResponse.create(OK).withBody(htmlResource);
        }
        int sessionToken = new Random().nextInt();
        headers.put(COOKIE, "theme=game".getBytes());
        headers.put(COOKIE, ("sessionToken=" + sessionToken + "; Expires=Wed, 09 June 2021 10:18:14 GMT").getBytes());
        return HTTPResponse.create(OK).withHeaders(headers).withBody(htmlResource);
    }
}
