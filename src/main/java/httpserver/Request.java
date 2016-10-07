package httpserver;

import httpserver.httprequests.RequestHeader;
import httpserver.routing.Method;

import java.net.URI;

public interface Request {

    boolean hasHeader(RequestHeader header);

    String getValue(RequestHeader header);

    Method getMethod();

    URI getRequestURI();
}
