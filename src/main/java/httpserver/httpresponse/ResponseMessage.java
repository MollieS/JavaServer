package httpserver.httpresponse;

import httpserver.Resource;

import static httpserver.httpresponse.StatusCode.TEAPOT;
import static httpserver.resourcemanagement.ResourceContentType.TEXT;

public class ResponseMessage extends HTTPResponse {

    private final int statusCode;
    private final String reasonPhrase;
    private final String originTime;
    private byte[] body;
    private String contentType;
    private String location;

    private ResponseMessage(int code, String reason, HTTPResponseDate httpResponseDate) {
        super(code, reason, httpResponseDate);
        this.statusCode = code;
        this.reasonPhrase = reason;
        this.originTime = httpResponseDate.getDate();
    }

    public static ResponseMessage create(StatusCode statusCode) {
        ResponseMessage responseMessage = new ResponseMessage(statusCode.code, statusCode.reason, new HTTPResponseDate());
        if (statusCode == TEAPOT) {
            responseMessage.setBody(TEAPOT.reason);
            responseMessage.setContentType(TEXT.contentType);
        }
        return responseMessage;
    }

    public byte[] getBody() {
        return body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public String getContentType() {
        return contentType;
    }

    public String getOriginTime() {
        return originTime;
    }

    private void setBody(String body) {
        this.body = body.getBytes();
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public boolean hasBody() {
        return body != null;
    }

    public ResponseMessage withBody(Resource resource) {
        this.body = resource.getContents();
        this.contentType = resource.getContentType();
        if (this.statusCode == 206) {
            setContentRange(body.length);
        }
        return this;
    }

    public ResponseMessage withLocation(String location) {
        super.setLocation(location);
        return this;
    }
}
