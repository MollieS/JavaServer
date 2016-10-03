package httpserver;

import httpserver.httpmessages.HTTPResponse;

public interface ResponseBuilder {

    HTTPResponse buildResponse(String method, String path);
}
