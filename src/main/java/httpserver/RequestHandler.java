package httpserver;

import httpserver.httpmessages.HTTPRequest;
import httpserver.httpmessages.HTTPResponse;

public interface RequestHandler {

    HTTPResponse handle(HTTPRequest httpRequest);
}
