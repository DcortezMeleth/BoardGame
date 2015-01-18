package pl.agh.edu.boardgame.adapters;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Polygon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.agh.edu.boardgame.abilities.*;
import pl.agh.edu.boardgame.configuration.Configuration;
import pl.agh.edu.boardgame.core.AbilityNationPair;
import pl.agh.edu.boardgame.core.BoardGameMain;
import pl.agh.edu.boardgame.core.Player;
import pl.agh.edu.boardgame.nations.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter odpowiedzialny za obsluge eventu wybory rasy.
 *
 * @author Bartosz
 */
public class NationChoiceAdapter extends InputAdapter {

    /** Logger. */
    private final static Logger LOGGER = LogManager.getLogger(NationChoiceAdapter.class);

    /** Konfiguracja gry. */
    private final Configuration configuration;

    /** Referencja do glownej klasy gry. */
    private final BoardGameMain game;

    /** Lista ras ktore mozemy wybrac. */
    private final List<AbilityNationPair> nations;

    /** Jeszcze nie wykorzystane umiejetnosci. */
    private final List<Ability> availableAbilities = new ArrayList<>();

    /** Jeszcze nie wykorzystane rasy. */
    private final List<Nation> availableNations = new ArrayList<>();

    public NationChoiceAdapter(final BoardGameMain game, final Configuration configuration) {
        this.configuration = configuration;
        this.game = game;
        this.nations = game.getNationsToChoose();

        // dodajemy wszystskie mozliwe umiejetnosci
        availableAbilities.add(new Alchemical());
        availableAbilities.add(new Brave());
        availableAbilities.add(new Camper());
        //availableAbilities.add(new Diplomatic());
        //availableAbilities.add(new DragonLords());
        availableAbilities.add(new Flying());
        availableAbilities.add(new Forestry());
        availableAbilities.add(new Fortified());
        availableAbilities.add(new Heroic());
        availableAbilities.add(new Hilly());
        availableAbilities.add(new Horseman());
        availableAbilities.add(new Looting());
        availableAbilities.add(new Mad());
        availableAbilities.add(new Merchant());
        availableAbilities.add(new Sailing());
        //availableAbilities.add(new Spiritualist());
        availableAbilities.add(new Steadfast());
        availableAbilities.add(new Swampy());
        availableAbilities.add(new Underground());
        availableAbilities.add(new Wealth());

        //dodajemy wszystkie mozliwe rasy
        availableNations.add(new Amazons());
        availableNations.add(new Dwarves());
        //availableNations.add(new Elves());
        //TODO: odblokowac po dodaniu tekstur!
        availableNations.add(new Ghouls());
        availableNations.add(new Giants());
        availableNations.add(new Halflings());
        availableNations.add(new Humans());
        availableNations.add(new Orcs());
        availableNations.add(new Ratmen());
        availableNations.add(new Skeletons());
        availableNations.add(new Sorcerers());
        availableNations.add(new Tritons());
        availableNations.add(new Trolls());
        //availableNations.add(new Wizards());

        //tworzymy liste 6 par z ktorej beda mogli wybierac gracze
        for(int i = 0; i <= 5; i++) {
            nations.add(randomPair());
        }

        //ustawiamy pozycje sztandarow do wyboru
        refreshBanners();
        LOGGER.debug("Nations to choose list initialized!");
    }

    @Override
    public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
        int y = configuration.getIntProperty(Configuration.APP_HEIGHT) - screenY;
        Player player = game.getActivePlayer();

        if(player.getActiveNation() != null || game.getPhase() == BoardGameMain.TurnPhase.REGROUP) {
            return false;
        }

        boolean chosen = false;
        for(int i = nations.size() - 1; i >= 0; i--) {
            AbilityNationPair pair = nations.get(i);

            // gdy wybralismy juz rase rozkladamy monety na pozostalych, wpp. sprawdzamy czy kliknieto kolejne pary
            if(chosen) {
                pair.addCoin();
            } else if(pair.getNation().getBannerPolygon().contains(screenX, y) ||
                    pair.getAbility().getPolygon().contains(screenX, y)) {

                //jesli tooltip to reszte pomijamy
                if(pair.isTooltip()) {
                    pair.setTooltip(false);
                    game.setPairToTooltip(pair);
                    return true;
                }

                // sprawdzamy czy gracza stac na wybor tej rasy, jesli tak to ja wybieramy
                if(player.spendMoney(i)) {
                    chosen = true;

                    //dodajemy graczowi wybrano rase, a do listy losujemy nastepna jesli jeszcze sa nie wybrane
                    chooseNewNation(nations.remove(i));
                    if(availableNations.size() > 0) {
                        nations.add(randomPair());
                    }

                    //dodajemy zgromadzone na parze monety
                    player.addMoney(pair.getCoins());

                    LOGGER.debug("You have chosen: " + pair.getAbility().getName() + " " + pair.getNation().getName());
                    LOGGER.debug("You have spent " + i + " money for that.");
                    LOGGER.debug("You have earned " + pair.getCoins() + " for this choose.");
                } else {
                    LOGGER.debug("You do not have enough money!");
                    game.setMessageToShow("err_choose_nation");
                    return true;
                }
            }
        }

        //jesli cos wybralismy, uzupelniamy liste
        if(chosen) {
            refreshBanners();
        }

        return chosen;
    }

    private void refreshBanners() {
        int row = 0;
        for(AbilityNationPair pair : nations) {
            Polygon ability = pair.getAbility().getPolygon();
            Polygon nation = pair.getNation().getBannerPolygon();

            ability.setPosition(10, 660 - 130*row);
            nation.setPosition(100, 660 - 130*row);

            pair.setActive(true);

            row++;
        }
    }

    /**
     * Metoda ma na celu wylosowac kombinacje umiejetnosci i rasy z jeszcze dostepnych.
     *
     * @return wylosowana para
     */
    private AbilityNationPair randomPair() {
        int rand = (int) (Math.random()*(availableAbilities.size() - 1));
        int rand2 = (int) (Math.random()*(availableNations.size() - 1));
        return new AbilityNationPair(availableAbilities.remove(rand), availableNations.remove(rand2));
    }

    /**
     * Metoda zwraca uzyta rase do sterty ras do wybrania.
     *
     * @param pair wykorzystana juz rasa
     */
    public void returnNation(final AbilityNationPair pair) {
        availableAbilities.add(pair.getAbility());
        availableNations.add(pair.getNation());
    }

    /** Resetuje tooltipy. */
    public void resetToolTips() {
        for(AbilityNationPair pair : nations) {
            pair.setTooltip(true);
        }
    }

    /** Metoda wybiera nowa rase i poczynia wszystkie zwiazane z tym ustawienia. */
    public void chooseNewNation(final AbilityNationPair pair) {
        Player activePlayer = game.getActivePlayer();
        activePlayer.addAbilityNationPair(pair, game.getPhase());
        pair.setActive(true);
        setArmyPolygons(activePlayer);

        int x, y, x1;
        x = configuration.getIntProperty(Configuration.APP_WIDTH)/2 - 175;
        x1 = x + 90;
        y = 10;

        activePlayer.getActiveAbility().getPolygon().setPosition(x, y);
        activePlayer.getActiveNation().getBannerPolygon().setPosition(x1, y);
    }

    /**
     * Ustawia armie gracza w odpowienim miejscu.
     *
     * @param player gracz ktorego armie trzeba ustawic
     */
    private void setArmyPolygons(final Player player) {
        int i = 0;
        for(Nation token : player.getStillAvailableTokens()) {
            token.getArmyPolygon().setPosition(500 + i*42, 130);
            i++;
        }
    }

    /** Metoda dba o wyczyszczenie tekstur przy zamknieciu aplikacji. */
    public void dispose() {
        for(Nation nation : availableNations) {
            nation.dispose();
        }
        for(Ability ability : availableAbilities) {
            ability.dispose();
        }
    }
}
