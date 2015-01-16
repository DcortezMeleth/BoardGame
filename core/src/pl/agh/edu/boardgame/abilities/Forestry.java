package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import pl.agh.edu.boardgame.core.Player;
import pl.agh.edu.boardgame.map.fields.BaseField;
import pl.agh.edu.boardgame.map.fields.Field;
import pl.agh.edu.boardgame.nations.NationType;

/**
 * @author Bartosz
 */
public class Forestry extends BaseAbility {

    private static final AbilityType ABILITY_TYPE = AbilityType.FORESTRY;

    public static final String NAME = "Leśne";

    private static final String DESCRIPTION = "Na koniec każdej tury każdy leśny region zajmowany przez twoją rasę " +
            "przynosi ci dodatkowo 1 monetę zwycięstwa.";

    /** Maksymalna liczba jednostek z umiejetnosci. */
    private final static int maxUnits = 4;

    public Forestry() {
        setActiveTexture(new Texture(Gdx.files.internal("assets/Textures/power_badges/lesne.png")));
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
        NationType nationType = player.getActiveNation().getNationType();
        for(Field field : player.getOwnedLands()) {
            if(field.getType() == BaseField.FieldType.FOREST && field.getNationType() == nationType) {
                income++;
            }
        }

        return income;
    }
}
