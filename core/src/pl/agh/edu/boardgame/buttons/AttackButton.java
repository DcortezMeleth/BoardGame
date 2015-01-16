package pl.agh.edu.boardgame.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import pl.agh.edu.boardgame.core.BoardGameMain;

/**
 * Przycisk sluzacy do ataku.
 *
 * @author Bartosz
 */
public class AttackButton extends BaseButton {

    /** Tekstura przycisku aktywnego. */
    private final Texture activeTexture;

    /** Tekstura przycisku nieaktywnego. */
    private final Texture inactiveTexture;

    public AttackButton(final int x, final int y) {
        super(x, y);
        this.activeTexture = new Texture(Gdx.files.internal(BASE_PATH + "attack.png"));
        this.inactiveTexture = new Texture(Gdx.files.internal(BASE_PATH + "attack_inactive.png"));
    }

    public Texture getTexture(BoardGameMain.TurnPhase phase) {
        return phase == BoardGameMain.TurnPhase.ATTACK ? activeTexture : inactiveTexture;
    }

    public void dispose() {
        activeTexture.dispose();
        inactiveTexture.dispose();
    }
}
