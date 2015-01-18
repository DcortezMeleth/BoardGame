package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Bartosz
 */
public class Sailing extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.SAILING;

    public static final String KEY = "sailing";

    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 5;

    public Sailing() {
        setInactiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/zeglujace_tyl.png")));
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/zeglujace.png")));
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public AbilityType getAbilityType() {
        return ABILITY_TYPE;
    }

    @Override
    public int getMaxUnits() {
        return maxUnits;
    }
}
