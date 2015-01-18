package pl.agh.edu.boardgame.nations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import static pl.agh.edu.boardgame.nations.NationType.ELVES;

/**
 * @author Michal
 */
public class Elves extends BaseNation {

    private static final String KEY = "elves";

    /** Ilosc tokenow */
    private final Integer units = 6;

    /** Typ rasy */
    private final NationType nationType = ELVES;

    /** Tekstura zywych jednostek. */
    private static Texture liveArmiesTexture;

    /** Tekstura martwych jednostek. */
    private static Texture deadArmiesTexture;

    /** Tekstura zywych jednostek. */
    private static Texture liveBannerTexture;

    /** Tekstura martwych jednostek. */
    private static Texture deadBannerTexture;

    static {
        liveArmiesTexture = new Texture(Gdx.files.internal(LIVE_ARMIES_TEXTURES_PATH + "elfy.png"));
        deadArmiesTexture = new Texture(Gdx.files.internal(DEAD_ARMIES_TEXTURES_PATH + "elfy.png"));
        liveBannerTexture = new Texture(Gdx.files.internal(LIVE_BANNERS_TEXTURES_PATH + "elfy.png"));
        deadBannerTexture = new Texture(Gdx.files.internal(DEAD_BANNERS_TEXTURES_PATH + "elfy.png"));
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
}

