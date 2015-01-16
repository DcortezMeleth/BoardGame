package pl.agh.edu.boardgame.nations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import pl.agh.edu.boardgame.core.Player;
import pl.agh.edu.boardgame.map.fields.Field;

/**
 * Interfejs rasy. Klasa odpowiada rownoczesnie sztandarowi i tokenom.
 *
 * @author Bartosz
 */
public interface Nation {

    /** Zwraca nazwe rasy. */
    String getName();

    /** Zwraca opis rasy. */
    String getDescription();

    /** Zwraca maksymalna ilosc jednostek. */
    Integer getMaxUnits();

    /** Metoda zwraca typ rasy. */
    NationType getNationType();

    /** Metoda sprawdza czy rasa jest aktywna. */
    boolean isActive();

    /** Metoda ustawia aktywnosc rasy. */
    void setActive(final boolean active);

    /** Metoda zwraca teksture armii, odpowiednia dla stanu rasy (aktywna/nieaktywna). */
    Texture getArmyTexture();

    /** Metoda zwraca teksture banneru, odpowiednia dla stanu rasy (aktywna/nieaktywna). */
    Texture getBannerTexture();

    /** Zwraca wielokat opisujacy token armii. */
    Polygon getArmyPolygon();

    /** Zwraca wielokat opisujacy sztandar rasy. */
    Polygon getBannerPolygon();

    /** Metoda niszczy tekstury. */
    void dispose();

    /** Zwraca pole na ktorym stoi jednostka. */
    Field getField();

    /** Ustawia pole na ktorym stoi jednostka. */
    void setField(Field field);

    /**
     * Metoda liczy modyfikatory do ataku.
     *
     * @param minArmySize rozmiar armii przed liczeniem
     * @param field       pole ktore atakujemy
     *
     * @return zmodyfikowany rozmiar armii
     */
    int countAttackPerks(int minArmySize, Field field);

    /** Metoda dolicza do dochodu bonusy z rasy. */
    int countIncome(int income, Player player);
}
