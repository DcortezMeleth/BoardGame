package pl.agh.edu.boardgame.tokens;

import com.badlogic.gdx.graphics.Texture;
import pl.agh.edu.boardgame.core.Player;
import pl.agh.edu.boardgame.map.fields.Field;

/**
 * Interfejs tokenu.
 *
 * @author Bartosz
 */
public interface Token {

    /** Zwraca teksture tokenu. */
    Texture getTexture();

    /** Czy punkt zawiera sie w tokenie. */
    boolean contains(int x, int y);

    /** Pole na ktorym lezy token. */
    Field getField();

    /** Ustawia pole na ktorym lezy token. */
    void setField(Field field);

    /** Czy gracz moze uzyc tego tokenu. */
    boolean validUser(Player player);

    /** Czy mozna ustawic ten token na tym polu. */
    boolean validTarget(Field field);

    /** Ustawia pozycje tokenu. */
    void setPosition(float x, float y);

    /** Resetuje pozycje do startowej. */
    void resetPosition();

    /** Zwraca typ tokenu. */
    TokenType getType();

    float getX();

    float getY();
}
