package httpserver.httpresponses;

import httpserver.Response;
import httpserver.httpresponse.StatusCode;
import httpserver.routing.Method;

import java.util.List;

class HTTPResponseFake implements Response {

    private final StatusCode statusCode;
    private List<Method> allowedMethods;
    private String location;
    private int contentRange;
    private byte[] body;

    public HTTPResponseFake(StatusCode code) {
        this.statusCode = code;
    }

    @Override
    public String getOriginTime() {
        return "Wed, 5 Oct";
    }

    @Override
    public List<Method> getAllowedMethods() {
        return allowedMethods;
    }

    @Override
    public boolean hasLocation() {
        return (location != null);
    }

    @Override
    public boolean hasContentRange() {
        return (contentRange != 0);
    }

    @Override
    public boolean hasBody() {
        return (body != null);
    }

    @Override
    public int getContentRange() {
        return contentRange;
    }

    @Override
    public String getLocation() {
        return location;
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
    public String getContentType() {
        return null;
    }

    public void addAllowedMethod(List<Method> methods) {
        this.allowedMethods = methods;
    }

    public void addLocation(String location) {
        this.location = location;
    }

    public void addBody(String body) {
        this.body = body.getBytes();
    }

    public void setContentRange(int contentRange) {
        this.contentRange = contentRange;
    }
}
