package httpserver;

import httpserver.httpmessages.HTTPRequest;
import httpserver.httpmessages.HTTPResponse;
import httpserver.routing.Method;
import httpserver.routing.Router;

import java.net.URI;

public interface ResponseBuilder {

    HTTPResponse buildResponse(Method method, URI path);

    void addAllowedMethods(HTTPResponse httpResponse, HTTPRequest httpRequest, Router router);
}
