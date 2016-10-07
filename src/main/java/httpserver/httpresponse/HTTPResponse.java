package httpserver.httpresponse;

import httpserver.Resource;
import httpserver.Response;

import java.util.HashMap;
import java.util.Map;

import static httpserver.httpresponse.ResponseHeader.*;

public class HTTPResponse implements Response {

    private final int statusCode;
    private final String reasonPhrase;
    private byte[] body;
    private HashMap<ResponseHeader, byte[]> headers = new HashMap<>();

    private HTTPResponse(int code, String reason, HTTPResponseDate httpResponseDate) {
        this.statusCode = code;
        this.reasonPhrase = reason;
        headers.put(DATE, httpResponseDate.getDate().getBytes());
    }

    public static HTTPResponse create(StatusCode statusCode) {
        return new HTTPResponse(statusCode.code, statusCode.reason, new HTTPResponseDate());
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public boolean hasBody() {
        return body != null;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getReasonPhrase() {
        return reasonPhrase;
    }


    @Override
    public boolean hasHeader(ResponseHeader header) {
        return headers.containsKey(header);
    }

    @Override
    public byte[] getValue(ResponseHeader header) {
        for (Map.Entry headersSet : headers.entrySet()) {
            if (headersSet.getKey() == header) {
                return (byte[]) headersSet.getValue();
            }
        }
        return new byte[0];
    }

    @Override
    public HTTPResponse withHeaders(HashMap<ResponseHeader, byte[]> headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public Response withBody(Resource resource) {
        this.body = resource.getContents();
        headers.put(CONTENT_TYPE, resource.getContentType().getBytes());
        if (this.statusCode == 206) {
            String contentRange = String.valueOf(resource.getContents().length);
            headers.put(CONTENT_RANGE, contentRange.getBytes());
        }
        return this;
    }
}
