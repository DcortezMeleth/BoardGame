package pl.agh.edu.boardgame.adapters;

import com.badlogic.gdx.InputAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.agh.edu.boardgame.buttons.AttackButton;
import pl.agh.edu.boardgame.configuration.Configuration;
import pl.agh.edu.boardgame.core.BoardGameMain;
import pl.agh.edu.boardgame.core.Player;
import pl.agh.edu.boardgame.map.fields.Field;

/**
 * Adapter odpowiedzialny za przycisk ataku.
 *
 * @author Bartosz
 */
public class AttackButtonAdapter extends InputAdapter {

    /** Logger. */
    private final static Logger LOGGER = LogManager.getLogger(AttackButtonAdapter.class);

    /** Button. */
    private final AttackButton attackButton;

    /** Konfiguracja gry. */
    private final Configuration configuration;

    /** Glowna klasa gry. */
    private final BoardGameMain game;

    public AttackButtonAdapter(BoardGameMain game, final Configuration configuration, final AttackButton attackButton) {
        this.game = game;
        this.configuration = configuration;
        this.attackButton = attackButton;
    }

    @Override
    public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
        int y = configuration.getIntProperty(Configuration.APP_HEIGHT) - screenY;

        if(attackButton.getPolygon().contains(screenX, y) && game.getActivePlayer().getActiveNation() != null) {
            LOGGER.debug("Gracz " + (game.getPlayerNumber() + 1) + " atakuje!");
            Player player = game.getActivePlayer();
            for(Field field : game.getMap().getFields()) {
                if(!field.getAttackingArmy().isEmpty()) {
                    field.attack(player, game);
                }
            }
            return true;
        }

        return false;
    }
}
