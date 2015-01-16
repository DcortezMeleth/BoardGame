package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Bartosz
 */
public class Flying extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.FLYING;

    public static final String NAME = "Latające";

    private static final String DESCRIPTION = "Możesz podbijać jakiekolwiek regiony mapy za wyjątkiem Mórz i Jezior. " +
            "Regiony nie muszą w żaden sposób sąsiadować z regionami już podbitymi.";

    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 5;

    public Flying() {
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/latajace.png")));
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
