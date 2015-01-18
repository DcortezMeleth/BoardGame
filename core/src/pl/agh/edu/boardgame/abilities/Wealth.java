package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Bartosz
 */
public class Wealth extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.WEALTH;

    public static final String KEY = "wealth";

    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 4;


    public Wealth() {
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/zamozne.png")));
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
