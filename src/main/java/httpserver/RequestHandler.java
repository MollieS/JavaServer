package httpserver;

import httpserver.httprequests.HTTPRequest;
import httpserver.httpresponse.HTTPResponse;

public interface RequestHandler {

    HTTPResponse handle(HTTPRequest httpRequest);
}
