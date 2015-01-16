package pl.agh.edu.boardgame.map.fields;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Bartosz
 */
public class Mountains extends BaseField {

    private static final FieldType NAME = FieldType.MOUNTAIN;

    public Mountains(final boolean cave, final boolean sourceOfMagic, final boolean mine) {
        super(cave, sourceOfMagic, mine);
        setTexture(new Texture(Gdx.files.internal(REGIONS_HEX + "mountains/gory" + number() + ".png")));
    }

    @Override
    public FieldType getType() {
        return NAME;
    }
}
