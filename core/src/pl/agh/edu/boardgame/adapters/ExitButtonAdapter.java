package pl.agh.edu.boardgame.adapters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import pl.agh.edu.boardgame.buttons.ExitButton;
import pl.agh.edu.boardgame.configuration.Configuration;

/**
 * Adapter odpowiedzialny za wychodzenie z gry.
 *
 * @author Bartosz
 */
public class ExitButtonAdapter extends InputAdapter {

    /** Button. */
    private final ExitButton exitButton;

    /** Konfiguracja gry. */
    private final Configuration configuration;


    public ExitButtonAdapter(final Configuration configuration, final ExitButton exitButton) {
        this.configuration = configuration;
        this.exitButton = exitButton;
    }

    @Override
    public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
        int y = configuration.getIntProperty(Configuration.APP_HEIGHT) - screenY;

        if(exitButton.getPolygon().contains(screenX, y)) {
            Gdx.app.exit();
        }

        return false;
    }
}
