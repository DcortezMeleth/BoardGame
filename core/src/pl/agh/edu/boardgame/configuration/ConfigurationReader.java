package pl.agh.edu.boardgame.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa wczytujaca konfiguracje z pliku.
 *
 * @author Michał
 */
public class ConfigurationReader {

    /** Logger. */
    private Logger logger = LogManager.getLogger(ConfigurationReader.class.getName());

    /** Polozenie pliku z konfiguracja gry. */
    private static final String CONFIGURATION_FILE = "resources/boardgame.properties";

    /**
     * Metoda wczytuje konfiguracje i zwraca ja w postaci mapy.
     *
     * @return mapa z wczytanymi wartoscaimi.
     */
    public Map<String, String> getConfiguration() {
        Map<String, String> resultMap = new HashMap<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(CONFIGURATION_FILE));
            String line = bufferedReader.readLine();
            while(line != null) {
                String[] lineBreaker = line.split(" = ");
                if(lineBreaker.length == 2) {
                    resultMap.put(lineBreaker[0], lineBreaker[1]);
                    line = bufferedReader.readLine();
                }
            }
        } catch(IOException e1) {
            logger.debug(e1.getMessage());
        }
        return resultMap;
    }
}
