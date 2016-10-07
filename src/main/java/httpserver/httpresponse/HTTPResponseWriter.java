package httpserver.httpresponse;

import httpserver.Response;
import httpserver.routing.Method;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HTTPResponseWriter {

    private static final String PROTOCOL_VERSION = "HTTP/1.1";
    private static final String CONTENT_TYPE = "Content-Type : ";
    private static final String CONTENT_RANGE = "Content-Range : ";
    private static final String ALLOW_HEADER = "Allow : ";
    private static final String LOCATION_HEADER = "Location : ";
    private static final String DATE_HEADER = "Date : ";
    private static final String SPACE = " ";
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
        addDate(httpResponse);
        if (httpResponse.getAllowedMethods() != null) {
            addAllowedMethods(httpResponse);
        }
        if (httpResponse.hasLocation()) {
            addLocation(httpResponse);
        }
        if (httpResponse.hasContentRange()) {
            addContentRange(httpResponse);
        }
        if (httpResponse.hasBody()) {
            addBody(httpResponse);
        }
    }

    private void addDate(Response httpResponse) throws IOException {
        byteArrayOutputStream.write((DATE_HEADER + httpResponse.getOriginTime()).getBytes());
        byteArrayOutputStream.write("\n".getBytes());
    }

    private void addContentRange(Response httpResponse) throws IOException {
        byteArrayOutputStream.write((CONTENT_RANGE + httpResponse.getContentRange()).getBytes());
        byteArrayOutputStream.write("\n".getBytes());
    }

    private void addLocation(Response httpResponse) throws IOException {
        byteArrayOutputStream.write((LOCATION_HEADER + httpResponse.getLocation()).getBytes());
        byteArrayOutputStream.write("\n".getBytes());
    }

    private void addAllowedMethods(Response httpResponse) throws IOException {
        byteArrayOutputStream.write(ALLOW_HEADER.getBytes());
        for (Method method : httpResponse.getAllowedMethods()) {
            byteArrayOutputStream.write((method + ",").getBytes());
        }
        byteArrayOutputStream.write("\n".getBytes());
    }

    private void addBody(Response httpResponse) throws IOException {
        byteArrayOutputStream.write(getContentType(httpResponse));
        byteArrayOutputStream.write(httpResponse.getBody());
    }

    private void addStatusLine(Response httpResponse) throws IOException {
        byteArrayOutputStream.write(getHeader(httpResponse));
    }

    private byte[] getHeader(Response httpResponse) {
        return (PROTOCOL_VERSION + SPACE + httpResponse.getStatusCode() + SPACE + httpResponse.getReasonPhrase() + "\n").getBytes();
    }

    public byte[] getContentType(Response httpResponse) {
        return (CONTENT_TYPE + httpResponse.getContentType() + "\n\n").getBytes();
    }
}
