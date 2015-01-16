package pl.agh.edu.boardgame.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import pl.agh.edu.boardgame.abilities.AbilityType;
import pl.agh.edu.boardgame.core.BoardGameMain;
import pl.agh.edu.boardgame.core.Player;

/**
 * Przycisk sluzacy do wymierania.
 *
 * @author Bartosz
 */
public class ExtinctButton extends BaseButton {

    /** Tekstura przycisku aktywnego. */
    private final Texture activeTexture;

    /** Tekstura przycisku nieaktywnego. */
    private final Texture inactiveTexture;

    public ExtinctButton(final int x, final int y) {
        super(x, y);
        this.activeTexture = new Texture(Gdx.files.internal(BASE_PATH + "extinct.png"));
        this.inactiveTexture = new Texture(Gdx.files.internal(BASE_PATH + "extinct_inactive.png"));
    }

    public Texture getTexture(final Player player, final BoardGameMain game) {
        if(!player.isDeactivationPossible()) {
            return inactiveTexture;
        }
        if(game.getPhase() == BoardGameMain.TurnPhase.REGROUP &&
                game.getActivePlayer().getActiveAbility().getAbilityType() != AbilityType.STEADFAST) {
            return inactiveTexture;
        }
        return activeTexture;
    }

    public void dispose() {
        activeTexture.dispose();
        inactiveTexture.dispose();
    }
}
