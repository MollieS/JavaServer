package httpserver.server;

import httpserver.ClientSocket;
import httpserver.SocketConnectionException;
import httpserver.httpmessages.HTTPResponse;
import httpserver.httpmessages.HTTPResponseParser;

import java.io.*;
import java.net.Socket;

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
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new SocketConnectionException("Cannot get input stream: ", e.getCause());
        }
    }
}
