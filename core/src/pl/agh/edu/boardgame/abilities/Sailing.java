package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Bartosz
 */
public class Sailing extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.SAILING;

    public static final String NAME = "Żeglujące";

    private static final String DESCRIPTION = "Dopóki twoja Żeglująca rasa jest aktywna możesz podbijać Morza i " +
            "Jeziora traktując je jak puste regiony. Gdy rasa jest wymierająca nadal kontrolujesz re regiony i " +
            "zdobywasz za nie monety.";

    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 5;

    public Sailing() {
        setInactiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/zeglujace_tyl.png")));
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/zeglujace.png")));
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
