package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import pl.agh.edu.boardgame.map.fields.Field;

/**
 * @author Bartosz
 */
public class Underground extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.UNDERGROUND;

    public static final String KEY = "underground";

    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 5;

    public Underground() {
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/podziemne.png")));
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
        if(field.isCave()) {
            return minArmySize - 1;
        }

        return minArmySize;
    }

    @Override
    public int getMaxUnits() {
        return maxUnits;
    }
}
