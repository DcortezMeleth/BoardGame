package pl.agh.edu.boardgame.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Przycisk kostki.
 *
 * @author Bartosz
 */
public class DiceButton extends BaseButton {

    /** Tekstura przycisku aktywnego. */
    private final Texture activeTexture;

    /** Tekstura przycisku nieaktywnego. */
    private final Texture inactiveTexture;

    /** Czy button powinien byc widoczny. */
    private boolean active = true;

    public DiceButton(final int x, final int y) {
        super(x, y);
        this.activeTexture = new Texture(Gdx.files.internal(BASE_PATH + "dice.png"));
        this.inactiveTexture = new Texture(Gdx.files.internal(BASE_PATH + "dice_inactive.png"));
    }

    public Texture getTexture() {
        return active ? activeTexture : inactiveTexture;
    }

    public boolean isActive() {
        return active;
    }

    public void negate() {
        active = !active;
    }

    public void reset() {
        active = true;
    }

    public void dispose() {
        activeTexture.dispose();
        inactiveTexture.dispose();
    }
}
