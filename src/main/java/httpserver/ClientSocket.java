package httpserver;

import httpserver.httpmessages.HTTPRequest;
import httpserver.httpmessages.HTTPResponse;

public interface ClientSocket {

    void close();

    void sendResponse(HTTPResponse httpResponse);

    HTTPRequest getRequest();
}
