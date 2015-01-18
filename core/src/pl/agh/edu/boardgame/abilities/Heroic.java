package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Bartosz
 */
public class Heroic extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.HEROIC;

    public static final String KEY = "heroic";

    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 5;

    public Heroic() {
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/bohaterskie.png")));
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
