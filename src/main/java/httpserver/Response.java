package httpserver;

import httpserver.httpresponse.ResponseHeader;

import java.util.HashMap;

public interface Response {

    int getStatusCode();

    String getReasonPhrase();

    boolean hasBody();

    byte[] getBody();

    boolean hasHeader(ResponseHeader header);

    byte[] getValue(ResponseHeader header);

    Response withHeaders(HashMap<ResponseHeader, byte[]> headers);

    Response withBody(Resource resource);
}
