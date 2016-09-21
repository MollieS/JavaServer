package httpserver;

import httpserver.httpmessages.HTTPResponse;

public interface ClientSocket {

    void close();

    void sendResponse(HTTPResponse httpResponse);

    String getRequest();
}
