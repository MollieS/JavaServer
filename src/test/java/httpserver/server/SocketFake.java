package httpserver.server;

import httpserver.ClientSocket;
import httpserver.Response;
import httpserver.httprequests.HTTPRequest;

import static httpserver.routing.Method.GET;

public class SocketFake implements ClientSocket {

    @Override
    public void close() {

    }

    @Override
    public void sendResponse(Response response) {

    }

    @Override
    public HTTPRequest getRequest() {
        return new HTTPRequest(GET, "/");
    }
}
