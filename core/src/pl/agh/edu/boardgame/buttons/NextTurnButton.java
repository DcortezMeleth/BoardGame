package pl.agh.edu.boardgame.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import pl.agh.edu.boardgame.core.BoardGameMain;

/**
 * Przycisk kolejnej truy.
 *
 * @author Bartosz
 */
public class NextTurnButton extends BaseButton {

    /** Tekstura przycisku aktywnego. */
    private final Texture nextPlayerTexture;

    /** Tekstura przycisku nieaktywnego. */
    private final Texture regroupTexture;

    public NextTurnButton(final int x, final int y) {
        super(x, y);
        this.nextPlayerTexture = new Texture(Gdx.files.internal(BASE_PATH + "next_player.png"));
        this.regroupTexture = new Texture(Gdx.files.internal(BASE_PATH + "regroup.png"));
    }

    public Texture getTexture(BoardGameMain.TurnPhase phase) {
        return phase == BoardGameMain.TurnPhase.REGROUP ? nextPlayerTexture : regroupTexture;
    }

    public void dispose() {
        nextPlayerTexture.dispose();
        regroupTexture.dispose();
    }
}
