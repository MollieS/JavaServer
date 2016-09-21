package httpserver.server;

import httpserver.ClientSocket;
import httpserver.httpmessages.HTTPResponse;

public class SocketFake implements ClientSocket {

    @Override
    public void close() {

    }

    @Override
    public void sendResponse(HTTPResponse httpResponse) {

    }

    @Override
    public String getRequest() {
        return "GET / HTTP/1.1";
    }
}
