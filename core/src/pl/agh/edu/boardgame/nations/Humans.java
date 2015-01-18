package pl.agh.edu.boardgame.nations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import pl.agh.edu.boardgame.core.Player;
import pl.agh.edu.boardgame.map.fields.BaseField;
import pl.agh.edu.boardgame.map.fields.Field;

import static pl.agh.edu.boardgame.nations.NationType.HUMANS;

/**
 * @author Michal
 */
public class Humans extends BaseNation {

    private static final String KEY = "humans";

    /** Ilosc tokenow */
    private final Integer units = 5;

    /** Typ rasy */
    private final NationType nationType = HUMANS;

    /** Tekstura zywych jednostek. */
    private static Texture liveArmiesTexture;

    /** Tekstura martwych jednostek. */
    private static Texture deadArmiesTexture;

    /** Tekstura zywych jednostek. */
    private static Texture liveBannerTexture;

    /** Tekstura martwych jednostek. */
    private static Texture deadBannerTexture;

    static {
        liveArmiesTexture = new Texture(Gdx.files.internal(LIVE_ARMIES_TEXTURES_PATH + "ludzie.png"));
        deadArmiesTexture = new Texture(Gdx.files.internal(DEAD_ARMIES_TEXTURES_PATH + "ludzie.png"));
        liveBannerTexture = new Texture(Gdx.files.internal(LIVE_BANNERS_TEXTURES_PATH + "ludzie.png"));
        deadBannerTexture = new Texture(Gdx.files.internal(DEAD_BANNERS_TEXTURES_PATH + "ludzie.png"));
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
    public void dispose() {
        liveArmiesTexture.dispose();
        deadArmiesTexture.dispose();
        liveBannerTexture.dispose();
        deadBannerTexture.dispose();
    }

    @Override
    public int countIncome(int income, final Player player) {
        for(Field field : player.getOwnedLands()) {
            if(field.getType() == BaseField.FieldType.PLAINS && field.getNationType() == nationType) {
                income++;
            }
        }

        return income;
    }
}
