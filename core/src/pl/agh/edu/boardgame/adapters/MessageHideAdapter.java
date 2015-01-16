package pl.agh.edu.boardgame.adapters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import pl.agh.edu.boardgame.core.BoardGameMain;

/**
 * Adapter odpoweidzlany za znikanie przycisku.
 *
 * @author Bartosz
 */
public class MessageHideAdapter extends InputAdapter {

    /** Glowna klasa gry. */
    private final BoardGameMain game;

    public MessageHideAdapter(BoardGameMain game) {
        this.game = game;
    }

    @Override
    public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
        if(game.isMessageSet()) {
            if(game.isWinner()) {
                Gdx.app.exit();
            }

            game.setMessageToShow("");
            return true;
        }

        if(game.getPairToTooltip() != null) {
            game.setPairToTooltip(null);
            return true;
        }

        return false;
    }
}
