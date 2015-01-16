package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Bartosz
 */
public class Heroic extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.HEROIC;

    public static final String NAME = "Bohaterskie";

    private static final String DESCRIPTION = "Na koniec swojej tury umieść obu Bohaterów w dwóch różnych " +
            "kontrolowanuch przez rasę regionach. Do czasu gdy bohaterowie się w nich znajdują regiony te są " +
            "nietykalne.";

    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 5;

    public Heroic() {
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/bohaterskie.png")));
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
