package pl.agh.edu.boardgame.tokens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import pl.agh.edu.boardgame.abilities.AbilityType;
import pl.agh.edu.boardgame.core.Player;
import pl.agh.edu.boardgame.map.fields.Field;

/**
 * Klasa bohatera.
 *
 * @author Bartosz
 */
public class Hero extends BaseToken {

    /** Pozycja srodka ogregu na teksturze. Tutaj taka sama dla x i y. */
    public static final int POSITION = 25;

    /** Peomien okregu. */
    private static final float RADIUS = 21f;

    /** Tekstura przycisku. */
    private static Texture texture;

    /** Wierzcholki wielokata opisujacego przycisk. */
    private final Circle circle;

    static {
        texture = new Texture(Gdx.files.internal(TEXTURES_PATH + "bohater.png"));
    }

    public Hero() {
        super(530, 710);
        this.circle = new Circle(POSITION, POSITION, RADIUS);
    }

    @Override
    public void setPosition(final float x, final float y) {
        circle.setPosition(x + POSITION, y + POSITION);
    }

    @Override
    public boolean validUser(final Player player) {
        return player.getActiveAbility() != null &&
                player.getActiveAbility().getAbilityType() == AbilityType.HEROIC;
    }

    @Override
    public void setField(final Field field) {
        if(getField() != null) {
            getField().setHero(false);
        }
        super.setField(field);
        if(field != null) {
            field.setHero(true);
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
    public boolean validTarget(final Field field) {
        return field.getOwner() != null &&
                field.getOwner().getActiveAbility().getAbilityType() == AbilityType.HEROIC;
    }

    @Override
    public void resetPosition() {
        if(getField() != null) {
            getField().setHero(false);
        }
        super.resetPosition();
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
        return TokenType.HERO;
    }
}
