package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Klasa reprezentujaca umjejetnosc dyplomatyczne.
 *
 * @author Bartosz
 */
public class Diplomatic extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.DIPLOMATIC;

    public static final String KEY = "diplomatic";

    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 5;

    public Diplomatic() {
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/dyplomatyczne.png")));
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
