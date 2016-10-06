package httpserver;

import httpserver.httprequests.HTTPRequest;

public interface ClientSocket {

    void close();

    void sendResponse(Response response);

    HTTPRequest getRequest();
}
