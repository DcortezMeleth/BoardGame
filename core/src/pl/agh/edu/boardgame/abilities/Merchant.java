package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import pl.agh.edu.boardgame.core.Player;

/**
 * @author Bartosz
 */
public class Merchant extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.MERCHANT;

    public static final String NAME = "Handlujące";

    private static final String DESCRIPTION = "Na koniec każdej tury region zajmowany przez twoją rasę przynosi ci " +
            "dodatkowo 1 monetę zwycięstwa.";

    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 2;

    public Merchant() {
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/handlujace.png")));
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

    @Override
    public int countIncome(int income, final Player player) {
        return income + player.getOwnedLands().size();
    }
}
