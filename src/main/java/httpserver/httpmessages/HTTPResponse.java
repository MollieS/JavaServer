package httpserver.httpmessages;

public class HTTPResponse {

    private final String reasonPhrase;
    private final String statusCode;
    private byte[] body;

    public HTTPResponse(int statusCode, String reasonPhrase) {
        this.statusCode = String.valueOf(statusCode);
        this.reasonPhrase = reasonPhrase;
    }

    public String getStatusCode() {
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
}
