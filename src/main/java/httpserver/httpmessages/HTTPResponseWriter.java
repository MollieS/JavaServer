package httpserver.httpmessages;

import httpserver.routing.Method;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HTTPResponseWriter {

    private static final String PROTOCOL_VERSION = "HTTP/1.1";
    private static final String CONTENT_TYPE = "Content-Type : ";
    private static final String SPACE = " ";
    private final ByteArrayOutputStream byteArrayOutputStream;

    public HTTPResponseWriter(ByteArrayOutputStream byteArrayOutputStream) {
        this.byteArrayOutputStream = byteArrayOutputStream;
    }

    public byte[] parse(HTTPResponse httpResponse) {
        try {
            writeResponseToByteStream(httpResponse);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new ResponseWriterException("Error writing response: ", e);
        }
    }

    private void writeResponseToByteStream(HTTPResponse httpResponse) throws IOException {
        addHeader(httpResponse);
        if (httpResponse.allowedMethods() != null) {
            addAllowedMethods(httpResponse);
        }
        if (httpResponse.hasBody()) {
            addBody(httpResponse);
        }
    }

    private void addAllowedMethods(HTTPResponse httpResponse) throws IOException {
        byteArrayOutputStream.write(("Allow : ").getBytes());
        for (Method method : httpResponse.allowedMethods()) {
            byteArrayOutputStream.write((method + ",").getBytes());
        }
        byteArrayOutputStream.write("\n".getBytes());
    }

    private void addBody(HTTPResponse httpResponse) throws IOException {
        byteArrayOutputStream.write(getContentType(httpResponse));
        byteArrayOutputStream.write(httpResponse.getBody());
    }

    private void addHeader(HTTPResponse httpResponse) throws IOException {
        byteArrayOutputStream.write(getHeader(httpResponse));
    }

    private byte[] getHeader(HTTPResponse httpResponse) {
        return (PROTOCOL_VERSION + SPACE + httpResponse.getStatusCode() + SPACE + httpResponse.getReasonPhrase() + "\n").getBytes();
    }

    public byte[] getContentType(HTTPResponse httpResponse) {
        return (CONTENT_TYPE + httpResponse.getContentType() + "\n\n").getBytes();
    }
}
