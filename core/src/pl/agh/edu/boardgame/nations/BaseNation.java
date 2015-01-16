package pl.agh.edu.boardgame.nations;

import com.badlogic.gdx.math.Polygon;
import pl.agh.edu.boardgame.core.Player;
import pl.agh.edu.boardgame.map.fields.Field;

import java.io.Serializable;

/**
 * @author Bartosz
 */
public abstract class BaseNation implements Nation, Serializable {

    /** Sciezka do folderu z teksturami zywych jednostek. */
    public static final String LIVE_ARMIES_TEXTURES_PATH = "assets/Textures/small_parts/tokens/live/";

    /** Sciezka do folderu z teksturami martwych jednostek. */
    public static final String DEAD_ARMIES_TEXTURES_PATH = "assets/Textures/small_parts/tokens/dead/";

    /** Sciezka do folderu z teksturami zywych jednostek. */
    public static final String LIVE_BANNERS_TEXTURES_PATH = "assets/Textures/race_banners/live/";

    /** Sciezka do folderu z teksturami martwych jednostek. */
    public static final String DEAD_BANNERS_TEXTURES_PATH = "assets/Textures/race_banners/dead/";

    /** Ksztalt tokenu. */
    private final Polygon armyPolygon;

    /** Ksztalt tokenu. */
    private final Polygon bannerPolygon;

    /** Okresla czy jest to aktywna rasa gracza, czy rasa wymarla. */
    private boolean active = true;

    /** Pole na ktorym stoi jednostka. */
    private Field field;

    /** Wierzcholki wielokata opisujacego token. */
    private final static float[] ARMY_VERTICES = new float[]{
            0f, 0f,
            40f, 0f,
            40f, 40f,
            0f, 40f
    };

    /** Wierzcholki wielokata opisujacego sztandar. */
    private final static float[] BANNER_VERTICES = new float[]{
            0f, 0f,
            222f, 0f,
            222f, 120f,
            0f, 120f
    };

    public BaseNation() {
        this.armyPolygon = new Polygon(ARMY_VERTICES);
        this.bannerPolygon = new Polygon(BANNER_VERTICES);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    @Override
    public Polygon getArmyPolygon() {
        return armyPolygon;
    }

    @Override
    public Polygon getBannerPolygon() {
        return bannerPolygon;
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
    public int countAttackPerks(final int minArmySize, final Field field) {
        return minArmySize;
    }

    @Override
    public int countIncome(final int income, final Player player) {
        return income;
    }
}
