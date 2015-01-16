package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Bartosz
 */
public class Fortified extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.FORTIFIED;

    public static final String NAME = "Warowne";

    private static final String DESCRIPTION = "Raz na turę umieść 1 Twierdzę w kontrolowanym przez siebie regionie. " +
            "Na koniec tury Twierdza przynosi 1 dodatkową monetę zwycięstwa. Twierdza dodatkowo zwiększa obronność " +
            "regionu o 1. Odkłóż Twierdzę gdy opuścisz region lub zostanie on podboity. Maksymalnie może być 1 " +
            "Twierdza na regionie, a 6 na mapie.";

    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 3;

    public Fortified() {
        setInactiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/warowne_tyl.png")));
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/warowne.png")));
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
