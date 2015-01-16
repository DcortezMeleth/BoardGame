package pl.agh.edu.boardgame.map.fields;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Bartosz
 */
public class Hills extends BaseField {

    private static final FieldType NAME = FieldType.HILLS;

    public Hills(final boolean cave, final boolean sourceOfMagic, final boolean mine) {
        super(cave, sourceOfMagic, mine);
        setTexture(new Texture(Gdx.files.internal(REGIONS_HEX + "hills/wzgorza" + number() + ".png")));
    }

    @Override
    public FieldType getType() {
        return NAME;
    }
}
