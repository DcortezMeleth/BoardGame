package pl.agh.edu.boardgame.adapters;

import com.badlogic.gdx.InputAdapter;
import pl.agh.edu.boardgame.buttons.MoneyButton;
import pl.agh.edu.boardgame.configuration.Configuration;

/**
 * Adapter odpowiedzialny za wyswietlanie pieniedzy.
 *
 * @author Bartosz
 */
public class MoneyButtonAdapter extends InputAdapter {

    /** Button. */
    private final MoneyButton moneyButton;

    /** Konfiguracja gry. */
    private final Configuration configuration;

    public MoneyButtonAdapter(final Configuration configuration, final MoneyButton moneyButton) {
        this.configuration = configuration;
        this.moneyButton = moneyButton;
    }

    @Override
    public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
        int y = configuration.getIntProperty(Configuration.APP_HEIGHT) - screenY;

        if(moneyButton.getPolygon().contains(screenX, y)) {
            moneyButton.negate();
            return true;
        }

        return false;
    }
}
