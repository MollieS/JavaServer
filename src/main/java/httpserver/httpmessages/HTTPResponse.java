package httpserver.httpmessages;

import httpserver.routing.Method;

import java.util.List;

public class HTTPResponse {

    private final String reasonPhrase;
    private final int statusCode;
    private byte[] body;
    private String contentType;
    private List<Method> allowedMethods;
    private String location;

    public HTTPResponse(int statusCode, String reasonPhrase) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }

    public boolean hasBody() {
        return (body != null);
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setAllowedMethods(List<Method> allowedMethods) {
        this.allowedMethods = allowedMethods;
    }

    public List<Method> allowedMethods() {
        return allowedMethods;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public boolean hasLocation() {
        return (location != null);
    }
}
