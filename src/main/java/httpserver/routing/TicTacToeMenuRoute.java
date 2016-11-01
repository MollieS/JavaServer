package httpserver.routing;

import httpserver.Request;
import httpserver.Resource;
import httpserver.Response;
import httpserver.httpresponse.HTTPResponse;
import httpserver.resourcemanagement.HTMLResource;

import static httpserver.httpresponse.StatusCode.OK;

public class TicTacToeMenuRoute extends Route {

    public TicTacToeMenuRoute(String uri, Method... methods) {
        super(uri, methods);
    }

    @Override
    public Response performAction(Request request) {
        Resource htmlResource = new HTMLResource("/ttt-menu.html");
        return HTTPResponse.create(OK).withBody(htmlResource);
    }
}
