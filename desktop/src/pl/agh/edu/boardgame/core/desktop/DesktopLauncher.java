package pl.agh.edu.boardgame.core.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import pl.agh.edu.boardgame.configuration.Configuration;
import pl.agh.edu.boardgame.core.BoardGameMain;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Configuration configuration = new Configuration();

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = configuration.getProperty(Configuration.APP_NAME);
        config.width = configuration.getIntProperty(Configuration.APP_WIDTH);
        config.height = configuration.getIntProperty(Configuration.APP_HEIGHT);
        config.fullscreen = configuration.getBoolProperty(Configuration.FULLSCREEN);

        new LwjglApplication(new BoardGameMain(configuration), config);
    }
}
