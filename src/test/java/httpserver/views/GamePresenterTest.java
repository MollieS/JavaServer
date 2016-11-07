package httpserver.views;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GamePresenterTest {

    @Test
    public void knowsIfTheGameIsOver() {
        GamePresenter gamePresenter = new GamePresenter("hvh", "XXX-OO---");

        assertTrue(gamePresenter.gameIsOver());
    }

    @Test
    public void returnsTheCorrectFormatForAnEmptyCell() {
        GamePresenter gamePresenter = new GamePresenter("hvh", "---------");

        assertEquals(" ", gamePresenter.getMark(0));
    }

    @Test
    public void returnsTheCorrectFormatForAMarkedCell() {
        GamePresenter gamePresenter = new GamePresenter("hvh", "X--------");

        assertEquals("X", gamePresenter.getMark(0));
    }

    @Test
    public void getsTheCorrectResultFromADrawnBoard() {
        GamePresenter gamePresenter = new GamePresenter("hvh", "XOXOXOOXX");

        assertEquals("It's a draw!", gamePresenter.getResult());
    }

    @Test
    public void getsTheCorrectResultFromAWonBoard() {
        GamePresenter gamePresenter = new GamePresenter("hvh", "XXX-OO---");

        assertEquals("X wins!", gamePresenter.getResult());
    }

    @Test
    public void returnsAnEmptyStringIfGameIsNotOver() {
        GamePresenter gamePresenter = new GamePresenter("hvh", "XX_-OO---");

        assertEquals("", gamePresenter.getResult());
    }
}
