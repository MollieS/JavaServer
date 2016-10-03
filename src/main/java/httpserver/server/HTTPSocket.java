package httpserver.server;

import httpserver.ClientSocket;
import httpserver.SocketConnectionException;
import httpserver.httpmessages.HTTPRequest;
import httpserver.httpmessages.HTTPRequestParser;
import httpserver.httpmessages.HTTPResponse;
import httpserver.httpmessages.HTTPResponseWriter;

import java.io.*;
import java.net.Socket;

public class HTTPSocket implements ClientSocket {

    private final Socket socket;
    private final HTTPResponseWriter responseParser;
    private final HTTPRequestParser requestBuilder;

    public HTTPSocket(Socket socket, HTTPResponseWriter responseParser, HTTPRequestParser httpRequestParser) {
        this.socket = socket;
        this.responseParser = responseParser;
        this.requestBuilder = httpRequestParser;
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
    public HTTPRequest getRequest() {
        try {
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String request = bufferedReader.readLine();
            return requestBuilder.parse(request);
        } catch (IOException e) {
            throw new SocketConnectionException("Cannot get input stream: ", e.getCause());
        }
    }
}
