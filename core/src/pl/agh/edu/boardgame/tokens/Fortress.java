package pl.agh.edu.boardgame.tokens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import pl.agh.edu.boardgame.abilities.AbilityType;
import pl.agh.edu.boardgame.core.Player;
import pl.agh.edu.boardgame.map.fields.Field;

/**
 * @author Bartosz
 */
public class Fortress extends BaseToken {

    /** Wierzcholki wielokata opisujacego token. */
    private final static float[] VERTICES = new float[]{
            0f, 0f,
            60f, 0f,
            60f, 60f,
            0f, 60f
    };

    /** Tekstura przycisku. */
    private static Texture texture;

    /** Ksztalt tokenu. */
    private final Polygon polygon;

    /** Czy token zostal juz polozony w tej turze. */
    private static boolean USED_IN_THIS_TURN = false;

    /** Czy token zostal rozmieszczony. */
    private boolean placed = false;

    static {
        texture = new Texture(Gdx.files.internal(TEXTURES_PATH + "twierdza.png"));
    }

    public Fortress() {
        super(1150, 800);
        this.polygon = new Polygon(VERTICES);
    }

    @Override
    public void setPosition(final float x, final float y) {
        polygon.setPosition(x, y);
    }

    @Override
    public boolean validUser(final Player player) {
        return player.getActiveAbility() != null && getField() == null
                && player.getActiveAbility().getAbilityType() == AbilityType.FORTIFIED;
    }

    @Override
    public void setField(final Field field) {
        super.setField(field);
        if(field != null) {
            field.setFortress(true);
            USED_IN_THIS_TURN = true;
        }
    }

    @Override
    public boolean contains(final int x, final int y) {
        return polygon.contains(x, y);
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public boolean validTarget(final Field field) {
        return field.getOwner() != null && !field.isFortress() &&
                field.getOwner().getActiveAbility().getAbilityType() == AbilityType.FORTIFIED;
    }

    @Override
    public void resetPosition() {
        if(getField() != null) {
            getField().setFortress(false);
        }
        super.resetPosition();
    }

    @Override
    public boolean validThisTurn() {
        return !placed && !USED_IN_THIS_TURN;
    }

    @Override
    public void resetUsage() {
        USED_IN_THIS_TURN = false;
    }

    @Override
    public float getX() {
        return polygon.getX();
    }

    @Override
    public float getY() {
        return polygon.getY();
    }

    @Override
    public TokenType getType() {
        return TokenType.FORTRESS;
    }
}
