package pl.agh.edu.boardgame.map.fields;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import pl.agh.edu.boardgame.core.BoardGameMain;
import pl.agh.edu.boardgame.core.Player;
import pl.agh.edu.boardgame.nations.Nation;
import pl.agh.edu.boardgame.nations.NationType;

import java.util.List;

/**
 * @author Bartosz
 */
public interface Field {

    /**
     * Zwraca typ pola.
     *
     * @return wartosc z enuma FieldType
     */
    BaseField.FieldType getType();

    /**
     * Czy pole zawiera kopalnie.
     *
     * @return true jesli zawiera, false wpp.
     */
    boolean isMine();

    /**
     * Czy pole zawiera jaskinie.
     *
     * @return true jesli zawiera, false wpp.
     */
    boolean isCave();

    /**
     * Czy pole zawiera zrodlo magii.
     *
     * @return true jesli zawiera, false wpp.
     */
    boolean isSourceOfMagic();

    /**
     * @return tektura odpowiednia dla typu pola
     */
    Texture getTexture();

    /**
     * @return zwraca wielokat opisujacy pole
     */
    Polygon getPolygon();

    boolean isBurrow();

    void setBurrow(boolean burrow);

    boolean isLore();

    void setLore(boolean lore);

    boolean isFortress();

    void setFortress(boolean fortress);

    boolean isCamp();

    void setCamp(boolean camp);

    boolean isDragon();

    void setDragon(boolean dragon);

    boolean isHero();

    void setHero(boolean hero);

    Player getImmunity();

    void setImmunity(Player immunity);

    void setNeighbours(List<Field> neighbours);

    List<Field> getNeighbours();

    /** Zwraca jednoski stojace na polu. */
    List<Nation> getArmy();

    /**
     * Dodaje jednostke do listy atakujacych
     *
     * @param army jednostka
     */
    void addAttackingToken(Nation army);

    /**
     * Zwraca typ armii stacjonujacej na polu
     *
     * @return {@link pl.agh.edu.boardgame.nations.NationType Rasa} stacjonujaca na polu, null gdy pole niczyje
     */
    NationType getNationType();

    /** Zwraca jednoski atakujace pole. */
    List<Nation> getAttackingArmy();

    /** Zwraca wlasciciela pola. */
    Player getOwner();

    /** Ustawia nowego wlasciciela pola. */
    void setOwner(Player owner);

    /** Czy gracz moze zaatakowac to pole. */
    boolean isValidAttacker(Player player, Nation nation, BoardGameMain game);

    /**
     * Atakowanie pola i rzeczy ktore sa tym zwiazane.
     *
     * @param player aktywny gracz (atakujacy)
     */
    void attack(Player player, BoardGameMain game);

    /** Rysowanie pola na ekranie. */
    void draw(Batch batch);

    /**
     * Dodaje token armii do pola. Uzywane podczas reorganizacji albo wycofania sie wojsk.
     *
     * @param token token do dodania
     */
    void addArmyToken(Nation token);

    /** Zbiera armie i ustawia jej pozycje na jednej stercie. */
    void positionArmy();

    /** Czysci atakujace armie na polu. */
    void resetAttackingArmy();
}
