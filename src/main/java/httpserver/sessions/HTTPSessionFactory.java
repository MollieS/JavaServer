package httpserver.sessions;

public class HTTPSessionFactory implements SessionFactory{

    public GameSession createSession(String id) {
        return new GameSession(id);
    }
}
