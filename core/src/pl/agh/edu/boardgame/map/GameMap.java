package pl.agh.edu.boardgame.map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.agh.edu.boardgame.configuration.Configuration;
import pl.agh.edu.boardgame.map.fields.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Klasa mapy gry.
 *
 * @author Bartosz
 */
public class GameMap {

    /** Logger. */
    private final Logger logger = LogManager.getLogger(GameMap.class);

    /** Lista pol mapy. */
    private final List<Field> fields;

    /** Odleglosc miedzy sasiadujacymi polami na mapie. */
    private final int DISTANCE = 121;

    /** Roznica miedzy kafelkami w x. */
    private final int X_DIFF = 105;

    /** Szerokość kafelka na podstawie. Rownoznaczne z dlugoscia krawedzi. */
    private final int FIELD_WIDTH = 70;

    /** Różnica między kafelkami w y. */
    private final int Y_DIFF = 60;

    /** Różnica między początkiem tekstury a początkiem wielokąta oznaczającego pole. */
    private final int INIT_X = 40;

    /** Pozostala liczba kopalnii do wygenerowania. */
    private int mines;

    /** Pozostala liczba źródeł magii do wygenerowania. */
    private int magicSources;

    /** Pozostała liczba jaskiń do wygenerowania. */
    private int caves;

    /** Rozmiar mapy. */
    private int mapSize;

    /** Współrzędna x pierwszego klocka w kolumnie. */
    private int init_x;

    /** Wspolrzedna y pierwszego klocka w kolumnie. */
    private int init_y;

    public GameMap(Configuration configuration) {
        mapSize = configuration.getIntProperty(Configuration.MAP_SIZE);
        mines = configuration.getIntProperty(Configuration.MINES_AMOUNT);
        magicSources = configuration.getIntProperty(Configuration.MAGIC_SOURCES_AMOUNT);
        caves = configuration.getIntProperty(Configuration.CAVES_AMOUNT);

        int rowSize = (int) Math.sqrt(mapSize);
        init_x = (configuration.getIntProperty(Configuration.APP_WIDTH) - (3*rowSize - 2)*FIELD_WIDTH)/2 - INIT_X;
        init_y = (configuration.getIntProperty(Configuration.APP_HEIGHT))/2 - Y_DIFF;

        fields = generateMap();

        if(fields.size() == mapSize) {
            logger.debug("Map creation succeeded!");
        } else {
            logger.debug("Map creation failed! Created " + fields.size() + " fields, while should " + mapSize);
        }
    }

    /**
     * Metoda generuje mapę na podstawie parametrów z konfiguracji.
     *
     * @return list pol mapy
     */
    private List<Field> generateMap() {
        List<Field> result = new ArrayList<>();

        for(int i = 0; i < mapSize; ++i) {
            Random random = new Random();
            boolean cave = false;
            boolean sourceOfMagic = false;
            boolean mine = false;

            if(mines > 0) {
                mine = random.nextInt(2)%2 == 0;
                if(mine) {
                    mines--;
                }
            }
            if(magicSources > 0 && !mine) {
                sourceOfMagic = random.nextInt(2)%2 == 0;
                if(sourceOfMagic) {
                    magicSources--;
                }
            }
            if(caves > 0) {
                cave = random.nextInt(2)%2 == 0;
                if(cave) {
                    caves--;
                }
            }

            boolean canBeLake = !(cave || sourceOfMagic || mine);

            Field field = createField(BaseField.FieldType.getRandomType(canBeLake), cave, sourceOfMagic, mine);
            result.add(field);
        }

        Collections.shuffle(result);
        setPositions(result);
        setNeighbours(result);

        return result;
    }

    /** Metoda ustawia polom listy sasiadow. */
    private void setNeighbours(final List<Field> result) {
        for(Field field : result) {
            List<Field> neighbours = new ArrayList<>();
            for(Field field1 : result) {
                if(field != field1 && dist(field, field1) <= DISTANCE) {
                    neighbours.add(field1);
                }
            }
            field.setNeighbours(neighbours);
        }
    }

    /**
     * Funkcja liczy dystans miedzy polami.
     *
     * @param field  pierwsze pole
     * @param field1 drugie pole
     */
    private float dist(final Field field, final Field field1) {
        float x = field.getPolygon().getX() - field1.getPolygon().getX();
        float y = field.getPolygon().getY() - field1.getPolygon().getY();

        return (float) Math.sqrt(x*x + y*y);
    }

    /**
     * Metoda tworzy pole.
     *
     * @param type          typ pola jakie ma zostać utworzone
     * @param cave          czy pole ma zawierać jaskinie
     * @param sourceOfMagic czy pole ma zawierać źródło magii
     * @param mine          czy pole ma zawierać kopalnie
     *
     * @return utworzone i zainicjowane pole
     */
    private Field createField(final BaseField.FieldType type, final boolean cave, final boolean sourceOfMagic, final
    boolean mine) {
        Field field = null;
        switch(type) {
            case PLAINS:
                field = new Plains(cave, sourceOfMagic, mine);
                break;
            case SWAMP:
                field = new Swamps(cave, sourceOfMagic, mine);
                break;
            case MOUNTAIN:
                field = new Mountains(cave, sourceOfMagic, mine);
                break;
            case FOREST:
                field = new Forest(cave, sourceOfMagic, mine);
                break;
            case LAKE:
                field = new Lake(cave, sourceOfMagic, mine);
                break;
            case HILLS:
                field = new Hills(cave, sourceOfMagic, mine);
                break;
        }
        return field;
    }

    /**
     * Metoda ustawia pozycje pol na ekranie.
     *
     * @param fieldsList list pol które chcemy rozmieścić
     */
    private void setPositions(final List<Field> fieldsList) {
        logger.debug("Ustawiam pozycje.");

        int counter = 0;
        for(Field field : fieldsList) {
            int x = init_x + counter*X_DIFF;
            int y = init_y - counter*Y_DIFF;
            field.getPolygon().setPosition(x, y);

            counter = counter != (int) (Math.sqrt(mapSize) - 1) ? counter + 1 : 0;
            if(counter == 0) {
                init_x += X_DIFF;
                init_y += Y_DIFF;
            }
        }

        logger.debug("Pozycje pol ustawione.");
    }

    /**
     * @return zwraca listę pol mapy
     */
    public List<Field> getFields() {
        return fields;
    }

    public void resetAttackingArmies() {
        for(Field field : fields) {
            field.resetAttackingArmy();
        }
    }

    /** Zwraca pole aktualnie atakowane przez gracza. */
    public Field getAttackedField() {
        for(Field field : fields) {
            if(field.getAttackingArmy().size() != 0) {
                return field;
            }
        }
        return null;
    }
}
