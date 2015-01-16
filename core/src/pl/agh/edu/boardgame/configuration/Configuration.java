package pl.agh.edu.boardgame.configuration;

import java.util.Map;

/**
 * Klasa przechowujaca konfiguracje aplikacji.
 *
 * @author Bartosz
 */
public class Configuration {

    /** Nazwa aplikacji. */
    public static final String APP_NAME = "APP_NAME";

    /** Domyslna szczerokosc okna. */
    public static final String APP_WIDTH = "APP_WIDTH";

    /** Domyslna wysokosc okna. */
    public static final String APP_HEIGHT = "APP_HEIGHT";

    /** Rozmiar mapy (ilosc klockow). */
    public static final String MAP_SIZE = "MAP_SIZE";

    /** Ilosc jakin na mapie. */
    public static final String CAVES_AMOUNT = "CAVES_AMOUNT";

    /** Ilosc zrodel magii na mapie. */
    public static final String MAGIC_SOURCES_AMOUNT = "MAGIC_SOURCES_AMOUNT";

    /** Ilosc kopalni na mapie. */
    public static final String MINES_AMOUNT = "MINES_AMOUNT";

    /** Jezyk gry - lokalizacja. */
    public static final String LOCALE = "LOCALE";

    /** Imie pierwszego gracza. */
    public static final String PLAYER_1 = "PLAYER_1";

    /** Imie pierwszego gracza. */
    public static final String PLAYER_2 = "PLAYER_2";

    /** Imie pierwszego gracza. */
    public static final String PLAYER_3 = "PLAYER_3";

    /** Imie pierwszego gracza. */
    public static final String PLAYER_4 = "PLAYER_4";

    /** Czy uruchamiamy gre domyslnie w trybie peloekranowym. */
    public static final String FULLSCREEN = "FULLSCREEN";

    /** Ilosc tur */
    public static final String TURN = "TURN";

    /** Mapa z propertisami. */
    private final Map<String, String> properties;

    public Configuration() {
        ConfigurationReader reader = new ConfigurationReader();
        this.properties = reader.getConfiguration();
    }

    /**
     * Metoda zwraca wybrana wartosc z konfiguracji.
     *
     * @param key klucz
     *
     * @return wartosc jako String
     */
    public String getProperty(String key) {
        return properties.get(key);
    }

    /**
     * Metoda zwraca wybrana wartosc z konfiguracji.
     *
     * @param key klucz
     *
     * @return wartosc jako Integer
     */
    public Integer getIntProperty(String key) {
        return Integer.valueOf(properties.get(key));
    }

    /**
     * Metoda zwraca wybrana wartosc z konfiguracji.
     *
     * @param key klucz
     *
     * @return wartosc jako Boolean
     */
    public Boolean getBoolProperty(String key) {
        return Boolean.valueOf(properties.get(key));
    }
}
