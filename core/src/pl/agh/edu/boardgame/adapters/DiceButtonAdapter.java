package pl.agh.edu.boardgame.adapters;

import com.badlogic.gdx.InputAdapter;
import pl.agh.edu.boardgame.abilities.AbilityType;
import pl.agh.edu.boardgame.buttons.DiceButton;
import pl.agh.edu.boardgame.configuration.Configuration;
import pl.agh.edu.boardgame.core.BoardGameMain;

/**
 * @author Bartosz
 */
public class DiceButtonAdapter extends InputAdapter {

    /** Button. */
    private final DiceButton diceButton;

    /** Konfiguracja gry. */
    private final Configuration configuration;

    /** Glowna klasa gry. */
    private final BoardGameMain game;

    public DiceButtonAdapter(final BoardGameMain game, final Configuration configuration, final DiceButton diceButton) {
        this.configuration = configuration;
        this.diceButton = diceButton;
        this.game = game;
    }

    @Override
    public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
        int y = configuration.getIntProperty(Configuration.APP_HEIGHT) - screenY;

        if(diceButton.getPolygon().contains(screenX, y) && diceButton.isActive() &&
                game.getActivePlayer().getActiveNation() != null) {
            game.setDiceUsed(true);

            //waleczne zawsze moga uzyc
            if(game.getActivePlayer().getActiveAbility().getAbilityType() != AbilityType.MAD) {
                diceButton.negate();
            }
            return true;
        }

        return false;
    }
}
