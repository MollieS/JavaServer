package httpserver.httpresponse;

import httpserver.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HTTPResponseWriter {

    private static final String PROTOCOL_VERSION = "HTTP/1.1";
    private static final String SPACE = " ";
    private static final byte[] HEADER_SEPARATOR = " : ".getBytes();
    private static final byte[] HEADER_END = "\n".getBytes();
    private final ByteArrayOutputStream byteArrayOutputStream;

    public HTTPResponseWriter(ByteArrayOutputStream byteArrayOutputStream) {
        this.byteArrayOutputStream = byteArrayOutputStream;
    }

    public byte[] parse(Response httpResponse) {
        try {
            writeResponseToByteStream(httpResponse);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new ResponseWriterException("Error writing response: ", e);
        }
    }

    private void writeResponseToByteStream(Response httpResponse) throws IOException {
        addStatusLine(httpResponse);
        for (ResponseHeader header : ResponseHeader.values()) {
            if (httpResponse.hasHeader(header)) {
                byteArrayOutputStream.write((header.headerName().getBytes()));
                byteArrayOutputStream.write(HEADER_SEPARATOR);
                byteArrayOutputStream.write(httpResponse.getValue(header));
                byteArrayOutputStream.write(HEADER_END);
            }
        }
        if (httpResponse.hasBody()) {
            addBody(httpResponse);
        }
    }

    private void addBody(Response httpResponse) throws IOException {
        byteArrayOutputStream.write(httpResponse.getBody());
    }

    private void addStatusLine(Response httpResponse) throws IOException {
        byteArrayOutputStream.write(getHeader(httpResponse));
    }

    private byte[] getHeader(Response httpResponse) {
        return (PROTOCOL_VERSION + SPACE + httpResponse.getStatusCode() + SPACE + httpResponse.getReasonPhrase() + "\n").getBytes();
    }
}
