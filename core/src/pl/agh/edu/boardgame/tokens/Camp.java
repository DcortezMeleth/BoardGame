package pl.agh.edu.boardgame.tokens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import pl.agh.edu.boardgame.abilities.AbilityType;
import pl.agh.edu.boardgame.core.Player;
import pl.agh.edu.boardgame.map.fields.Field;

/**
 * Klasa obozu.
 *
 * @author Bartosz
 */
public class Camp extends BaseToken {

    /** Pozycja srodka ogregu na teksturze. Tutaj taka sama dla x i y. */
    public static final int POSITION = 30;

    /** Peomien okregu. */
    private static final float RADIUS = 25f;

    /** Tekstura przycisku. */
    private static Texture texture;

    /** Wierzcholki wielokata opisujacego przycisk. */
    private final Circle circle;

    static {
        texture = new Texture(Gdx.files.internal(TEXTURES_PATH + "oboz.png"));
    }

    public Camp() {
        super(1320, 710);
        this.circle = new Circle(POSITION, POSITION, RADIUS);
    }

    @Override
    public void setPosition(final float x, final float y) {
        circle.setPosition(x + POSITION, y + POSITION);
    }

    @Override
    public boolean validUser(final Player player) {
        return player.getActiveAbility() != null &&
                player.getActiveAbility().getAbilityType() == AbilityType.CAMPER;
    }

    @Override
    public void setField(final Field field) {
        if(getField() != null) {
            getField().setCamp(false);
        }
        super.setField(field);
        if(field != null) {
            field.setCamp(true);
        }
    }

    @Override
    public boolean contains(final int x, final int y) {
        return circle.contains(x, y);
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void resetPosition() {
        if(getField() != null) {
            getField().setCamp(false);
        }
        super.resetPosition();
    }

    @Override
    public boolean validTarget(Field field) {
        return field.getOwner() != null &&
                field.getOwner().getActiveAbility().getAbilityType() == AbilityType.CAMPER;
    }

    @Override
    public float getX() {
        return circle.x - POSITION;
    }

    @Override
    public float getY() {
        return circle.y - POSITION;
    }

    @Override
    public TokenType getType() {
        return TokenType.CAMP;
    }
}
