package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import pl.agh.edu.boardgame.map.fields.BaseField;
import pl.agh.edu.boardgame.map.fields.Field;

/**
 * @author Bartosz
 */
public class Horseman extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.HORSEMAN;

    public static final String KEY = "horseman";
    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 5;

    public Horseman() {
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/konne.png")));
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
        if(field.getType() == BaseField.FieldType.HILLS || field.getType() == BaseField.FieldType.PLAINS) {
            return minArmySize - 1;
        }

        return minArmySize;
    }

    @Override
    public int getMaxUnits() {
        return maxUnits;
    }
}
