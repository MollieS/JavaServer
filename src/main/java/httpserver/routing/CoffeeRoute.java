package httpserver.routing;

import httpserver.httprequests.HTTPRequest;
import httpserver.httpresponse.HTTPResponse;
import httpserver.httpresponse.HTTPResponseDate;

import static httpserver.httpresponse.StatusCode.TEAPOT;
import static httpserver.resourcemanagement.ResourceContentType.TEXT;

public class CoffeeRoute extends Route {

    public CoffeeRoute(Method... methods) {
        super("/coffee", methods);
    }

    public HTTPResponse performAction(HTTPRequest httpRequest) {
        if (super.methodIsAllowed(httpRequest.getMethod())) {
            HTTPResponse httpResponse = new HTTPResponse(TEAPOT.code, TEAPOT.reason, new HTTPResponseDate());
            httpResponse.setContentType(TEXT.contentType);
            httpResponse.setBody(TEAPOT.reason.getBytes());
            return httpResponse;
        }
        return super.methodNotAllowed();
    }
}
