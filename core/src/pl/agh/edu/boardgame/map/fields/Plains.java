package pl.agh.edu.boardgame.map.fields;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Bartosz
 */
public class Plains extends BaseField {

    private static final FieldType NAME = FieldType.PLAINS;

    public Plains(final boolean cave, final boolean sourceOfMagic, final boolean mine) {
        super(cave, sourceOfMagic, mine);
        setTexture(new Texture(Gdx.files.internal(REGIONS_HEX + "farmlands/pola" + number() + ".png")));
    }

    @Override
    public FieldType getType() {
        return NAME;
    }

}
