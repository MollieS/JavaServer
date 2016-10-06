package httpserver.server;

import httpserver.ClientSocket;
import httpserver.httpmessages.HTTPRequest;
import httpserver.httpmessages.HTTPResponse;

import static httpserver.routing.Method.GET;

public class SocketFake implements ClientSocket {

    @Override
    public void close() {

    }

    @Override
    public void sendResponse(HTTPResponse httpResponse) {

    }

    @Override
    public HTTPRequest getRequest() {
        return new HTTPRequest(GET, "/");
    }
}
