package httpserver;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) {
        try {
            SocketServer socketServer = new HTTPSocketServer(new ServerSocket(9000));
            HTTPServer httpServer = new HTTPServer(9000, socketServer);
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
