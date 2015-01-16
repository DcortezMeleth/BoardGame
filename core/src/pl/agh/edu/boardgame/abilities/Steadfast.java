package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Bartosz
 */
public class Steadfast extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.STEADFAST;

    public static final String NAME = "Niezłomne";

    private static final String DESCRIPTION = "Możesz przeprowadzić akcję wymierania rasy na koniec swojej normalnej " +
            "tury, po fazie zdobywania monet zwycięstwa, zamiast poświęcać na to osobną turę.";

    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 4;

    public Steadfast() {
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/niezlomne.png")));
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
