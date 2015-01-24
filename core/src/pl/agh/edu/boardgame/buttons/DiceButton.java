package pl.agh.edu.boardgame.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import pl.agh.edu.boardgame.map.GameMap;
import pl.agh.edu.boardgame.map.fields.Field;

import java.util.ArrayList;
import java.util.List;

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

    /** Lista pol atakowanych przez oszalala rase. */
    private List<Field> attackedFields = new ArrayList<>();

    public DiceButton(final int x, final int y) {
        super(x, y);
        this.activeTexture = new Texture(Gdx.files.internal(BASE_PATH + "dice.png"));
        this.inactiveTexture = new Texture(Gdx.files.internal(BASE_PATH + "dice_inactive.png"));
    }

    public Texture getTexture(final GameMap map) {
        return isActive(map) ? activeTexture : inactiveTexture;
    }

    public boolean isActive(final GameMap map) {
        return active && map.getAttackedField() != null && !attackedFields.contains(map.getAttackedField());
    }

    public void negate() {
        active = !active;
    }

    public void reset() {
        active = true;
        attackedFields = new ArrayList<>();
    }

    /** Dodaj pole do ktorego podbiva probowano uzyc kosci. */
    public void addAttackedField(final Field field) {
        attackedFields.add(field);
    }

    public void dispose() {
        activeTexture.dispose();
        inactiveTexture.dispose();
    }
}
