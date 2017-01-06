package httpserver.sessions;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class SessionManagerTest {
    private SessionFactorySpy sessionFactorySpy = new SessionFactorySpy();
    private SessionManager sessionManager = new SessionManager(sessionFactorySpy);

    @Test
    public void knowsIfSessionDoesNotExists() {
        assertFalse(sessionManager.exists("1"));
    }

    @Test
    public void knowsIfSessionExists() {
        sessionManager.getOrCreateSession("1");

        assertTrue(sessionManager.exists("1"));
    }

    @Test
    public void createsANewSessionIfOneDoesNotExist() {
        Session session = sessionManager.getOrCreateSession("1");

        assertEquals(1, sessionFactorySpy.timesCalled);
        assertNotNull(session);
        assertEquals("1", sessionFactorySpy.createdSession.getId());
    }

    @Test
    public void findsCorrectSessionIfItExists() {
        sessionManager.getOrCreateSession("1");
        sessionManager.getOrCreateSession("1");

        assertEquals(1, sessionFactorySpy.timesCalled);
    }
}
