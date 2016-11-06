package httpserver.views;

import static ttt.webplay.web_game.isOver;

public class GameView {

    public static String createView(String boardState, String gameType) {
        String view = HTMLHeader(gameType);
        view += HTMLbody(boardState);
        view += HTMLFooter();
        return view;
    }

    private static String HTMLFooter() {
        return "</html>";
    }

    private static String HTMLbody(String boardState) {
        String body = "<body>" +
                "<form method=\"get\" action=\"/ttt-game\" class=\"board\">\n";
        body += createBoard(boardState, body);
        body += "</form>";
        if (isOver(boardState)) {
            body += "<div>Game Over!</div>" +
                    "<form method=\"get\" action=\"/ttt-menu\">" +
                    "<button name=\"replay\" value=\"true\">Replay?</button>" +
                    "</form>" +
                    "</div>";
        }
        body += "</body>";
        return body;
    }

    private static String createBoard(String boardState, String view) {
        for (int i = 0; i < 9; i++) {
            view += createButton(i, boardState);
        }
        return view;
    }


    private static String css() {
//        return "<style media=\"screen\" type=\"text/css\"> " +
//                ".button { " +
//                "background-color: yellow;" +
//                "width: 33%; }" +
//                ".board {" +
//                "width: 70%;" +
//                "margin: 0 auto; }" +
//                "</style>";
        return "";
    }

    private static String HTMLHeader(String gameType) {
        String header = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n";
        header += css();
        header += addRefresh(gameType);
        header += "<title>Tic Tac Toe</title>\n" +
                "</head>\n";
        return header;
    }

    private static String addRefresh(String gameType) {
        if (gameType.equals("cvc")) return "<meta http-equiv=\"refresh\" content=\"1\">\n";
        return "";
    }

    private static String createButton(int i, String boardState) {
        String button = "<button class=\"button\" name=\"cell\" value=\"" + i + "\">" + getBoardMark(boardState, i) + "</button>\n";
        if ((i + 1) % 3 == 0) {
            button += "</br>";
        }
        return button;
    }

    private static String getBoardMark(String boardState, int cell) {
        if (boardState.charAt(cell) == '-') return " ";
        return String.valueOf(boardState.charAt(cell));
    }
}
