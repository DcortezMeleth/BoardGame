package pl.agh.edu.boardgame.map.fields;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.agh.edu.boardgame.abilities.AbilityType;
import pl.agh.edu.boardgame.core.BoardGameMain;
import pl.agh.edu.boardgame.core.Player;
import pl.agh.edu.boardgame.nations.Nation;
import pl.agh.edu.boardgame.nations.NationType;
import pl.agh.edu.boardgame.tokens.TokenType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Bartosz
 */
public abstract class BaseField implements Field, Serializable {

    /** Logger. */
    protected final static Logger LOGGER = LogManager.getLogger(BaseField.class);

    /** Sciezka do tekstur mapy. */
    protected static final String REGIONS_HEX = "assets/Textures/regions_hex/";

    /** Wzgledna sciezka do symbolow kopalnii, jaskini i zrodla magii. */
    private static final String MAP_SYMBOLS = "map_symbols/";

    /** Sciezka do tekstur tokenow. */
    private static final String TOKENS_PATH = "assets/Textures/small_parts/";

    /** Tekstura kopalnii. */
    private static Texture mineTexture;

    /** Tekstura jaskini. */
    private static Texture caveTexture;

    /** Tekstura zrodla magii. */
    private static Texture sourceOfMagicTexture;

    /** Tekstura legowiska. */
    private static Texture loreTexture;

    /** Tekstura norki. */
    private static Texture burrowTexture;

    /** Tekstura wymierajacych plemion. */
    private static Texture extinctTribeTexture;

    /** Tekstura gory. */
    private static Texture mountainTexture;

    /** Indicates if field contains cave. */
    private final boolean cave;

    /** Indicates if field contains source of magic. */
    private final boolean sourceOfMagic;

    /** Indicates if field contains mine. */
    private final boolean mine;

    /** Czy na polu jest wymierajace plemie. */
    private boolean extinctTribe = false;

    /** Czy na polu jest forteca. Wynika z {@link pl.agh.edu.boardgame.abilities.Fortified Warowne}. */
    private boolean fortress = false;

    /** Czy na polu jest oboz. Wynika z {@link pl.agh.edu.boardgame.abilities.Camper Obozujace}. */
    private boolean camp = false;

    /** Czy na polu stoi smok. Wynika z {@link pl.agh.edu.boardgame.abilities.DragonLords Wladcy Smokow}. */
    private boolean dragon = false;

    /** Czy na polu jest legowisko. Wynika z {@link pl.agh.edu.boardgame.nations.Trolls Trolle}. */
    private boolean lore = false;

    /** Czy na polu jest norka. Wynika z {@link pl.agh.edu.boardgame.nations.Halflings Niziolki}. */
    private boolean burrow = false;

    /** Czy na polu jest bohater. Wynika z {@link pl.agh.edu.boardgame.abilities.Heroic Bohaterskie}. */
    private boolean hero = false;

    /** Gracz nie mogacy atakowac tego pola. Wynika z {@link pl.agh.edu.boardgame.abilities.Diplomatic Diplomatic}. */
    private Player immunity = null;

    /** Shape describing field on screen. */
    private final Polygon polygon;

    /** Jednostki na tym polu. */
    private List<Nation> army = Collections.synchronizedList(new ArrayList<Nation>());

    /** Jednostki atakujace to pole. */
    private List<Nation> attackingArmy = new ArrayList<>();

    /** Lista sasiadujacych pol. */
    private List<Field> neighbours = new ArrayList<>();

    /** Tekstura. */
    private Texture texture;

    /** Wlasciciel pola */
    private Player owner;

    /** Wierzcholki szesciokata na naszej teksturze. */
    private final float[] VERTICES = new float[]{
            40f, 0f,
            110f, 0f,
            145f, 60f,
            110f, 120f,
            40f, 120f,
            5f, 60f
    };

    /** Statycznie inicjalizujemy statczne pola tekstur. */
    static {
        caveTexture = new Texture(Gdx.files.internal(REGIONS_HEX + MAP_SYMBOLS + "jaskinia.png"));
        mineTexture = new Texture(Gdx.files.internal(REGIONS_HEX + MAP_SYMBOLS + "kopalnia.png"));
        sourceOfMagicTexture = new Texture(Gdx.files.internal(REGIONS_HEX + MAP_SYMBOLS + "magiczne_zrodlo.png"));
        loreTexture = new Texture(Gdx.files.internal(TOKENS_PATH + "legowisko.png"));
        burrowTexture = new Texture(Gdx.files.internal(TOKENS_PATH + "norka.png"));
        mountainTexture = new Texture(Gdx.files.internal(TOKENS_PATH + "gora.png"));
        extinctTribeTexture = new Texture(Gdx.files.internal(TOKENS_PATH + "tokens/ginace_plemie.png"));
    }

    protected BaseField(final boolean cave, final boolean sourceOfMagic, final boolean mine) {
        this.cave = cave;
        this.sourceOfMagic = sourceOfMagic;
        this.mine = mine;

        extinctTribe = getType() != FieldType.LAKE && (new Random()).nextInt(3) == 0;

        this.polygon = new Polygon(VERTICES);
    }

    /** Usuwa tekstury. */
    public static void dispose() {
        caveTexture.dispose();
        mineTexture.dispose();
        sourceOfMagicTexture.dispose();
        loreTexture.dispose();
        burrowTexture.dispose();
        mountainTexture.dispose();
    }

    @Override
    public Polygon getPolygon() {
        return polygon;
    }

    @Override
    public boolean isMine() {
        return mine;
    }

    @Override
    public boolean isCave() {
        return cave;
    }

    @Override
    public boolean isSourceOfMagic() {
        return sourceOfMagic;
    }

    @Override
    public boolean isBurrow() {
        return burrow;
    }

    @Override
    public void setBurrow(final boolean burrow) {
        this.burrow = burrow;
    }

    @Override
    public boolean isLore() {
        return lore;
    }

    @Override
    public void setLore(final boolean lore) {
        this.lore = lore;
    }

    @Override
    public boolean isFortress() {
        return fortress;
    }

    @Override
    public void setFortress(final boolean fortress) {
        this.fortress = fortress;
    }

    @Override
    public boolean isCamp() {
        return camp;
    }

    @Override
    public void setCamp(final boolean camp) {
        this.camp = camp;
    }

    @Override
    public boolean isDragon() {
        return dragon;
    }

    @Override
    public void setDragon(final boolean dragon) {
        this.dragon = dragon;
    }

    @Override
    public boolean isHero() {
        return hero;
    }

    @Override
    public void setHero(final boolean hero) {
        this.hero = hero;
    }

    @Override
    public Player getImmunity() {
        return immunity;
    }

    @Override
    public void setImmunity(final Player immunity) {
        this.immunity = immunity;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    protected void setTexture(final Texture texture) {
        this.texture = texture;
    }

    @Override
    public void setNeighbours(final List<Field> neighbours) {
        this.neighbours = neighbours;
    }

    @Override
    public List<Field> getNeighbours() {
        return neighbours;
    }

    @Override
    public List<Nation> getArmy() {
        return army;
    }

    @Override
    public NationType getNationType() {
        if(army != null && !army.isEmpty()) {
            return army.get(0).getNationType();
        }
        return null;
    }

    @Override
    public List<Nation> getAttackingArmy() {
        return attackingArmy;
    }

    @Override
    public boolean isValidAttacker(final Player player, final Nation nation, final BoardGameMain game) {
        NationType nationType = player.getActiveNation().getNationType();
        AbilityType abilityType = player.getActiveAbility().getAbilityType();

        //jesli atakujemy juz jakies inne pole to tego nie mozemy
        if(player.firstAttack()) {
            for(Field field : game.getMap().getFields()) {
                if(!field.getAttackingArmy().isEmpty() && !this.equals(field)) {
                    return false;
                }
            }
        }

        // sprawdzamy czy to pierwszy atak dana rasa - jesli tak, nieco inna walidacja
        boolean isFirstAttack = false;
        if(player.firstAttack()) {
            if(neighbours.size() == 6 &&
                    nationType != NationType.HALFLINGS && abilityType != AbilityType.FLYING) {
                //game.setMessageToShow("err_attack3");
                LOGGER.debug("Nie mozna zaatakowac tego pola jako pierwszego.");
                return false;
            } else {
                isFirstAttack = true;
            }
        }

        boolean isNeighbour = false;
        for(Field neighbour : neighbours) {
            if(player.equals(neighbour.getOwner())) {
                isNeighbour = true;
                break;
            }
        }

        // sasiadem jest sie rowniez jesli jaskinie lacza te pola
        if(!isNeighbour && cave && player.getActiveAbility().getAbilityType() == AbilityType.UNDERGROUND) {
            for(Field field : player.getOwnedLands()) {
                if(field.isCave()) {
                    isNeighbour = true;
                    break;
                }
            }
        }

        // jesli nie jest sasiadem oraz nie potrafi latac nie moze atakowac
        if(!isFirstAttack && !isNeighbour
                && player.getActiveAbility().getAbilityType() != AbilityType.FLYING) {
            return false;
        }

        // nietykalnosc wynikajaca z umiejetnosci {@link Diplomatic}
        if(immunity != null && immunity.equals(player)) {
            return false;
        }

        // smok i norka i bohater daja nietykalnosc
        if(dragon || burrow || hero) {
            return false;
        }

        return army.isEmpty() || nation.getNationType() != army.get(0).getNationType();
    }

    /**
     * Dodaje jednostke do listy atakujacych to pole.
     *
     * @param token atakujaca jednostka
     */
    @Override
    public void addAttackingToken(Nation token) {
        attackingArmy.add(token);
    }

    /**
     * Dodaje jednostke do listy stacjonujacych na tym polu. Jednoczenie przypisuje jednostce to pole jako miejsce
     * stacjonowania.
     *
     * @param token atakujaca jednostka
     */
    @Override
    public void addArmyToken(Nation token) {
        army.add(token);
        token.setField(this);
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public void setOwner(final Player owner) {
        this.owner = owner;
    }

    @Override
    public void draw(final Batch batch) {
        batch.draw(texture, polygon.getX(), polygon.getY());
        if(cave) {
            batch.draw(caveTexture, polygon.getX() + 60, polygon.getY() + 75);
        }
        if(mine) {
            batch.draw(mineTexture, polygon.getX() + 35, polygon.getY() + 25);
        }
        if(sourceOfMagic) {
            batch.draw(sourceOfMagicTexture, polygon.getX() + 85, polygon.getY() + 25);
        }
        if(getType() == FieldType.MOUNTAIN) {
            batch.draw(mountainTexture, polygon.getX() + 40, polygon.getY() + 40);
        }
        if(lore) {
            batch.draw(loreTexture, polygon.getX() + 30, polygon.getY() + 30);
        }
        if(burrow) {
            batch.draw(burrowTexture, polygon.getX() + 30, polygon.getY() + 30);
        }
        if(extinctTribe) {
            batch.draw(extinctTribeTexture, polygon.getX() + 50, polygon.getY() + 40);
        }
    }

    @Override
    public void attack(final Player player, final BoardGameMain game) {
        int minArmySize = army.isEmpty() ? (extinctTribe ? 3 : 2) : army.size() + 1;
        int attackingArmySize = attackingArmy.size();

        minArmySize = player.getActiveNation().countAttackPerks(minArmySize, this);
        minArmySize = player.getActiveAbility().countAttackPerks(minArmySize, this);
        minArmySize = countAttackPenalty(minArmySize);

        // minimalna wymagana liczba jednostek to 1
        minArmySize = minArmySize < 1 ? 1 : minArmySize;

        //jesli lista atakujacych jednostek jest pusta jest to pierwszy tak rasa
        if(player.firstAttack() && !firstAttackValidation(player, game)) {
            return;
        }

        LOGGER.debug("attacking army: " + attackingArmySize + ", needed: " + minArmySize);
        if(attackingArmySize >= minArmySize) {
            //jesli wieksza wystarczajaca liczba jednostek to atak
            LOGGER.debug("Zwyciestwo! Podbijamy teren. ");
            successfulAttack(player, game);
        } else {
            //gdy mamy za malo jednostek
            game.setMessageToShow("err_attack2");
            LOGGER.debug("Porazka! Masz za malo jednostek zeby podbic ten teren!");
        }
    }

    /**
     * Obsluga pierwszego ataku nowa rasa. Sprawdza czy mozna zaatakowac to pole jako pierwsze. Dodatkowo liczy rozmiar
     * armii gracza i ustawia ja jako armie atakujaca.
     *
     * @param player atakujacy atakujacy gracz
     *
     * @return true jesli mozna zaatakowac to pole jako pierwszy atak, false wpp.
     */
    private boolean firstAttackValidation(final Player player, final BoardGameMain game) {
        NationType nationType = player.getActiveNation().getNationType();
        AbilityType abilityType = player.getActiveAbility().getAbilityType();
        if(neighbours.size() == 6 &&
                nationType != NationType.HALFLINGS && abilityType != AbilityType.FLYING) {
            LOGGER.debug("Nie mozna zaatakowac tego pola jako pierwszego.");
            return false;
        }


        return true;
    }

    /**
     * Wykonujaca wszystie czynnosci zwiazane z atakiem ktory odniols sukces.
     *
     * @param player atakujacy gracz
     */
    private void successfulAttack(final Player player, final BoardGameMain game) {
        NationType armyNationType = getNationType();

        /* jesli rasa nalezy do gracza i jest aktywna to musimy zadbac o armie wszesniej staconujaca na polu,
         jesli rasa wymierajaca sprawdzamy czy to czasem nie ostatni jej teren, jesli tak to usuwamy ja */
        if(owner != null && armyNationType != null && owner.getActiveNation() != null &&
                armyNationType == owner.getActiveNation().getNationType()) {

            //szukamy pola gracza z nasza rasa
            Field randomField = null;
            for(Field field : owner.getOwnedLands()) {
                if(field.getNationType() == armyNationType) {
                    randomField = field;
                    break;
                }
            }

            // jesli gracz posiada jeszcze pole z ta rasa
            if(randomField != null) {
                // dla kazdego tokenu cofamy go na pole wczesniej wybrane pole
                for(Nation token : army) {
                    token.setField(randomField);
                }

                randomField.getArmy().addAll(army);

                //jesli nie sa to elfy, usuwamy jedna jednostke
                if(owner.getActiveNation().getNationType() != NationType.ELVES) {
                    randomField.getArmy().remove(0);
                }

                //ustawiamy cofnieta armie
                randomField.positionArmy();
            } else {
                player.removeNation(army.get(0));
            }
        } else if(owner != null && armyNationType != null) {
            //sprawdzamy czy gracz posiada jeszcze jakies pole z ta rasa
            boolean lastField = true;
            for(Field field : owner.getOwnedLands()) {
                if(field.getNationType() == armyNationType) {
                    lastField = false;
                    break;
                }
            }

            if(lastField) {
                player.removeNation(army.get(0));
            }
        }

        //teraz armia ktora atakowa tu stacjonuje, a armia atakujaca jest pusta
        army = Collections.synchronizedList(new ArrayList<Nation>());
        for(Nation token : attackingArmy) {
            addArmyToken(token);
        }
        attackingArmy = new ArrayList<>();

        //na koniec ustawiamy armie na polu
        positionArmy();

        // jesli trzeba ustawic tokeny, robimy to
        if(player.getActiveNation().getNationType() == NationType.TROLLS) {
            setLore(true);
        } else if(player.getActiveNation().getNationType() == NationType.HALFLINGS && player.getOwnedLands().size() < 2) {
            setBurrow(true);
        } else {
            if(fortress) {
                setFortress(false);
                game.resetTokens(TokenType.FORTRESS);
            }
            setLore(false);
            setBurrow(false);
        }
        extinctTribe = false;

        //zmieniamy wlasicieli pol
        if(owner != null) {
            owner.removeField(this);
            player.addField(this, false);
        } else {
            player.addField(this, true);
        }
        owner = player;

        //usuwamy tokeny z listy dostepnych w tej turze (juz wykonaly akcje)
        player.getStillAvailableTokens().removeAll(army);
        if(!player.getTokensWithoutField().isEmpty()) {
            player.getTokensWithoutField().removeAll(army);
        }
    }

    @Override
    public void positionArmy() {
        int i = 0;
        for(Nation token : army) {
            token.getArmyPolygon().setPosition(polygon.getX() + 30 + 10*i, polygon.getY() + 20 + 10*i);
            i++;
        }
    }

    /**
     * Random number generator.
     */
    protected String number() {
        Random random = new Random();
        Integer number = random.nextInt(4) + 1;
        return number.toString();
    }

    /**
     * Metoda weryfikuje modyfikatory do ataku wynikajace z typu pola lub tokenow na nim.
     *
     * @param armySize poczatkowy rozmiar armii
     *
     * @return rozmiar armii po doliczeniu kar
     */
    private int countAttackPenalty(int armySize) {
        if(getType() == FieldType.MOUNTAIN) {
            armySize++;
        }
        if(camp) {
            armySize++;
        }
        if(fortress) {
            armySize++;
        }
        if(lore) {
            armySize++;
        }

        return armySize;
    }

    /**
     * Typy dostepnych pol. Stosowane celem latwiejszego porownywania typow.
     */
    public enum FieldType {
        PLAINS, SWAMP, MOUNTAIN, FOREST, LAKE, HILLS;

        /**
         * Wybiera losowy typ pola.
         *
         * @param canBeLake czy wykluczyc jezioro z mozliwosci
         *
         * @return wylosowany type
         */
        public static FieldType getRandomType(final boolean canBeLake) {
            Random random = new Random();
            FieldType randomField = values()[random.nextInt(values().length)];
            if(!canBeLake) {
                while(randomField == LAKE) {
                    randomField = values()[random.nextInt(values().length)];
                }
            }
            return randomField;
        }
    }
}
