package httpserver.httpmessages;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HTTPResponseParser {

    private static final String PROTOCOL_VERSION = "HTTP/1.1";
    private static final String CONTENT_TYPE = "Content-Type : ";
    private static final String SPACE = " ";
    private final ByteArrayOutputStream byteArrayOutputStream;

    public HTTPResponseParser(ByteArrayOutputStream byteArrayOutputStream) {
        this.byteArrayOutputStream = byteArrayOutputStream;
    }

    public byte[] parse(HTTPResponse httpResponse) {
        addHeader(httpResponse, byteArrayOutputStream);
        if (httpResponse.hasBody()) {
            addBody(httpResponse, byteArrayOutputStream);
        }
        return byteArrayOutputStream.toByteArray();
    }

    private void addBody(HTTPResponse httpResponse, ByteArrayOutputStream byteArrayOutputStream) {
        try {
            byteArrayOutputStream.write(getContentType(httpResponse));
            byteArrayOutputStream.write(httpResponse.getBody());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addHeader(HTTPResponse httpResponse, ByteArrayOutputStream byteArrayOutputStream) {
        try {
            byteArrayOutputStream.write(getHeader(httpResponse));
        } catch (IOException e) {
            throw new ByteWriterException("Cannot write header : ", e);
        }
    }

    private byte[] getHeader(HTTPResponse httpResponse) {
        return (PROTOCOL_VERSION + SPACE + httpResponse.getStatusCode() + SPACE + httpResponse.getReasonPhrase() + "\n").getBytes();
    }

    public byte[] getContentType(HTTPResponse httpResponse) {
        return (CONTENT_TYPE + httpResponse.getContentType() + "\n\n").getBytes();
    }
}
