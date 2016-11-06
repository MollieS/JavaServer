package httpserver.sessions;

import org.junit.Test;

import java.util.HashMap;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SessionTest {

    private Session session = new GameSession("1");

    @Test
    public void hasAnId() {
        assertEquals("1", session.getId());
    }

    @Test
    public void canSetTheBoardState() {
        session.addData("boardState", "---------");

        HashMap<String, String> data = session.getData();

        assertEquals("---------", data.get("boardState"));
    }

    @Test
    public void canSetTheGameType() {
        session.addData("gameType", "hvh");

        HashMap<String, String> data = session.getData();

        assertEquals("hvh", data.get("gameType"));
    }

    @Test
    public void knowsIfBoardStateHasBeenSet() {
        assertFalse(session.hasData("boardState"));

        session.addData("boardState", "---------");

        assertTrue(session.hasData("boardState"));
    }

    @Test
    public void knowsIfGameStateHasBeenSet() {
        assertFalse(session.hasData("gameType"));

        session.addData("gameType", "hvh");

        assertTrue(session.hasData("gameType"));
    }
}
