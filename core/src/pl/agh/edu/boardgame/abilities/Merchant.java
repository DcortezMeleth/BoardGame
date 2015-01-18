package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import pl.agh.edu.boardgame.core.Player;

/**
 * @author Bartosz
 */
public class Merchant extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.MERCHANT;

    public static final String KEY = "merchant";

    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 2;

    public Merchant() {
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/handlujace.png")));
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
    public int countIncome(int income, final Player player) {
        return income + player.getOwnedLands().size();
    }
}
