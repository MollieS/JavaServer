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

    }
}
