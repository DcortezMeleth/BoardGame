package pl.agh.edu.boardgame.tokens;

import pl.agh.edu.boardgame.map.fields.Field;

import java.io.Serializable;

/**
 * Klasa bazowa tokenu.
 *
 * @author Bartosz
 */
public abstract class BaseToken implements Serializable, Token {

    /** Sciezka do folderu z teksturami zywych jednostek. */
    protected static final String TEXTURES_PATH = "assets/Textures/small_parts/";

    /** Pole na ktorym stoi token. */
    private Field field = null;

    /** Startowy x. */
    private final int x;

    /** Startowy y. */
    private final int y;

    public BaseToken(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Field getField() {
        return field;
    }

    @Override
    public void setField(final Field field) {
        this.field = field;
    }

    @Override
    public void resetPosition() {
        setPosition(x, y);
        setField(null);
    }

    @Override
    public boolean validThisTurn() {
        return true;
    }


    @Override
    public void resetUsage() {
    }
}
