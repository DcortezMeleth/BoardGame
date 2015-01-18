package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Bartosz
 */
public class Spiritualist extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.SPIRITUALIST;

    public static final String KEY = "spiritualist";

    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 5;

    public Spiritualist() {
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/uduchowione.png")));
        setInactiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/uduchowione_tyl.png")));
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
