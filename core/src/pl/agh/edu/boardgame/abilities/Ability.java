package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import pl.agh.edu.boardgame.core.Player;
import pl.agh.edu.boardgame.map.fields.Field;

/**
 * Interfejs umiejetnosci.
 *
 * @author Bartosz
 */
public interface Ability {

    /** Zwraca nazwe umiejetnosci. */
    String getName();

    /** Zwraca opis umiejetnosci. */
    String getDescription();

    /** Zwraca wartosc enuma odpowiadajacego umiejetnosci. */
    AbilityType getAbilityType();

    /** Zwraca maksymalna ilosc jednostek. */
    int getMaxUnits();

    /** Zwraca wielokat opisujacy teksture umiejetnosci. */
    Polygon getPolygon();

    /** Zwraca teksture umiejetnosci. */
    Texture getTexture();

    /** Metoda niszczy tekstury. */
    void dispose();

    /**
     * Metoda dolicza do wymaganego rozmiaru armii bonusy z umiejetnosci.
     *
     * @param minArmySize rozmiar armii potrzebny do podboju przed liczeniem
     * @param field       pole ktoreatakujemy
     *
     * @return rozmiar armii potrzebny po dodaniu modyfikatorow
     */
    int countAttackPerks(int minArmySize, Field field);

    /** Metoda dolicza do dochodu bonusy z rasy. */
    int countIncome(int income, Player player);

    /** Ustawia aktywnosc umiejetnosci. */
    void setActive(boolean active);
}
