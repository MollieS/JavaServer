package httpserver.sessions;

public interface SessionFactory {

    Session createSession(String id);
}
