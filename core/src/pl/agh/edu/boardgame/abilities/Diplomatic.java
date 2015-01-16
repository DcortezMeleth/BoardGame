package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Klasa reprezentujaca umjejetnosc dyplomatyczne.
 *
 * @author Bartosz
 */
public class Diplomatic extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.DIPLOMATIC;

    private static final String DESCRIPTION = "Na koniec swojej tury możesz wybrać jednego z graczy, którego aktywnej" +
            " rasy nie atakowałeś w tej turze i zawrzeć z nim pokój. Ten gracz nie może Cie atakować aż do końca " +
            "twojej następnej tury.";

    public static final String NAME = "Dyplomatyczne";

    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 5;

    public Diplomatic() {
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/dyplomatyczne.png")));
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
