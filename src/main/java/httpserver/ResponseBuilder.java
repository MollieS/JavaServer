package httpserver;

import httpserver.httpmessages.HTTPResponse;
import httpserver.routing.Method;

import java.net.URI;

public interface ResponseBuilder {

    HTTPResponse buildResponse(Method method, URI path);

}
