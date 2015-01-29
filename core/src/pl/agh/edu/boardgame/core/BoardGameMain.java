package pl.agh.edu.boardgame.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.I18NBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.agh.edu.boardgame.abilities.Ability;
import pl.agh.edu.boardgame.adapters.*;
import pl.agh.edu.boardgame.buttons.*;
import pl.agh.edu.boardgame.configuration.Configuration;
import pl.agh.edu.boardgame.map.GameMap;
import pl.agh.edu.boardgame.map.fields.BaseField;
import pl.agh.edu.boardgame.map.fields.Field;
import pl.agh.edu.boardgame.nations.Nation;
import pl.agh.edu.boardgame.tokens.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Główna klasa gry. W niej implementujemy metody wołane w głównej pętli aplikacji.
 *
 * @author Bartosz
 */
public class BoardGameMain implements ApplicationListener {

    /** Logger. */
    private final static Logger LOGGER = LogManager.getLogger(BoardGameMain.class);

    /** Tlo gry. */
    private static Sprite backgroundSprite;

    /** Ilosc tur ile ma trwac gra. */
    private int MAX_TURN;

    /** Numer aktualnej tury. */
    private int turn = 0;

    /** Batch którym rysujemy nasze tekstury. */
    private PolygonSpriteBatch batch;

    /** Kamera okreslajaca co widzimy. */
    private OrthographicCamera camera;

    /** Konfiguracja gry. */
    private final Configuration configuration;

    /** Mapa gry. */
    private GameMap map;

    /** Lista graczy. */
    private List<Player> players = new ArrayList<>();

    /** Lista ras ktore mozna wybrac. Powinna byc zawsze rowna 6. */
    private List<AbilityNationPair> nationsToChoose = new ArrayList<>();

    /** Lista tokenow ktore sami mozemy poruszac. */
    private List<Token> tokens = new ArrayList<>();

    /** Numer gracza, którego tura się odbywa. Liczymy od 0. */
    private int player = 0;

    /** Adapter odpowiedziany za wybor rasy. */
    private NationChoiceAdapter nationChoice = null;

    /** Faza tury gracza. */
    private TurnPhase phase;

    /** Przycisk konca tury. */
    private NextTurnButton nextTurnButton;

    /** Przycisk wymierania. */
    private ExtinctButton extinctButton;

    /** Przycisk ataku. */
    private AttackButton attackButton;

    /** Przycisk pieniedzy. */
    private MoneyButton moneyButton;

    /** Przycisk kostki. */
    private DiceButton diceButton;

    /** Przycisk wyjscia. */
    private ExitButton exitButton;

    /** Czcionka mala. */
    private BitmapFont smallFont;

    /** Czcionka wieksza . */
    private BitmapFont hugeFont;

    /** Napis do wyswietlenia. */
    private String messageToShow = "";

    /** Tekstura monety. */
    private Texture coinTexture;

    /** Paczka tekstow w roznych jezykach. */
    private I18NBundle bundle;

    /** Para do wyswietlania tooltipow. */
    private AbilityNationPair pairToTooltip = null;

    /** Czy jest wyloniony zwyciezca. */
    private boolean winner = false;

    /** Czy uzyto kostki posilkow. */
    private boolean diceUsed = false;

    /**
     * Konstruktor mapy.
     *
     * @param configuration konfiguracja gry
     */
    public BoardGameMain(final Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void create() {
        LOGGER.debug("START");
        map = new GameMap(configuration);
        nextTurnButton = new NextTurnButton(350, 20);
        extinctButton = new ExtinctButton(350, 120);
        attackButton = new AttackButton(350, 220);
        moneyButton = new MoneyButton(450, 20);
        diceButton = new DiceButton(450, 120);
        exitButton = new ExitButton(configuration.getIntProperty(Configuration.APP_WIDTH) - 100,
                configuration.getIntProperty(Configuration.APP_HEIGHT) - 100);

        MAX_TURN = configuration.getIntProperty(Configuration.TURN);

        smallFont = new BitmapFont(Gdx.files.internal("assets/fonts/50px/Impact.fnt"),
                Gdx.files.internal("assets/fonts/50px/Impact.png"), false);
        hugeFont = new BitmapFont(Gdx.files.internal("assets/fonts/100px/Impact.fnt"),
                Gdx.files.internal("assets/fonts/100px/Impact.png"), false);

        bundle = I18NBundle.createBundle(Gdx.files.internal("resources/GameBundle"),
                new Locale(configuration.getProperty(Configuration.LOCALE)));

        coinTexture = new Texture(Gdx.files.internal("assets/Textures/small_parts/moneta.png"));

        initPlayers();
        setTextures();
        initTokens();

        phase = TurnPhase.ATTACK;

        batch = new PolygonSpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, configuration.getIntProperty(Configuration.APP_WIDTH), configuration.getIntProperty
                (Configuration.APP_HEIGHT));

        setInputHandlers();
    }

    @Override
    public void resize(final int width, final int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.begin(); //BEGIN

        //renderujemy tlo jako pierwsze
        renderBackground();

        //renderujemy sztandary ras i umjejetnosci
        renderBanners();

        //renderujemy tokeny armii
        renderArmies();

        //renderujemy tokeny
        renderTokens();

        smallFont.draw(batch, getActivePlayer().getMoney().toString(), 470, 80);

        batch.draw(extinctButton.getTexture(getActivePlayer(), this), extinctButton.getX(), extinctButton.getY());
        batch.draw(attackButton.getTexture(phase), attackButton.getX(), attackButton.getY());
        batch.draw(diceButton.getTexture(map), diceButton.getX(), diceButton.getY());
        batch.draw(nextTurnButton.getTexture(phase), nextTurnButton.getPolygon().getX(), nextTurnButton.getPolygon().getY());
        batch.draw(exitButton.getTexture(), exitButton.getPolygon().getX(), exitButton.getPolygon().getY());
        if(moneyButton.isActive()) {
            batch.draw(moneyButton.getTexture(), moneyButton.getPolygon().getX(), moneyButton.getPolygon().getY());
        }

        //renderowanie wiadomosci na samym szczycie stosu
        renderMessages();

        batch.end(); //END
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        nextTurnButton.dispose();
        extinctButton.dispose();
        attackButton.dispose();
        diceButton.dispose();
        moneyButton.dispose();
        exitButton.dispose();
        for(Player player1 : players) {
            for(AbilityNationPair pair : player1.getNations()) {
                pair.dispose();
            }
        }
        for(Field field : map.getFields()) {
            field.getTexture().dispose();
        }
        nationChoice.dispose();
        coinTexture.dispose();
        BaseField.dispose();
    }

    /** Renderuje wiadomosci i tooltipy. */
    private void renderMessages() {
        float fontX, fontY;
        if(isMessageSet()) {
            fontX = (configuration.getIntProperty(Configuration.APP_WIDTH)
                    - hugeFont.getWrappedBounds(messageToShow, 1600).width)/2;
            fontY = (configuration.getIntProperty(Configuration.APP_HEIGHT)
                    + hugeFont.getBounds(messageToShow).height)/2 + 100;
            if(fontX > 160) {
                hugeFont.drawWrapped(batch, messageToShow, fontX, fontY, 1600);
            } else {
                hugeFont.drawWrapped(batch, messageToShow, fontX, fontY, 1600, BitmapFont.HAlignment.CENTER);
            }
        }

        fontX = (configuration.getIntProperty(Configuration.APP_WIDTH)
                - hugeFont.getBounds(getActivePlayer().getName()).width)/2;
        fontY = configuration.getIntProperty(Configuration.APP_HEIGHT)
                - hugeFont.getBounds(getActivePlayer().getName()).height + 40;
        hugeFont.draw(batch, getActivePlayer().getName(), fontX, fontY);

        if(pairToTooltip != null) {
            String abilityName = bundle.format(pairToTooltip.getAbility().getName());
            String abilityDesc = bundle.format(pairToTooltip.getAbility().getDescription());
            String nationName = bundle.format(pairToTooltip.getNation().getName());
            String nationDesc = bundle.format(pairToTooltip.getNation().getDescription());

            fontX = (configuration.getIntProperty(Configuration.APP_WIDTH)
                    - hugeFont.getBounds(abilityName).width)/2;
            fontY = configuration.getIntProperty(Configuration.APP_HEIGHT)
                    - hugeFont.getBounds(abilityName).height - 150;
            hugeFont.draw(batch, abilityName, fontX, fontY);

            fontX = (configuration.getIntProperty(Configuration.APP_WIDTH)
                    - smallFont.getWrappedBounds(abilityDesc, 1400).width)/2;
            fontY -= smallFont.getBounds(abilityDesc).height + 100;
            smallFont.drawWrapped(batch, abilityDesc, fontX, fontY, 1400, BitmapFont.HAlignment.CENTER);

            fontX = (configuration.getIntProperty(Configuration.APP_WIDTH)
                    - hugeFont.getBounds(nationName).width)/2;
            fontY -= hugeFont.getBounds(nationName).height + 150;
            hugeFont.draw(batch, nationName, fontX, fontY);

            fontX = (configuration.getIntProperty(Configuration.APP_WIDTH)
                    - smallFont.getWrappedBounds(nationDesc, 1400).width)/2;
            fontY -= smallFont.getBounds(nationDesc).height + 100;
            smallFont.drawWrapped(batch, nationDesc, fontX, fontY, 1400, BitmapFont.HAlignment.CENTER);
        }
    }

    /** Metoda renderujaca sztandary. Zarowno te graczy jak i te do wyboru. */
    private void renderBanners() {
        for(AbilityNationPair pair : nationsToChoose) {
            Nation nation = pair.getNation();
            Ability ability = pair.getAbility();
            batch.draw(nation.getBannerTexture(), nation.getBannerPolygon().getX(), nation.getBannerPolygon().getY());
            batch.draw(ability.getTexture(), ability.getPolygon().getX(), ability.getPolygon().getY());
            for(int i = 0; i < pair.getCoins(); i++) {
                batch.draw(coinTexture, ability.getPolygon().getX() + 20 + 20*i,
                        ability.getPolygon().getY() + 20);
            }
        }

        for(AbilityNationPair pair : getActivePlayer().getNations()) {
            Nation nation = pair.getNation();
            Ability ability = pair.getAbility();
            batch.draw(ability.getTexture(), ability.getPolygon().getX(), ability.getPolygon().getY());
            batch.draw(nation.getBannerTexture(), nation.getBannerPolygon().getX(), nation.getBannerPolygon().getY());
        }

        int i = 0;
        for(Player player1 : nextPlayers()) {
            if(player1.getActiveNation() != null) {
                Nation nation = player1.getActiveNation();
                Ability ability = player1.getActiveAbility();
                batch.draw(ability.getTexture(), 1570, 270 - 130*i);
                batch.draw(nation.getBannerTexture(), 1660, 270 - 130*i);
            } else if(!player1.getNations().isEmpty()) {
                Nation nation = player1.getNations().get(player1.getNations().size() - 1).getNation();
                Ability ability = player1.getNations().get(player1.getNations().size() - 1).getAbility();
                batch.draw(ability.getTexture(), 1760, 270 - 130*i);
                batch.draw(nation.getBannerTexture(), 1570, 270 - 130*i);
            }
            i++;
        }
    }

    /** Renderowanie tokenow armii. */
    private void renderArmies() {
        for(Field field : map.getFields()) {
            for(Nation token : field.getArmy()) {
                batch.draw(token.getArmyTexture(), token.getArmyPolygon().getX(), token.getArmyPolygon().getY());
            }
        }
        for(Player player1 : players) {
            for(Nation token : player1.getTokensWithoutField()) {
                batch.draw(token.getArmyTexture(), token.getArmyPolygon().getX(), token.getArmyPolygon().getY());
            }
        }
    }

    /** Renderowanie tokenow. */
    private void renderTokens() {
        for(Token token : tokens) {
            batch.draw(token.getTexture(), token.getX(), token.getY());
        }
    }

    /** Ustawia lancuch obslugi eventow. */
    private void setInputHandlers() {
        nationChoice = new NationChoiceAdapter(this, configuration);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new MessageHideAdapter(this));
        multiplexer.addProcessor(new ExitButtonAdapter(configuration, exitButton));
        multiplexer.addProcessor(new AttackDragAdapter(this, configuration));
        multiplexer.addProcessor(new RegroupDragAdapter(this, configuration));
        multiplexer.addProcessor(new TokenDragAdapter(this, configuration));
        multiplexer.addProcessor(new NextTurnButtonAdapter(this, configuration, nextTurnButton));
        multiplexer.addProcessor(new ExtinctButtonAdapter(this, configuration, extinctButton));
        multiplexer.addProcessor(new AttackButtonAdapter(this, configuration, attackButton));
        multiplexer.addProcessor(new DiceButtonAdapter(this, configuration, diceButton));
        multiplexer.addProcessor(new MoneyButtonAdapter(configuration, moneyButton));
        multiplexer.addProcessor(nationChoice);
        Gdx.input.setInputProcessor(multiplexer);
    }

    /** Metoda ustawiajaca stale tekstury. */
    private void setTextures() {
        backgroundSprite = new Sprite(new Texture(Gdx.files.internal("assets/Textures/desktop/wooden1.jpg")));
    }

    /** Metoda renderujaca tlo i mape. */
    private void renderBackground() {
        backgroundSprite.draw(batch);

        for(Field field : map.getFields()) {
            field.draw(batch);
        }
    }

    /** Metoda inicjujaca graczy. */
    private void initPlayers() {
        players.add(new Player(configuration.getProperty(Configuration.PLAYER_1)));
        players.add(new Player(configuration.getProperty(Configuration.PLAYER_2)));
        players.add(new Player(configuration.getProperty(Configuration.PLAYER_3)));
        players.add(new Player(configuration.getProperty(Configuration.PLAYER_4)));
    }

    /** Metoda inicjujaca tokeny. */
    private void initTokens() {
        // 2 bohaterow
        tokens.add(new Hero());
        tokens.add(new Hero());

        // 1 smok
        tokens.add(new Dragon());

        // 6 fortec
        tokens.add(new Fortress());
        tokens.add(new Fortress());
        tokens.add(new Fortress());
        tokens.add(new Fortress());
        tokens.add(new Fortress());
        tokens.add(new Fortress());

        // 5 obozow
        tokens.add(new Camp());
        tokens.add(new Camp());
        tokens.add(new Camp());
        tokens.add(new Camp());
        tokens.add(new Camp());

        for(Token token : tokens) {
            token.resetPosition();
        }
    }

    /** Mechanizm przechodzenia do kolejnej tury. */
    public void nextTurn() {

        //jesli byl atak to teraz przegrupowanie
        if(phase == TurnPhase.ATTACK) {
            LOGGER.debug("FAZA PRZGRUPOWANIA!");
            phase = TurnPhase.REGROUP;

            //w fazie przegrupowania usuwamy 4 tokeny amazonek
            getActivePlayer().removeNecessaryTokens();

            return;
        }

        //gracz przegrupowal wojska - kolejny gracz
        phase = TurnPhase.ATTACK;
        getActivePlayer().clearJustConquered();

        //podliczamy pieniadze
        getActivePlayer().countIncome();

        nationChoice.resetToolTips();

        // jesli osttani gracz to nowa tura
        if(player == 3) {
            player = 0;
            turn++;
            LOGGER.debug("Koniec tury: " + turn);
        } else {
            player++;
            LOGGER.debug("Kolejny gracz: " + (player + 1));
        }

        //niektore rasy generuja dodatkowe tokeny
        getActivePlayer().generateMoreTokens(phase);

        //gracz znowu moze uzyc wszystkich tokenow
        getActivePlayer().refreshStillAvailable();

        //czyscimy atakujace armie
        map.resetAttackingArmies();

        //resetujemy uzywalnosci tokenow
        for(Token token : tokens) {
            token.resetUsage();
        }

        //resetujemy uzycie kosci posilkow
        diceButton.reset();

        //jesli ostatnia tura to konczymy gre
        if(turn >= MAX_TURN) {
            winner();
            LOGGER.debug("Koniec gry");
            for(Player player1 : players) {
                LOGGER.debug(player1.getName() + " z liczba zlota: " + player1.getMoney() + " i " + player1.getOwnedLands().size() + " posiadanych ziemi ");
            }
        }
    }

    /** Wylania zwyciezce. */
    private void winner() {
        String result = "";
        List<Player> winners = new ArrayList<>();
        winners.add(players.get(0));
        for(Player player1 : players) {
            if(player1.getMoney() > winners.get(0).getMoney()) {
                winners.clear();
                winners.add(player1);
            } else if(player1.getMoney().intValue() == winners.get(0).getMoney().intValue() &&
                    !winners.contains(player1)) {
                winners.add(player1);
            }
        }
        for(Player player1 : winners) {
            setMessageToShow("winner", player1.getName());
            winner = true;
            result += player1.getName() + " z liczba zlota: " + player1.getMoney() + " i " + player1.getOwnedLands().size() + " posiadanych ziemi";
            LOGGER.debug(result);
        }
    }

    /** Zwraca nastepnych graczy w odpowiedniej kolejnosci. */
    private List<Player> nextPlayers() {
        List<Player> result = new ArrayList<>();
        int nextPlayer = player;
        for(int i = 0; i < 3; i++) {
            nextPlayer = (nextPlayer == 3) ? 0 : nextPlayer + 1;
            result.add(players.get(nextPlayer));
        }
        return result;
    }

    /** Metoda zwraca aktywnego gracza. */
    public Player getActivePlayer() {
        return players.get(player);
    }

    /** Metoda zwraca numer aktywnego gracza. */
    public int getPlayerNumber() {
        return player;
    }

    /** Metoda zwraca mape gry. */
    public GameMap getMap() {
        return map;
    }

    /** Zwraca adapter odpowedzialny za wybor rasy. */
    public NationChoiceAdapter getNationChoice() {
        return nationChoice;
    }

    /** Zwraca list ras ktore mozemy wybrac . */
    public List<AbilityNationPair> getNationsToChoose() {
        return nationsToChoose;
    }

    /** Czy jest juz zwyciezca. */
    public boolean isWinner() {
        return winner;
    }

    /** Zwraca liste tokenow do uzytku graczy. */
    public List<Token> getTokens() {
        return tokens;
    }

    /** Zwraca faze gry. */
    public TurnPhase getPhase() {
        return phase;
    }

    /** Zwraca konfiguracje gry. */
    public Configuration getConfiguration() {
        return configuration;
    }

    /** Zwraca pare ktorej tooltipy wyswietlamy. */
    public AbilityNationPair getPairToTooltip() {
        return pairToTooltip;
    }

    /** Ustawia pare do wyswietlania tooltipow. */
    public void setPairToTooltip(AbilityNationPair pairToTooltip) {
        this.pairToTooltip = pairToTooltip;
    }

    /** Sprawdza czy uzyto kostki posilkow. */
    public boolean isDiceUsed() {
        return diceUsed;
    }

    /** Ustawia uzycie kostki. */
    public void setDiceUsed(final boolean diceUsed) {
        this.diceUsed = diceUsed;
    }

    /**
     * Pozwala wyswietlic wiadomosc na ekranie. Aby przestac wyswietlac uzywamy tej metody przekazujac null lub "" jako
     * parametr.
     *
     * @param key  klucz z bundla napis ktory chcemy wyswietlic
     * @param args argumenty do umieszczenia w wiadomosci
     */
    public void setMessageToShow(final String key, final Object... args) {
        if(key != null && !"".equals(key)) {
            this.messageToShow += " \n" + bundle.format(key, args);
        } else {
            this.messageToShow = "";
        }
    }

    /** Sprawdza czy jest wyswietlana wiadomosc. */
    public boolean isMessageSet() {
        return messageToShow != null && !"".equals(messageToShow);
    }

    /**
     * Resetuje dany typ tokenow.
     *
     * @param tokenType typ tokenu
     */
    public void resetTokens(TokenType tokenType) {
        for(Token token : tokens) {
            if(token.getType() == tokenType) {
                token.resetPosition();
            }
        }
    }

    /** Enum faz tury. */
    public enum TurnPhase {
        REGROUP, ATTACK
    }
}
