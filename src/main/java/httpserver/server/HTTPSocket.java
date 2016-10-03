package httpserver.server;

import httpserver.ClientSocket;
import httpserver.SocketConnectionException;
import httpserver.httpmessages.HTTPResponse;
import httpserver.httpmessages.HTTPResponseParser;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class HTTPSocket implements ClientSocket {

    private final Socket socket;
    private final HTTPResponseParser responseParser;

    public HTTPSocket(Socket socket, HTTPResponseParser responseParser) {
        this.socket = socket;
        this.responseParser = responseParser;
    }

    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new SocketConnectionException("Cannot close socket: ", e.getCause());
        }
    }

    @Override
    public void sendResponse(HTTPResponse httpResponse) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(responseParser.parse(httpResponse));
        } catch (IOException e) {
            throw new SocketConnectionException("Cannot get output stream: ", e.getCause());
        }
    }

    @Override
    public String getRequest() {
        try {
            InputStream inputStream = socket.getInputStream();
            byte[] buffer = new byte[600];
            inputStream.read(buffer);
            String req = new String(buffer, Charset.forName("UTF-8"));
            return req;
        } catch (IOException e) {
            throw new SocketConnectionException("Cannot get input stream: ", e.getCause());
        }
    }
}
