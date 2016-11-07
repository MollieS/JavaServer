package httpserver.views;

import static ttt.core.isDraw;
import static ttt.core.isOver;
import static ttt.core.winningSymbol;

public class GamePresenter {

    private final String board;
    private final String gameType;
    private boolean over;

    public GamePresenter(String gameType, String board) {
        this.board = board;
        this.gameType = gameType;
        this.over = isOver(board);
    }

    public boolean gameIsOver() {
        return over;
    }

    public String gameType() {
        return gameType;
    }

    public String getMark(int i) {
        String[] boardArray = board.split("");
        if (boardArray[i].equals("-")) {
           return "-";
        }
        return boardArray[i].toLowerCase();
    }

    public String getResult() {
        if (over && !isDraw(board)) {
            return (winningSymbol(board) + " wins!");
        } else if (isDraw(board)) {
            return "It's a draw!";
        }
        return "";
    }
}
