package httpserver.routing;

import httpserver.Request;
import httpserver.Response;
import httpserver.httpresponse.HTTPResponse;
import httpserver.resourcemanagement.HTMLResource;
import httpserver.sessions.Session;
import httpserver.sessions.SessionFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static httpserver.httprequests.RequestHeader.COOKIE;
import static httpserver.httprequests.RequestHeader.PARAMS;
import static httpserver.httpresponse.StatusCode.OK;
import static httpserver.views.GameView.createView;
import static ttt.webplay.web_game.isOver;
import static ttt.webplay.web_game.playMove;

public class TicTacToeGameRoute extends Route {

    private final String emptyBoard = "---------";
    private final SessionFactory sessionFactory;
    private List<Session> sessions = new ArrayList<>();

    public TicTacToeGameRoute(String uri, SessionFactory sessionFactory, Method... methods) {
        super(uri, methods);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Response performAction(Request request) {
        String boardState = emptyBoard;
        String gameType = "hvh";
        if (request.hasHeader(COOKIE)) {
            String sessionId = request.getValue(COOKIE).split("=")[1];
            Session currentSession = getSession(sessionId);
            HashMap<String, String> sessionData = getSessionInfo(currentSession, request);
            gameType = sessionData.get("gameType");
            boardState = getBoardState(sessionData, request);
            updateSessionData(boardState, currentSession);
        }
        String view = createView(boardState, gameType);
        HTMLResource htmlResource = new HTMLResource(view.getBytes());
        return HTTPResponse.create(OK).withBody(htmlResource);
    }

    private void updateSessionData(String boardState, Session currentSession) {
        if (isOver(boardState)) {
            currentSession.addData("boardState", emptyBoard);
        } else {
            currentSession.addData("boardState", boardState);
        }
    }

    private String getBoardState(HashMap<String, String> sessionData, Request request) {
        String currentBoardState = sessionData.get("boardState");
        String gameType = sessionData.get("gameType");
        currentBoardState = makeMove(request, currentBoardState, gameType);
        return currentBoardState;
    }

    private String makeMove(Request request, String currentBoardState, String gameType) {
        Integer move = getMove(request);
        return playMove(currentBoardState, move, gameType);
    }

    private Integer getMove(Request request) {
        if (request.getValue(PARAMS).contains("cell")) {
            return Integer.valueOf(request.getValue(PARAMS).split("=")[1]);
        }
        return -1;
    }

    private HashMap<String, String> getSessionInfo(Session currentSession, Request request) {
        HashMap<String, String> data = currentSession.getData();
        addBoardState(currentSession, data);
        addGameType(currentSession, request);
        return currentSession.getData();
    }

    private void addGameType(Session currentSession, Request request) {
        if (request.getValue(PARAMS).contains("game-type")) {
            String gameType = request.getValue(PARAMS).split("=")[1];
            currentSession.addData("gameType", gameType);
        }
    }

    private void addBoardState(Session currentSession, HashMap<String, String> data) {
        if (data.isEmpty()) {
            currentSession.addData("boardState", emptyBoard);
        }
    }

    private Session getSession(String sessionID) {
        for (Session session : sessions) {
            if (session.getId().equals(sessionID)) {
                return session;
            }
        }
        Session session = sessionFactory.createSession(sessionID);
        sessions.add(session);
        return session;
    }
}