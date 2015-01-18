package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Bartosz
 */
public class Fortified extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.FORTIFIED;

    public static final String KEY = "fortified";

    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 3;

    public Fortified() {
        setInactiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/warowne_tyl.png")));
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/warowne.png")));
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
