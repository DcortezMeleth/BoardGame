package pl.agh.edu.boardgame.map.fields;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import pl.agh.edu.boardgame.abilities.AbilityType;
import pl.agh.edu.boardgame.core.BoardGameMain;
import pl.agh.edu.boardgame.core.Player;
import pl.agh.edu.boardgame.nations.Nation;

/**
 * @author Bartosz
 */
public class Lake extends BaseField {

    private static final FieldType NAME = FieldType.LAKE;

    public Lake(final boolean cave, final boolean sourceOfMagic, final boolean mine) {
        super(cave, sourceOfMagic, mine);
        setTexture(new Texture(Gdx.files.internal(REGIONS_HEX + "sea/morze" + number() + ".png")));
    }

    @Override
    public FieldType getType() {
        return NAME;
    }

    @Override
    public boolean isValidAttacker(final Player player, final Nation nation, final BoardGameMain game) {
        // morza i jeziora moga podbijac tylko rasy zeglujace
        if(player.getActiveAbility().getAbilityType() != AbilityType.SAILING) {
            return false;
        }
        return super.isValidAttacker(player, nation, game);
    }
}
