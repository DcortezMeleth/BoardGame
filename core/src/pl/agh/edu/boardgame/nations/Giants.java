package pl.agh.edu.boardgame.nations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import pl.agh.edu.boardgame.map.fields.BaseField;
import pl.agh.edu.boardgame.map.fields.Field;

import static pl.agh.edu.boardgame.nations.NationType.GIANTS;

/**
 * @author Michal
 */
public class Giants extends BaseNation {

    private static final String KEY = "giants";

    /** Ilosc tokenow */
    private final Integer units = 6;

    /** Typ rasy */
    private final NationType nationType = GIANTS;

    /** Tekstura zywych jednostek. */
    private static Texture liveArmiesTexture;

    /** Tekstura martwych jednostek. */
    private static Texture deadArmiesTexture;

    /** Tekstura zywych jednostek. */
    private static Texture liveBannerTexture;

    /** Tekstura martwych jednostek. */
    private static Texture deadBannerTexture;

    static {
        liveArmiesTexture = new Texture(Gdx.files.internal(LIVE_ARMIES_TEXTURES_PATH + "olbrzymy.png"));
        deadArmiesTexture = new Texture(Gdx.files.internal(DEAD_ARMIES_TEXTURES_PATH + "olbrzymy.png"));
        liveBannerTexture = new Texture(Gdx.files.internal(LIVE_BANNERS_TEXTURES_PATH + "olbrzymy.png"));
        deadBannerTexture = new Texture(Gdx.files.internal(DEAD_BANNERS_TEXTURES_PATH + "olbrzymy.png"));
    }

    @Override
    protected String getKey() {
        return KEY;
    }

    @Override
    public Texture getBannerTexture() {
        return isActive() ? liveBannerTexture : deadBannerTexture;
    }

    @Override
    public Texture getArmyTexture() {
        return isActive() ? liveArmiesTexture : deadArmiesTexture;
    }

    @Override
    public Integer getMaxUnits() {
        return units;
    }

    @Override
    public NationType getNationType() {
        return nationType;
    }

    @Override
    public int countAttackPerks(final int minArmySize, final Field field) {
        for(Field neighbour : field.getNeighbours()) {
            if(neighbour.getType() == BaseField.FieldType.MOUNTAIN && neighbour.getNationType() == GIANTS) {
                return minArmySize - 1;
            }
        }
        return minArmySize;
    }

    @Override
    public void dispose() {
        liveArmiesTexture.dispose();
        deadArmiesTexture.dispose();
        liveBannerTexture.dispose();
        deadBannerTexture.dispose();
    }
}
