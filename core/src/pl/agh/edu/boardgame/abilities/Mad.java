package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Bartosz
 */
public class Mad extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.MAD;

    public static final String NAME = "Oszalałe";

    private static final String DESCRIPTION = "Możesz użyć kości posiłków przed każdym swoim podbojem. Jak zawsze do " +
            "podboju wymagany jest co najmniej jeden żeton.";

    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 4;

    public Mad() {
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/oszalale.png")));
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
