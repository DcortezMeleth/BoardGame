package pl.agh.edu.boardgame.adapters;

import com.badlogic.gdx.InputAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.agh.edu.boardgame.buttons.NextTurnButton;
import pl.agh.edu.boardgame.configuration.Configuration;
import pl.agh.edu.boardgame.core.BoardGameMain;

/**
 * Klasa odpowiadajaca za wykrywanie i obsluge klikniecia przycisku nowej tury.
 *
 * @author Bartosz
 */
public class NextTurnButtonAdapter extends InputAdapter {

    /** Logger. */
    private final static Logger LOGGER = LogManager.getLogger(NextTurnButtonAdapter.class);

    /** Button. */
    private final NextTurnButton nextTurnButton;

    /** Konfiguracja gry. */
    private final Configuration configuration;

    /** Glowna klasa gry. */
    private final BoardGameMain game;

    public NextTurnButtonAdapter(BoardGameMain game, final Configuration configuration, final NextTurnButton nextTurnButton) {
        this.game = game;
        this.configuration = configuration;
        this.nextTurnButton = nextTurnButton;
    }

    @Override
    public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
        int y = configuration.getIntProperty(Configuration.APP_HEIGHT) - screenY;

        if(nextTurnButton.getPolygon().contains(screenX, y)) {
            if(game.getPhase() == BoardGameMain.TurnPhase.REGROUP && !game.getActivePlayer().getTokensWithoutField().isEmpty()) {
                game.setMessageToShow("err_regroup");
                LOGGER.debug("Musisz rozmiescic wszystkie tokeny!");
                return false;
            } else if(game.getPhase() == BoardGameMain.TurnPhase.ATTACK) {

                //jesli gracz nie podbil zadnego pole nie bedzie mial gdzie rozstawic jednostek!
                if(!game.getActivePlayer().getTokensWithoutField().isEmpty()
                        && !game.getActivePlayer().activeRaceFieldConquered()) {
                    game.setMessageToShow("err_no_field_owned");
                    return false;
                }
            }

            game.nextTurn();
            return true;
        }

        return false;
    }
}
