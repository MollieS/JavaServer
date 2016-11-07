package httpserver.views;

import org.junit.Test;

import static httpserver.views.GameView.createView;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameViewTest {

    @Test
    public void addsARefreshForAComputerVComputerGame() {
        GamePresenter gamePresenter = new GamePresenter("cvc", "---------");

        String view = createView(gamePresenter);

        assertTrue(view.contains("<meta http-equiv=\"refresh\""));
    }

    @Test
    public void doesNotAddARefreshIfGameIsOver() {
        GamePresenter gamePresenter = new GamePresenter("cvc", "XXX-OO---");

        String view = createView(gamePresenter);

        assertFalse(view.contains("<meta http-equiv=\"refresh\""));
    }
}
