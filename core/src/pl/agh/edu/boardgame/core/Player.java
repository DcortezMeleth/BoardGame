package pl.agh.edu.boardgame.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.agh.edu.boardgame.abilities.Ability;
import pl.agh.edu.boardgame.abilities.AbilityType;
import pl.agh.edu.boardgame.configuration.Configuration;
import pl.agh.edu.boardgame.map.fields.Field;
import pl.agh.edu.boardgame.nations.*;
import pl.agh.edu.boardgame.tokens.TokenType;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa gracza. Przechowuje wszystkie najwazniejsze informacje o nim
 *
 * @author Bartosz
 */
public class Player {

    /** Logger. */
    private final static Logger LOGGER = LogManager.getLogger(Player.class);

    /** Nazwa gracza. */
    private final String name;

    /** Poczatkowa ilosc monet. */
    private Integer money = 5;

    /** Lista posiadanych terenow. */
    private List<Field> ownedLands = new ArrayList<>();

    /** Lista landow podbitych w tej turze. */
    private List<Field> justConqueredNotEmptyLands = new ArrayList<>();

    /** Lista ras i umiejetnosci. */
    private List<AbilityNationPair> nations = new ArrayList<>();

    /** Wszystkie posiadane jednostki. */
    private List<Nation> allTokens = new ArrayList<>();

    /** Jednostki ktore mozna jeszcze wykorzystac w tej turze. */
    private List<Nation> stillAvailableTokens = new ArrayList<>();

    /** Lista tokenow nie bedacych jeszcze na zadnym polu. */
    private List<Nation> tokensWithoutField = new ArrayList<>();

    public Player(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Field> getOwnedLands() {
        return ownedLands;
    }

    public List<AbilityNationPair> getNations() {
        return nations;
    }

    public Integer getMoney() {
        return money;
    }

    public List<Nation> getStillAvailableTokens() {
        return stillAvailableTokens;
    }

    public List<Nation> getTokensWithoutField() {
        return tokensWithoutField;
    }

    public List<Nation> getAllTokens() {
        return allTokens;
    }

    /**
     * Ilosc podbitych nie pustych regionow w tej turze.
     *
     * @return liczba regionow
     */
    public int getConqueredNotEmptyLandsSize() {
        return justConqueredNotEmptyLands.size();
    }

    /**
     * Metoda dodaje pole pod posiadanie gracza.
     *
     * @param field pole do dodania
     * @param empty znacznik czy pole bylo puste
     */
    public void addField(final Field field, final boolean empty) {
        if(!empty) {
            justConqueredNotEmptyLands.add(field);
        }
        ownedLands.add(field);
    }

    /**
     * Usuwa pole z posiadanych przez gracza.
     *
     * @param field pole do usuniecia
     */
    public void removeField(final Field field) {
        ownedLands.remove(field);
    }

    /** Czysci list pol podbitych w tej turze. Powinno byc uzywane na koniec tury. */
    public void clearJustConquered() {
        justConqueredNotEmptyLands = new ArrayList<>();
    }

    /** Sprawdza czy aktywna rasa ma przynajmniej jedno podbite pole. */
    public boolean activeRaceFieldConquered() {
        for(Field field : ownedLands) {
            if(field.getNationType() == getActiveNation().getNationType()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Metoda dodajaca graczowi nowa pare rasa - umiejetnosc. Rasa ta jest automatycznie aktywowana i ustawiane sa
     * dostepne wojska.
     *
     * @param pair para rasa-umiejetnosc do ustawienia
     */
    public void addAbilityNationPair(final AbilityNationPair pair) {
        nations.add(pair);
        pair.setActive(true);
        int maxArmy = pair.getAbility().getMaxUnits() + pair.getNation().getMaxUnits();
        for(int i = 0; i < maxArmy; i++) {
            allTokens.add(createNationByType(pair.getNation().getNationType()));
        }
        LOGGER.debug("Army to create: " + maxArmy + ", army created: " + allTokens.size());
        tokensWithoutField = new ArrayList<>(allTokens);
        refreshStillAvailable();

        for(int i = 0; i < nations.size() - 1 && i >= 0; i++) {
            nations.get(i).move(350);
        }
    }

    /** Generuje wiecej tokenow rasy. */
    public void generateMoreTokens(final BoardGameMain.TurnPhase phase) {
        if(phase == BoardGameMain.TurnPhase.REGROUP && getActiveNation().getNationType() == NationType.SKELETONS) {
            int toCreate = justConqueredNotEmptyLands.size() / 2;
            toCreate = toCreate % 2 == 0 ? toCreate : toCreate-1;
            for(int i=0 ; i<toCreate ; i++) {
                Nation nation = createNationByType(NationType.SKELETONS);
                allTokens.add(nation);
                stillAvailableTokens.add(nation);
                tokensWithoutField.add(nation);
                nation.getArmyPolygon().setPosition(500 + i*42, 130);
            }
        }

        if(phase == BoardGameMain.TurnPhase.ATTACK && getActiveNation().getNationType() == NationType.AMAZONS) {
            for(int i=0 ; i<4 ; i++) {
                Nation nation = createNationByType(NationType.AMAZONS);
                allTokens.add(nation);
                stillAvailableTokens.add(nation);
                tokensWithoutField.add(nation);
                nation.getArmyPolygon().setPosition(500 + i*42, 130);
            }
        }
    }

    /** usuwa tokeny amazonek. */
    public void removeNecessaryTokens() {
        if(getActiveNation().getNationType() == NationType.AMAZONS) {
            List<Nation> toRemove = allTokens.subList(0, 4);
            allTokens.removeAll(toRemove);
            stillAvailableTokens.removeAll(toRemove);
            tokensWithoutField.removeAll(toRemove);
        }
    }

    /**
     * Odswieza list teokenow ktorymi mozemy jeszcze ruszyc.
     * Uzywamy na poczatku tury gracza.
     */
    public void refreshStillAvailable() {
        stillAvailableTokens = new ArrayList<>(allTokens);
    }

    /**
     * Zwraca umiejetnosc aktywnej rasy gracza.
     *
     * @return umiejetnosc jesli istnieje aktywna rasa, null wpp.
     */
    public Ability getActiveAbility() {
        if(!nations.isEmpty() && nations.get(nations.size() - 1).isActive()) {
            return nations.get(nations.size() - 1).getAbility();
        }
        return null;
    }

    /**
     * Zwraca aktywna rase gracza.
     *
     * @return rase jesli istnieje aktywna, null wpp.
     */
    public Nation getActiveNation() {
        if(!nations.isEmpty() && nations.get(nations.size() - 1).isActive()) {
            return nations.get(nations.size() - 1).getNation();
        }
        return null;
    }

    /**
     * Deaktywuje obecna rase. Uzywane przy wymieraniu.
     *
     * @param game glowna klasa gry
     */
    public AbilityNationPair deactivateNation(final BoardGameMain game) {

        //usuwamy tokeny umiejetnosci
        switch(getActiveAbility().getAbilityType()) {
            case DRAGON_LORDS:
                game.resetTokens(TokenType.DRAGON);
                break;
            case CAMPER:
                game.resetTokens(TokenType.CAMP);
                break;
            case HEROIC:
                game.resetTokens(TokenType.HERO);
                break;
        }

        int x, y, x1;
        x = game.getConfiguration().getIntProperty(Configuration.APP_WIDTH)/2 - 175;
        x1 = x + 190;
        y = 10;

        getActiveAbility().getPolygon().setPosition(x1, y);
        getActiveNation().getBannerPolygon().setPosition(x, y);

        nations.get(nations.size() - 1).setActive(false);


        for(Nation token : allTokens) {
            token.setActive(false);
        }

        //Ghule wymieraja inaczej!
        NationType nationType = nations.get(nations.size() - 1).getNation().getNationType();
        if(nationType != NationType.GHOULS) {

            allTokens = new ArrayList<>();
            stillAvailableTokens = new ArrayList<>();

            // pozostawiamy po jednym tokenie wymierajacej rasy
            for (Field field : ownedLands) {
                if (nationType == field.getNationType()) {
                    field.getArmy().subList(1, field.getArmy().size()).clear();
                }

                if (nationType == NationType.HALFLINGS) {
                    field.setBurrow(false);
                }
            }
        }

        if(nations.size() == 2 && nations.get(0).getAbility().getAbilityType() != AbilityType.SPIRITUALIST) {
            return removeLastNation();
        }

        return null;
    }

    /**
     * Usuwa rase z listy ras gracza. Uzywane w przypadku podbicia wszystkich regionow rasy. Zaklada ze rasa ta nie
     * posiada juz w posiadaniu zadnego terenu.
     *
     * @param nation rasa do usuniecia
     *
     * @return true jesli udalo sie usunac rase, false wpp.
     */
    public boolean removeNation(final Nation nation) {
        AbilityNationPair pairToRemove = null;
        for(AbilityNationPair pair : nations) {
            if(pair.getNation().getNationType() == nation.getNationType()) {
                pairToRemove = pair;
                break;
            }
        }

        return pairToRemove != null && nations.remove(pairToRemove);
    }

    /**
     * Usuwa wymierajaca rase. Uzywane w przypadku gdy jest taka wiecej niz jedna. Dodatkowo usuwa z pol posiadanych
     * przez gracza pola zajmowane przez ta rase, jesli takie jeszcze sa.
     *
     * @return zwraca usunieta pare
     */
    private AbilityNationPair removeLastNation() {
        NationType type = nations.get(0).getNation().getNationType();
        List<Field> landsToRemove = new ArrayList<>();
        for(Field field : ownedLands) {
            if(field.getNationType() == type) {
                field.setOwner(null);
                field.setLore(false);
                landsToRemove.add(field);
            }
        }
        ownedLands.removeAll(landsToRemove);

        return nations.remove(0);
    }

    /**
     * Dodaje monety do stanu konta gracza.
     *
     * @param amount liczba monet do dodania
     */
    public void addMoney(final Integer amount) {
        money += amount;
    }

    /**
     * Wydawanie monet przez gracza.
     *
     * @param amount Liczba monet jaka chcemy wydac
     *
     * @return true jesli udalo sie nam wydac kwote, false jesli nie stac nas na zakup
     */
    public boolean spendMoney(final Integer amount) {
        if(money >= amount) {
            money -= amount;
            return true;
        }
        return false;
    }


    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                '}';
    }

    /**
     * Mowi o tym czy gracz moze wymrzec w tym momencie. Powinno zwracac false w przypadku gdy gracz wynakal juz jakis
     * ruch w biezacej turze.
     *
     * @return true jesli gracz moze teraz wymrzec, false wpp.
     */
    public boolean isDeactivationPossible() {
        return getActiveNation() != null && tokensWithoutField.isEmpty() &&
                (getActiveAbility().getAbilityType() == AbilityType.STEADFAST ||
                        allTokens.size() == stillAvailableTokens.size());
    }

    /**
     * Metoda sprawdza czy jest to pierwszy atak gracza z wykorzystaniem tej rasy.
     *
     * @return true jesli jest, false wpp.
     */
    public boolean firstAttack() {
        NationType activeNation = getActiveNation().getNationType();
        for(Field field : ownedLands) {
            if(field.getNationType() == activeNation) {
                return false;
            }
        }
        return true;
    }

    /**
     * Meotda zlicza pieniadze na koniec tury.
     */
    public void countIncome() {
        int income = ownedLands.size();

        if(getActiveNation() != null) {
            income = getActiveNation().countIncome(income, this);
            income = getActiveAbility().countIncome(income, this);
        }

        money += income;
        LOGGER.debug("Money: " + money);
    }

    /**
     * Tworzy rase w zaleznosci o typu podanego w argumencie.
     *
     * @param type rodzaj rasy jaka chcemy stworzyc
     *
     * @return utowrzony obiekt rasy
     */
    private Nation createNationByType(final NationType type) {
        switch(type) {
            case AMAZONS:
                return new Amazons();
            case DWARVES:
                return new Dwarves();
            case ELVES:
                return new Elves();
            case GHOULS:
                return new Ghouls();
            case GIANTS:
                return new Giants();
            case HALFLINGS:
                return new Halflings();
            case HUMANS:
                return new Humans();
            case ORCS:
                return new Orcs();
            case RATMEN:
                return new Ratmen();
            case SKELETONS:
                return new Skeletons();
            case SORCERERS:
                return new Sorcerers();
            case TRITONS:
                return new Tritons();
            case TROLLS:
                return new Trolls();
            case WIZARDS:
                return new Wizards();
            default:
                return null;
        }
    }
}
