package sample.game;
import javafx.scene.image.ImageView;
import sample.board.Field;
import sample.board.MovementManager;
import sample.figures.Figure;

import java.util.List;

/**
 * Rozhranie pre grafické rozhranie, ktoré zahrňa metódy dôležité pre samotné gui
 * ale aj pre riadenie logiky programu.
 */
public interface Game
{
    /**
     * Vynuluje informacie o ťahu v logike ktorý je reprezentovaný triedy MovementManager,
     * a tým pripraví jadro(logiku) na ďalší ťah.
     */
    void nullMovementManager();

    /**
     * Nastavuje informácie o najbližšom ťahu hráča, ktorý sa má vykonať.
     * @param col Stĺpec políčka ktoré sa má do ťahu nastaviť.
     * @param row Riadok políčka ktoré sa má do ťahu nastaviť.
     * @return Úspešnosť metódy.
     */
    boolean setPlayerMovement(int col, int row);

    /**
     * Nastavuje informácie o najbližšom prehrávanom ťahu, ktorý sa má vykonať. Tieto informácie
     * sa vyberú z notácie partie.
     * @return Úspešnosť metódy.
     */
    boolean setPlaybackMovement();

    /**
     * Vykonáva už nastavený ťah hráča v jadre programu.
     * @return Úspešnosť ťahu.
     */
    boolean performPlayerMovement();

    /**
     * Vykonáva už nastavený prehrávaný ťah v jadre programu.
     * @return Úspešnosť ťahu.
     */
    boolean performPlaybackMovement();

    /**
     * Mení v jadre programu hráča, ktorý je na ťahu.
     */
    void changePlayer();

    /**
     * @return Informáciu o tom či je na ťahu biely hráč.
     */
    boolean isWhiteOnTheMove();

    /**
     * Vracia cieľové políčko momentálne nastaveného ťahu.
     * @return Objekt políčka.
     */
    Field getGoalField();

    /**
     * Vracia momentálne nastavený ťah partie.
     * @return Objekt ťahu.
     */
    MovementManager getMovementManager();

    /**
     * Vracia informáciu o tom či je momentálne ťah kompletne nastavený. (Je nastavené
     * štartovacie aj cieľové políčko.)
     * @return Uspešnosť testu.
     */
    boolean isMovementCompletlySet();

    /**
     * Testuje to či sa v momentalne nastaveném ťahu nachádza vyhadzovanie figurky.
     * @return Úspešnosť testu.
     */
    boolean isRemovingFigure();

    /**
     * Testuje to či sa v momentalne nastaveném ťahu nachádza výmena pešiaka za novú figúrku.
     * @return Úspešnosť testu.
     */
    boolean getIsChangingFigure();

    /**
     * Vytvára v jadre programu novú figúrku a položí ju na šachovnicu.
     * @param image Obrázok novej figúrky, ktorý sa uloži ako premenna objektu novej figúrky.
     * @param id Identifikátor typu figúrky, ktorá sa má vytvoriť.
     * @return Úspešnosť vytvorenia.
     */
    boolean createNewFigure(ImageView image, int id);


    /**
     * Vracia obrazok figúrky, ktorá sa v danom ťahu posúva.
     * @return Odkaz na daný obrázok.
     */
    ImageView getImageOfMovFigure();

    /**
     * Vracia obrazok figúrky, ktorá sa na začiatku ťahu nachádza na cieľovom políčku (vyhadzovaná figúrka).
     * @return Odkaz na daný obrázok.
     */
    ImageView getImageOfGoalFieldFigure();

    /**
     * Vracia notáciu šachovej partie, ktorá je uložena v Stringovom ArraListe po riadku.
     * @return ArrayList notácie.
     */
    List<String> getGameNotation();

    /**
     * Vracia riadok poradie riadku notácie, ktorý má byť vyznačený.
     * @return Poradie riadku.
     */
    int getIndexOfGameNotation();

    /**
     * V prípade vlastného ťahu užívateľa pridá nový ťah do notácie partie.
     * Informácie sa získavajú z aktuálne nastaveného ťahu.
     */
    void addPlayerNotationMovement();

    /**
     * Ukladá notáciu do súboru.
     * @return Úspešnosť uloženia.
     */
    boolean saveNotation();

    /**
     * Vráti hodnotu identifikátoru figúrky ktorá nahradila pešiaka.
     * @return ID figúrky.
     */
    int getChangingFigureID();

    /**
     * Inkrementuje index notácie pre výber aktuálneho ťahu.
     */
    void incrementIndexOfNotationLines();

    /**
     * Dekrementuje index notácie pre výber aktuálneho ťahu.
     */
    void decrementIndexOfNotationLines();

    /**
     * Testovanie či je index ťahov notácie na začiatku (je nulový).
     * @return Úspešnosť testu.
     */
    boolean isFirstIndexOfNotation();

    /**
     * Testovanie či je index ťahov notácie na konci (je posledný).
     * @return Úspešnosť testu.
     */
    boolean isLastIndexOfNotation();

    /**
     * Skompletuje informácie o ťahu notácie ak nie sú kompletné.
     * Využívané pri krátkej forme zápisu notácie.
     */
    void completeNotationMovement();

    /**
     * Vykonáva momentálne nastavený ťah v jadre programu a testuje správnosť/povolenosť ťahu.
     * @return Úspešnosť testu.
     */
    boolean performPlaybackUndoMovement();

    /**
     * Nastaví momentálny ťah v jadre programu a testuje správnosť/povolenosť ťahu.
     * @return Úspešnosť testu.
     */
    boolean setPlaybackUndoMovement();

    /**
     * Vracia informáciu o tom či zápis notácie partie správny.
     * @return Úspešnosť testu.
     */
    boolean isNotationRight();

    boolean getIsNotationRight();




}
