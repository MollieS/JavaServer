package httpserver;

import java.io.*;
import java.net.Socket;

public class HTTPSocket implements ClientSocket {

    private final Socket socket;

    public HTTPSocket(Socket socket) {
        this.socket = socket;
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
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.write(httpResponse.getStatusCode() + " " + httpResponse.getReasonPhrase());
            printWriter.close();
        } catch (IOException e) {
            throw new SocketConnectionException("Cannot get output stream: ", e.getCause());
        }
    }
}
