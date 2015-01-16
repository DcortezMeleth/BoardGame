package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Bartosz
 */
public class Spiritualist extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.SPIRITUALIST;

    public static final String NAME = "Uduchowione";

    private static final String DESCRIPTION = "Zdobywasz 2 dodatkowe monety zwycięstwa na koniec każdej tury w której" +
            " twoja rasa pozostała aktywna.";

    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 5;

    public Spiritualist() {
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/uduchowione.png")));
        setInactiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/uduchowione_tyl.png")));
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
