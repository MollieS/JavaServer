package httpserver.sessions;

public class SessionFactorySpy implements SessionFactory {
    public HTTPSession createdSession;
    public int timesCalled = 0;

    @Override
    public Session createSession(String id) {
        timesCalled++;
        createdSession = new HTTPSession(id);
        return createdSession;
    }
}
