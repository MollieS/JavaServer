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

    public HTTPResponse(int statusCode, String reasonPhrase, HashMap<ResponseHeader, byte[]> headers) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.headers = headers;
    }

    public HTTPResponse(int statusCode, String reasonPhrase, HashMap newHeaders, byte[] contents) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.headers = newHeaders;
        this.body = contents;
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
        headers = addHeaders(headers);
        return new HTTPResponse(this.statusCode, this.reasonPhrase, headers);
    }

    private HashMap<ResponseHeader, byte[]> addHeaders(HashMap<ResponseHeader, byte[]> headers) {
        HashMap newHeaders = new HashMap();
        copyHeaders(newHeaders, this.headers);
        copyHeaders(newHeaders, headers);
        return newHeaders;
    }

    private void copyHeaders(HashMap newHeaders, HashMap<ResponseHeader, byte[]> headers) {
        for (Map.Entry entry : headers.entrySet()) {
            newHeaders.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Response withBody(Resource resource) {
        HashMap newHeaders = new HashMap();
        newHeaders.put(CONTENT_TYPE, resource.getContentType().getBytes());
        if (this.statusCode == 206) {
            String contentRange = String.valueOf(resource.getContents().length);
            newHeaders.put(CONTENT_RANGE, contentRange.getBytes());
        }
        return new HTTPResponse(this.statusCode, this.reasonPhrase, newHeaders, resource.getContents());
    }
}
