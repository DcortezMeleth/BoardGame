package pl.agh.edu.boardgame.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import pl.agh.edu.boardgame.core.Player;
import pl.agh.edu.boardgame.map.fields.Field;

import java.io.Serializable;

/**
 * @author Bartosz
 */
public abstract class BaseAbility implements Ability, Serializable {

    /** Wielokat opisujacy teksture na ekranie. */
    private final Polygon polygon;

    /** Wierzcholki wielokat na naszej teksturze. */
    private final float[] VERTICES = new float[]{
            0f, 0f,
            120f, 0f,
            120f, 120f,
            0f, 120f
    };

    /** Teskstura aktywnej umiejetnosci. */
    private Texture activeTexture;

    /** Teskstura nieaktywnej umiejetnosci. */
    private Texture inactiveTexture;

    /** Czy umiejetnosc jest aktywna. */
    private boolean active = true;

    protected BaseAbility() {
        this.polygon = new Polygon(VERTICES);
        inactiveTexture = new Texture(Gdx.files.internal("assets/Textures/power_badges/zwykly_tyl.png"));
    }

    @Override
    public Texture getTexture() {
        return active ? activeTexture : inactiveTexture;
    }

    protected void setActiveTexture(Texture texture) {
        this.activeTexture = texture;
    }

    protected void setInactiveTexture(Texture texture) {
        this.inactiveTexture = texture;
    }

    @Override
    public Polygon getPolygon() {
        return polygon;
    }

    @Override
    public void dispose() {
        activeTexture.dispose();
        inactiveTexture.dispose();
    }

    @Override
    public int countAttackPerks(final int minArmySize, final Field field) {
        return minArmySize;
    }

    @Override
    public int countIncome(final int income, final Player player) {
        return income;
    }

    @Override
    public void setActive(final boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String getDescription() {
        return "ability." + getKey() + ".desc";
    }

    @Override
    public String getName() {
        return "ability." + getKey() + ".name";
    }

    /** Zwraca klucz z GameBundle po ktorym trzeba szukac tej umiejetnosci. */
    protected abstract String getKey();
}
