package httpserver.httprequests;

import httpserver.Request;
import httpserver.routing.Method;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static httpserver.httprequests.RequestHeader.*;

public class RequestFake implements Request {

    private final HashMap<RequestHeader, String> headers;
    private final String uri;
    private final Method method;

    public RequestFake(Method method, String uri) {
        this.method = method;
        this.uri = uri;
        this.headers = new HashMap<>();
    }

    @Override
    public boolean hasHeader(RequestHeader header) {
        return headers.containsKey(header);
    }

    @Override
    public String getValue(RequestHeader header) {
        for (Map.Entry<RequestHeader, String> entry : headers.entrySet()) {
            if (entry.getKey() == header) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public URI getRequestURI() {
        return URI.create(uri);
    }

    public void setData(String data) {
        headers.put(DATA, data);
    }

    public void setParams(String params) {
        headers.put(PARAMS, params);
    }

    public void setCookie(String cookie) {
        headers.put(COOKIE, cookie);
    }

    public void setAuthorization(String authorization) {
        headers.put(AUTHORIZATION, authorization);
    }

    public void setRangeStart(String rangeStart) {
        headers.put(RANGE, "range");
        headers.put(RANGE_START, rangeStart);
    }

    public void setRangeEnd(String rangeEnd) {
        headers.put(RANGE, "range");
        headers.put(RANGE_END, rangeEnd);
    }
}
