package httpserver.sessions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SessionFactoryTest {

    @Test
    public void returnsASessionWithCorrectId() {
        Session session = new HTTPSessionFactory().createSession("1");

        assertEquals("1", session.getId());
    }
}
