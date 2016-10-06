package httpserver.httpresponses;

import httpserver.Resource;
import httpserver.Response;
import httpserver.httpresponse.ResponseHeader;
import httpserver.httpresponse.StatusCode;
import httpserver.routing.Method;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static httpserver.httpresponse.ResponseHeader.*;

class HTTPResponseFake implements Response {

    private final StatusCode statusCode;
    private final HashMap<ResponseHeader, byte[]> headers;
    private List<Method> allowedMethods;
    private byte[] body;
    private int contentRange;

    public HTTPResponseFake(StatusCode code) {
        this.statusCode = code;
        this.headers = new HashMap<ResponseHeader, byte[]>();
        headers.put(DATE, "Wed, 5 Oct".getBytes());
    }

    @Override
    public boolean hasBody() {
        return (body != null);
    }

    @Override
    public byte[] getBody() {
        return body;
    }

    @Override
    public int getStatusCode() {
        return statusCode.code;
    }

    @Override
    public String getReasonPhrase() {
        return statusCode.reason;
    }

    @Override
    public boolean hasHeader(ResponseHeader header) {
        return headers.containsKey(header);
    }

    @Override
    public byte[] getValue(ResponseHeader header) {
        for (Map.Entry entry : headers.entrySet()) {
            if (entry.getKey() == header) {
                return (byte[]) entry.getValue();
            }
        }
        return new byte[0];
    }

    @Override
    public Response withHeaders(HashMap<ResponseHeader, byte[]> headers) {
        return null;
    }

    @Override
    public Response withBody(Resource resource) {
        return null;
    }

    public void addAllowedMethod(Method methods) {
       headers.put(ALLOW, methods.name().getBytes());
    }

    public void addBody(String body) {
        this.body = body.getBytes();
    }

    public void addLocation(String s) {
        headers.put(LOCATION, s.getBytes());
    }

    public void setContentRange(int contentRange) {
        headers.put(CONTENT_RANGE, String.valueOf(contentRange).getBytes());
    }

    public void addCookie(String s) {
        headers.put(COOKIE, s.getBytes());
    }
}
