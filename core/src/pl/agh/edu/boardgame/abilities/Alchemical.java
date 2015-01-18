package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import pl.agh.edu.boardgame.core.Player;

/**
 * @author Bartosz
 */
public class Alchemical extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.ALCHEMICAL;

    public static final String KEY = "alchemical";

    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 4;

    public Alchemical() {
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/alchemicy.png")));
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

    @Override
    public int countIncome(final int income, final Player player) {
        return income + 2;
    }
}
