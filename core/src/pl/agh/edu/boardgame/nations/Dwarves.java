package pl.agh.edu.boardgame.nations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import pl.agh.edu.boardgame.core.Player;
import pl.agh.edu.boardgame.map.fields.Field;

import static pl.agh.edu.boardgame.nations.NationType.DWARVES;

/**
 * @author Michal
 */
public class Dwarves extends BaseNation {

    private static final String KEY = "dwarves";

    /** Tekstura zywych jednostek. */
    private static Texture liveArmiesTexture;

    /** Tekstura martwych jednostek. */
    private static Texture deadArmiesTexture;

    /** Tekstura zywych jednostek. */
    private static Texture liveBannerTexture;

    /** Tekstura martwych jednostek. */
    private static Texture deadBannerTexture;

    /** Ilosc tokenow */
    private final Integer units = 3;

    /** Typ rasy */
    private final NationType nationType = DWARVES;

    static {
        liveArmiesTexture = new Texture(Gdx.files.internal(LIVE_ARMIES_TEXTURES_PATH + "krasnoludy.png"));
        deadArmiesTexture = new Texture(Gdx.files.internal(DEAD_ARMIES_TEXTURES_PATH + "krasnoludy.png"));
        liveBannerTexture = new Texture(Gdx.files.internal(LIVE_BANNERS_TEXTURES_PATH + "krasnoludy.png"));
        deadBannerTexture = new Texture(Gdx.files.internal(DEAD_BANNERS_TEXTURES_PATH + "krasnoludy.png"));
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
            if(field.isMine() && field.getNationType() == nationType) {
                income++;
            }
        }

        return income;
    }
}
