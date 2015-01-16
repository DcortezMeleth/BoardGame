package pl.agh.edu.boardgame.buttons;

import com.badlogic.gdx.math.Polygon;

import java.io.Serializable;

/**
 * Klasa bazowa przyciskow.
 *
 * @author Bartosz
 */
public class BaseButton implements Serializable {

    /** Sciezka do folderu z teksturami przyciskow. */
    protected final static String BASE_PATH = "assets/Textures/buttons/";

    /** Wierzcholki wielokata opisujacego token. */
    private final static float[] VERTICES = new float[]{
            0f, 0f,
            80f, 0f,
            80f, 80f,
            0f, 80f
    };

    /** Ksztalt tokenu. */
    private final Polygon polygon;

    public BaseButton(final int x, final int y) {
        polygon = new Polygon(VERTICES);
        polygon.setPosition(x, y);
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public float getX() {
        return polygon.getX();
    }

    public float getY() {
        return polygon.getY();
    }
}
