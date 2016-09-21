package httpserver;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) {
        HTTPServer httpServer = null;
            try {
                SocketServer socketServer = new HTTPSocketServer(new ServerSocket(5000));
                httpServer = new HTTPServer(5000, socketServer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        while(true) {
            httpServer.start();
        }
    }
}
