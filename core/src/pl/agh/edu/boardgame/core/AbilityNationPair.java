package pl.agh.edu.boardgame.core;

import com.badlogic.gdx.math.Polygon;
import pl.agh.edu.boardgame.abilities.Ability;
import pl.agh.edu.boardgame.abilities.AbilityType;
import pl.agh.edu.boardgame.nations.Nation;

import java.io.Serializable;

/**
 * Klasa maja na celu trzymac powiazanie pomiedzy rasa i umiejetnoscia.
 *
 * @author Bartosz
 */
public class AbilityNationPair implements Serializable {

    /** Rasa */
    private Nation nation;

    /** Umiejetnosc */
    private Ability ability;

    /** Monety lezace na danej parze. */
    private int coins = 0;

    /** Czy po kliknieciu pokazac tooltip. */
    private boolean tooltip = true;

    public AbilityNationPair(final Ability ability, final Nation nation) {
        this.ability = ability;
        this.nation = nation;
    }

    public Ability getAbility() {
        return ability;
    }

    public Nation getNation() {
        return nation;
    }

    /** Metoda sprawdza czy jest aktualnie aktywna rasa. */
    public boolean isActive() {
        return nation.isActive();
    }

    public void setActive(final boolean active) {
        nation.setActive(active);
        ability.setActive(active);
    }

    public boolean isTooltip() {
        return tooltip;
    }

    public void setTooltip(boolean tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * Zwraca zebrane na parze monety. Dodatkowo w przypadku zamoznych dolicza 7 dodatowych monet.
     *
     * @return monety ktore naleza sie graczowi za wybor tej rasy
     */
    public int getCoins() {
        return ability.getAbilityType() == AbilityType.WEALTH ? coins + 7 : coins;
    }

    public void addCoin() {
        coins++;
    }

    public void move(final int x) {
        Polygon abilityPolygon = ability.getPolygon();
        Polygon nationPolygon = nation.getBannerPolygon();
        abilityPolygon.setPosition(abilityPolygon.getX() + x, abilityPolygon.getY());
        nationPolygon.setPosition(nationPolygon.getX() + x, nationPolygon.getY());
    }

    public void dispose() {
        nation.dispose();
        ability.dispose();
    }
}