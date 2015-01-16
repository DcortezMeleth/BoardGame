package pl.agh.edu.boardgame.adapters;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.agh.edu.boardgame.configuration.Configuration;
import pl.agh.edu.boardgame.core.BoardGameMain;
import pl.agh.edu.boardgame.map.fields.Field;
import pl.agh.edu.boardgame.nations.Nation;

/**
 * Adapter odpowiedzalny za obsluge przesuwania tokenow armii w fazie przegurpowania.
 *
 * @author Bartosz
 */
public class RegroupDragAdapter extends DragListener implements InputProcessor {

    /** Logger. */
    private final static Logger LOGGER = LogManager.getLogger(RegroupDragAdapter.class);

    /** Konfiguracja gry. */
    private final Configuration configuration;

    /** Główna klasa gry. */
    private final BoardGameMain game;

    /** Aktualnie przesuwany token. */
    private Nation token;

    /** Czy obsuzono zdarzenie. */
    private boolean actionDone = false;

    /** Polozenie poczatkowe tokenu - x */
    private float initX;

    /** Polozenie poczatkowe tokenu - y */
    private float initY;

    public RegroupDragAdapter(final BoardGameMain game, final Configuration configuration) {
        this.game = game;
        this.configuration = configuration;
        setTapSquareSize(20);
    }

    @Override
    public boolean touchDown(final InputEvent event, final float x, final float screenY, final int pointer, final int button) {
        int y = (int) (configuration.getIntProperty(Configuration.APP_HEIGHT) - screenY);

        // sprawdzamy czy dotknieto jakis token
        for(Nation token1 : game.getActivePlayer().getAllTokens()) {
            if(token1.getArmyPolygon().contains(x, y)) {
                LOGGER.debug("Dotknieto tokenu.");
                actionDone = true;
                token = token1;
                initX = token.getArmyPolygon().getX();
                initY = token.getArmyPolygon().getY();
                return super.touchDown(event, x, y, pointer, button);
            }
        }

        return false;
    }

    /**
     * Zdarzenie wolane przy starcie przeciagania.
     *
     * @param event   {@link com.badlogic.gdx.scenes.scene2d.InputEvent}
     * @param x       wspolrzedna x
     * @param screenY wspolrzedna y
     * @param pointer pointer
     */
    @Override
    public void dragStart(final InputEvent event, final float x, final float screenY, final int pointer) {
        int y = (int) (configuration.getIntProperty(Configuration.APP_HEIGHT) - screenY);

        LOGGER.debug("Start przeciagania.");
        actionDone = true;

        super.dragStart(event, x, y, pointer);
    }

    /**
     * Zdarzenie wolane podczas przeciagania.
     *
     * @param event   {@link com.badlogic.gdx.scenes.scene2d.InputEvent}
     * @param x       wspolrzedna x
     * @param screenY wspolrzedna y
     * @param pointer pointer
     */
    @Override
    public void drag(final InputEvent event, final float x, final float screenY, final int pointer) {
        int y = (int) (configuration.getIntProperty(Configuration.APP_HEIGHT) - screenY);

        moveToken();
        actionDone = true;

        super.drag(event, x, y, pointer);
    }

    /**
     * Zdarzenie wolane na koniec przeciagania.
     *
     * @param event   {@link com.badlogic.gdx.scenes.scene2d.InputEvent}
     * @param x       wspolrzedna x
     * @param screenY wspolrzedna y
     * @param pointer pointer
     */
    @Override
    public void dragStop(final InputEvent event, final float x, final float screenY, final int pointer) {
        int y = (int) (configuration.getIntProperty(Configuration.APP_HEIGHT) - screenY);

        actionDone = true;

        boolean moved = false;
        for(Field field : game.getMap().getFields()) {
            if(field.getPolygon().contains(x, y) && game.getActivePlayer().getOwnedLands().contains(field)) {
                LOGGER.debug("Przegrupowuje!!");
                if(token.getField() != null) {
                    token.getField().getArmy().remove(token);
                }
                field.addArmyToken(token);
                game.getActivePlayer().getStillAvailableTokens().remove(token);
                game.getActivePlayer().getTokensWithoutField().remove(token);
                moved = true;
            }
        }
        if(!moved) {
            token.getArmyPolygon().setPosition(initX, initY);
        }

        token = null;

        super.dragStop(event, x, y, pointer);
    }

    /** Uaktualnanie wspolrzednych tokenu. Tu odbywa sie prawdziwe przesuwanie. */
    private void moveToken() {
        float x = token.getArmyPolygon().getX();
        float y = token.getArmyPolygon().getY();

        token.getArmyPolygon().setPosition(x - getDeltaX(), y + getDeltaY());
    }

    @Override
    public boolean scrolled(final int amount) {
        return false;
    }

    @Override
    public boolean mouseMoved(final int screenX, final int screenY) {
        return false;
    }

    @Override
    public boolean keyDown(final int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(final int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(final char character) {
        return false;
    }

    @Override
    public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
        if(game.getPhase() != BoardGameMain.TurnPhase.REGROUP) {
            return false;
        }

        return touchDown(null, screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(final int screenX, final int screenY, final int pointer, final int button) {
        if(game.getPhase() != BoardGameMain.TurnPhase.REGROUP) {
            return false;
        }

        actionDone = false;
        touchUp(null, screenX, screenY, pointer, button);
        return actionDone;
    }

    @Override
    public boolean touchDragged(final int screenX, final int screenY, final int pointer) {
        if(game.getPhase() != BoardGameMain.TurnPhase.REGROUP) {
            return false;
        }

        actionDone = false;
        touchDragged(null, screenX, screenY, pointer);
        return actionDone;
    }
}
