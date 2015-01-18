package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import pl.agh.edu.boardgame.map.fields.Field;

/**
 * @author Bartosz
 */
public class Brave extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.BRAVE;

    public static final String KEY = "brave";

    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 4;


    public Brave() {
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/waleczne.png")));
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
    public int countAttackPerks(final int minArmySize, final Field field) {
        return minArmySize - 1;
    }

    @Override
    public int getMaxUnits() {
        return maxUnits;
    }
}
