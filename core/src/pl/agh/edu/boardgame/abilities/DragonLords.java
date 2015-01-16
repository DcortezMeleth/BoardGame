package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Bartosz
 */
public class DragonLords extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.DRAGON_LORDS;

    public static final String NAME = "Władcy smoków";

    private static final String DESCRIPTION = "Raz na turę możesz podbić sąsiedni region używając 1 żetonu rasy, bez " +
            "względu na liczbę broniących. Po podbiciu regionu umieść na nim smoka. Tak długo jak smok pozostaje w " +
            "regionie, jest on nietykalny.";

    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 5;

    public DragonLords() {
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/wladcy_smokow.png")));
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
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
