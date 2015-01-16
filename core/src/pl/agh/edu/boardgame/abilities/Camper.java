package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Bartosz
 */
public class Camper extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.CAMPER;

    public static final String NAME = "Obozujące";

    private static final String DESCRIPTION = "Podczas fazy przegrupowania rozmieść 5 Obozowisk w dowolny sposób na " +
            "regionach kontrolowanych przez swoją rasę. Obozowisko działa jak dodatkowy żeton rasy i zwiększa " +
            "obronność regionu o 1, a także broni przez mocą czarnoksiężników. W każdej turze możesz zmieniać ich " +
            "rozmieszczenie. Obozowiska znikają dopiero gdy rasa wymiera.";

    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 5;

    public Camper() {
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/obozujace.png")));
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
