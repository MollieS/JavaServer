package httpserver;

public interface ClientSocket {

    void close();

    void sendResponse(HTTPResponse httpResponse);
}
