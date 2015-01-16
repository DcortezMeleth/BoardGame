package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Bartosz
 */
public class Wealth extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.WEALTH;

    public static final String NAME = "Zamożne";

    private static final String DESCRIPTION = "Na koniec pierwszej tury obecności Zamożnej rasy na mapie otrzymujesz " +
            "jednorazowo dodatkowe 7 monet zwycięstwa.";

    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 4;


    public Wealth() {
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/zamozne.png")));
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
