package httpserver.sessions;

public class HTTPSessionFactory implements SessionFactory{

    public HTTPSession createSession(String id) {
        return new HTTPSession(id);
    }
}
