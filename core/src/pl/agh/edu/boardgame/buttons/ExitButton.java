package pl.agh.edu.boardgame.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Przycisk wyjscia z gry.
 *
 * @author Bartosz
 */
public class ExitButton extends BaseButton {

    /** Tekstura przycisku aktywnego. */
    private final Texture activeTexture;

    public ExitButton(final int x, final int y) {
        super(x, y);
        this.activeTexture = new Texture(Gdx.files.internal(BASE_PATH + "exit.png"));
    }

    public Texture getTexture() {
        return activeTexture;
    }

    public void dispose() {
        activeTexture.dispose();
    }
}
