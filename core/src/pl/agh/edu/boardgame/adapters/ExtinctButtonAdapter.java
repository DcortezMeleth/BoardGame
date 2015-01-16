package pl.agh.edu.boardgame.adapters;

import com.badlogic.gdx.InputAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.agh.edu.boardgame.abilities.AbilityType;
import pl.agh.edu.boardgame.buttons.ExtinctButton;
import pl.agh.edu.boardgame.configuration.Configuration;
import pl.agh.edu.boardgame.core.AbilityNationPair;
import pl.agh.edu.boardgame.core.BoardGameMain;
import pl.agh.edu.boardgame.core.Player;

/**
 * Klasa odpowiadajaca za wykrywanie i obsluge klikniecia przycisku wymarcia rasy.
 *
 * @author Bartosz
 */
public class ExtinctButtonAdapter extends InputAdapter {

    /** Logger. */
    private final static Logger LOGGER = LogManager.getLogger(ExtinctButtonAdapter.class);

    /** Button. */
    private final ExtinctButton extinctButton;

    /** Konfiguracja gry. */
    private final Configuration configuration;

    /** Glowna klasa gry. */
    private final BoardGameMain game;

    public ExtinctButtonAdapter(BoardGameMain game, final Configuration configuration, final ExtinctButton extinctButton) {
        this.game = game;
        this.configuration = configuration;
        this.extinctButton = extinctButton;
    }

    @Override
    public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
        //jesli juz sie poruszono nie mozemy wymrzec w tej samej turze
        if(!game.getActivePlayer().isDeactivationPossible()) {
            return false;
        }

        //w fazie przegrupowania moga wymrzec tylko niezlomne
        if(game.getPhase() == BoardGameMain.TurnPhase.REGROUP &&
                game.getActivePlayer().getActiveAbility().getAbilityType() != AbilityType.STEADFAST) {
            return false;
        }

        int y = configuration.getIntProperty(Configuration.APP_HEIGHT) - screenY;

        if(extinctButton.getPolygon().contains(screenX, y) && game.getActivePlayer().getActiveNation() != null) {
            LOGGER.debug("Player " + (game.getPlayerNumber() + 1) + " decided to retire current nation!");
            Player player = game.getActivePlayer();
            AbilityNationPair removedPair = player.deactivateNation(game);
            if(removedPair != null) {
                game.getNationChoice().returnNation(removedPair);
            }
            game.nextTurn();
            return true;
        }

        return false;
    }
}
