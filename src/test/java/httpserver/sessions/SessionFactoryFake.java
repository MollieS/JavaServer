package httpserver.sessions;

public class SessionFactoryFake implements SessionFactory {

    private final Session session;

    public SessionFactoryFake(Session session) {
        this.session = session;
    }

    @Override
    public Session createSession(String id) {
        return session;
    }
}
