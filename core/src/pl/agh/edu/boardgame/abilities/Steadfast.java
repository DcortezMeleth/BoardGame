package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Bartosz
 */
public class Steadfast extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.STEADFAST;

    public static final String KEY = "steadfast";

    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 4;

    public Steadfast() {
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/niezlomne.png")));
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
