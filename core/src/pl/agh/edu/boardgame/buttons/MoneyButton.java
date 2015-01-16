package pl.agh.edu.boardgame.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Przycik przyslaniajacy pieniadze.
 *
 * @author Bartosz
 */
public class MoneyButton extends BaseButton {

    /** Tekstura przycisku aktywnego. */
    private final Texture activeTexture;

    /** Czy button powinien byc widoczny. */
    private boolean active = true;

    public MoneyButton(final int x, final int y) {
        super(x, y);
        this.activeTexture = new Texture(Gdx.files.internal(BASE_PATH + "cash.png"));
    }

    public Texture getTexture() {
        return activeTexture;
    }

    public boolean isActive() {
        return active;
    }

    public void negate() {
        active = !active;
    }

    public void dispose() {
        activeTexture.dispose();
    }
}
