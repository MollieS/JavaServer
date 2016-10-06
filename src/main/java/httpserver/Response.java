package httpserver;

import httpserver.httpresponse.ResponseHeader;
import httpserver.routing.Method;

import java.util.HashMap;
import java.util.List;

public interface Response {

    List<Method> getAllowedMethods();

    int getStatusCode();

    String getReasonPhrase();

    boolean hasBody();

    byte[] getBody();

    boolean hasHeader(ResponseHeader header);

    byte[] getValue(ResponseHeader header);

    Response withHeaders(HashMap<ResponseHeader, byte[]> headers);

    Response withBody(Resource resource);
}
