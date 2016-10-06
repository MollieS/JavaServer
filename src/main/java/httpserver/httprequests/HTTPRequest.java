package httpserver.httprequests;

import httpserver.routing.Method;

import java.net.URI;

public class HTTPRequest {

    private final Method method;
    private final URI requestURI;
    private String data;
    private String params;
    private int rangeStart;
    private int rangeEnd;
    private boolean hasRangeEnd;
    private boolean hasRangeStart;
    private String cookie;
    private String authorization;

    public HTTPRequest(Method method, String requestURI) {
        this.method = method;
        this.requestURI = URI.create(requestURI);
        this.hasRangeStart = false;
        this.hasRangeEnd = false;
    }

    public Method getMethod() {
        return method;
    }

    public URI getRequestURI() {
        return requestURI;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getParams() {
        return params;
    }

    public boolean hasParams() {
        return params != null;
    }

    public void setRangeStart(int rangeStart) {
        this.rangeStart = rangeStart;
        this.hasRangeStart = true;
    }

    public void setRangeEnd(int rangeEnd) {
        this.rangeEnd = rangeEnd + 1;
        this.hasRangeEnd = true;
    }

    public boolean hasRange() {
        return hasRangeStart || hasRangeEnd;
    }

    public int getRangeStart() {
        return rangeStart;
    }

    public int getRangeEnd() {
        return rangeEnd;
    }

    public boolean hasRangeEnd() {
        return hasRangeEnd;
    }

    public boolean hasRangeStart() {
        return hasRangeStart;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public boolean hasCookie() {
        return (cookie != null);
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public boolean hasAuthorization() {
        return authorization != null;
    }
}
