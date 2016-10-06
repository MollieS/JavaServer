package httpserver;

import httpserver.httprequests.HTTPRequest;

public interface HTTPRouter {

    Response route(HTTPRequest httpRequest);
}
