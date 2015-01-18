package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Bartosz
 */
public class DragonLords extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.DRAGON_LORDS;

    public static final String KEY = "dragon_lords";

    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 5;

    public DragonLords() {
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/wladcy_smokow.png")));
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
