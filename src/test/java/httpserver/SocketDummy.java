package httpserver;

public class SocketDummy implements ClientSocket {

    @Override
    public void close() {

    }

    @Override
    public void sendResponse(HTTPResponse httpResponse) {

    }

    @Override
    public String getRequest() {
        return null;
    }
}
