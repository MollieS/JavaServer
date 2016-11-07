package httpserver.routing;

import httpserver.Response;
import httpserver.httprequests.RequestFake;
import httpserver.sessions.GameSession;
import httpserver.sessions.Session;
import httpserver.sessions.SessionFactoryFake;
import httpserver.sessions.SessionMock;
import org.junit.Test;

import java.util.HashMap;

import static httpserver.httpresponses.HTTPResponseTest.getString;
import static httpserver.routing.Method.GET;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TicTacToeGameRouteTest {

    private Session session = new GameSession("1");
    private TicTacToeGameRoute ticTacToeGameRoute = new TicTacToeGameRoute("/ttt-game", new SessionFactoryFake(session), GET);
    private RequestFake request = new RequestFake(GET, "/ttt-game");

    @Test
    public void sendsA200Response() {
        Response response = ticTacToeGameRoute.performAction(request);

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void sendsAResponseWithBody() {
        Response response = ticTacToeGameRoute.performAction(request);

        assertTrue(response.hasBody());
    }

    @Test
    public void ifANewSessionHasBeenStartedItGivesAnEmptyBoardAnCorrectGameType() {
        sendRequest("game-type=hvh");

        HashMap sessionData = session.getData();

        assertEquals("---------", sessionData.get("boardState"));
        assertEquals("hvh", sessionData.get("gameType"));
    }

    @Test
    public void ifASessionExistsItRetrievesTheBoardState() {
        SessionMock sessionMock = new SessionMock();
        SessionFactoryFake sessionFactoryFake = new SessionFactoryFake(sessionMock);
        TicTacToeGameRoute ticTacToeGameRoute = new TicTacToeGameRoute("/ttt-game", sessionFactoryFake, GET);

        request.setCookie("cookie=1");
        request.setParams("game-type=hvh");
        ticTacToeGameRoute.performAction(request);
        RequestFake secondRequest = new RequestFake(GET, "/ttt-game");
        secondRequest.setCookie("cookie=1");
        secondRequest.setParams("cell=1");
        ticTacToeGameRoute.performAction(secondRequest);

        assertTrue(sessionMock.getDataHasBeenCalled());
    }

    @Test
    public void updatesTheViewIfAMoveHasBeenMade() {
        sendRequest("game-type=hvh");
        Response response = sendRequest("cell=0");

        String body = getString(response.getBody());

        assertTrue(body.contains("value=\"0\">x</button>"));
    }

    @Test
    public void canDealWithManyMoves() {
        sendRequest("game-type=hvh");
        sendRequest("cell=0");
        Response response = sendRequest("cell=1");

        String body = getString(response.getBody());

        assertTrue(body.contains("value=\"0\">x</button>"));
        assertTrue(body.contains("value=\"1\">o</button>"));
    }

    @Test
    public void canChangeGameTypeInASession() {
        sendRequest("game-type=hvh");
        sendRequest("game-type=cvh");

        assertEquals("cvh", session.getData().get("gameType"));
    }

    @Test
    public void whenTheGameEndsTheBoardStateIsCleared() {
        sendRequest("game-type=hvh");
        sendRequest("cell=0");
        sendRequest("cell=4");
        sendRequest("cell=1");
        sendRequest("cell=5");
        sendRequest("cell=2");

        assertEquals("---------", session.getData().get("boardState"));
    }

    @Test
    public void playsAComputerMoveIfHumanvComputerGame() {
        sendRequest("game-type=hvc");

        Response response = sendRequest("cell=0");
        String body = getString(response.getBody());

        assertEquals("X---O----", session.getData().get("boardState"));
        assertTrue(body.contains("value=\"0\">x</button>"));
        assertTrue(body.contains("value=\"4\">o</button>"));
    }

    @Test
    public void playsAComputerMoveFirstIfComputerVHumanGame() {
        Response response = sendRequest("game-type=cvh");

        String body = getString(response.getBody());

        assertEquals("X--------", session.getData().get("boardState"));
        assertTrue(body.contains("value=\"0\">x</button>"));
    }

    @Test
    public void playsOneMoveIfComputervComputerGame() {
        Response response = sendRequest("game-type=cvc");

        String body = getString(response.getBody());

        assertEquals("X--------", session.getData().get("boardState"));
        assertTrue(body.contains("value=\"0\">x</button>"));
    }

    @Test
    public void addsRefreshIfComputerVComputerGame() {
        Response response = sendRequest("game-type=cvc");

        String body = getString(response.getBody());

        assertTrue(body.contains("<meta http-equiv=\"refresh\" content=\"1\">"));
    }

    @Test
    public void doesNotPlayAComputerMoveIfHumanIsFirst() {
        Response response = sendRequest("game-type=hvc");

        String body = getString(response.getBody());

        assertEquals("---------", session.getData().get("boardState"));
        assertFalse(body.contains("X"));
    }

    @Test
    public void doesNotPlayAComputerMoveIfHumanMakesInvalidMove() {
        sendRequest("game-type=hvc");
        sendRequest("cell=0");
        sendRequest("cell=0");

        assertEquals("X---O----", session.getData().get("boardState"));
    }


    private Response sendRequest(String param) {
        RequestFake request = new RequestFake(GET, "/ttt-game");
        request.setCookie("cookie=1");
        request.setParams(param);
        return ticTacToeGameRoute.performAction(request);
    }

}
