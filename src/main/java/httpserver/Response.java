package httpserver;

import httpserver.routing.Method;

import java.util.List;

public interface Response {

    String getOriginTime();

    List<Method> getAllowedMethods();

    boolean hasLocation();

    boolean hasContentRange();

    boolean hasBody();

    int getContentRange();

    String getLocation();

    byte[] getBody();

    int getStatusCode();

    String getReasonPhrase();

    String getContentType();
}
