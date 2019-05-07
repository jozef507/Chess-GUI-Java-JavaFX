package sample.game;
import javafx.scene.image.ImageView;
import sample.figures.*;
import sample.board.*;
import sample.notation.Notation;
import sample.gui.GuiGameFactory;


import java.util.ArrayList;
import java.util.List;

/**
 * Trieda reprezentujúca celé jadro/logiku jedenej hry.
 * Obsahuje inštancie tried potrebných pre správu celej hry: board, movementManager,
 * figuresManager, notation.
 */
public class ChessGame implements Game
{
    private Board board;
    private MovementManager movementManager;
    private FiguresManager figuresManager;
    private Notation notation;


    /**
     * Inicializuje partiu hry.
     * @param whiteFigureImages Pole odkzaov na obrázky bielých figúrok.
     * @param blackFigureImages Pole odkzaov na obrázky čiernych figúrok.
     */
    public ChessGame(ImageView[] whiteFigureImages, ImageView[] blackFigureImages) {
        this.board = new Board(8);
        this.movementManager = new MovementManager();
        this.figuresManager = new FiguresManager();
        this.notation = new Notation(GuiGameFactory.stringFilepathArray[GuiGameFactory.counter++]);

        putPawnOnBoard(1, 2, true, whiteFigureImages[0]);
        putPawnOnBoard(2, 2, true, whiteFigureImages[1]);
        putPawnOnBoard(3, 2, true, whiteFigureImages[2]);
        putPawnOnBoard(4, 2, true, whiteFigureImages[3]);
        putPawnOnBoard(5, 2, true, whiteFigureImages[4]);
        putPawnOnBoard(6, 2, true, whiteFigureImages[5]);
        putPawnOnBoard(7, 2, true, whiteFigureImages[6]);
        putPawnOnBoard(8, 2, true, whiteFigureImages[7]);
        putRookOnBoard(1, 1, true, whiteFigureImages[8]);
        putRookOnBoard(8, 1, true, whiteFigureImages[9]);
        putKnightOnBoard(2, 1, true, whiteFigureImages[10]);
        putKnightOnBoard(7, 1, true, whiteFigureImages[11]);
        putBishopOnBoard(3, 1, true, whiteFigureImages[12]);
        putBishopOnBoard(6, 1, true, whiteFigureImages[13]);
        putQueenOnBoard(4, 1, true, whiteFigureImages[14]);
        putKingOnBoard(5, 1, true, whiteFigureImages[15]);

        putPawnOnBoard(1, 7, false, blackFigureImages[0]);
        putPawnOnBoard(2, 7, false, blackFigureImages[1]);
        putPawnOnBoard(3, 7, false, blackFigureImages[2]);
        putPawnOnBoard(4, 7, false, blackFigureImages[3]);
        putPawnOnBoard(5, 7, false, blackFigureImages[4]);
        putPawnOnBoard(6, 7, false, blackFigureImages[5]);
        putPawnOnBoard(7, 7, false, blackFigureImages[6]);
        putPawnOnBoard(8, 7, false, blackFigureImages[7]);
        putRookOnBoard(1, 8, false, blackFigureImages[8]);
        putRookOnBoard(8, 8, false, blackFigureImages[9]);
        putKnightOnBoard(2, 8, false, blackFigureImages[10]);
        putKnightOnBoard(7, 8, false, blackFigureImages[11]);
        putBishopOnBoard(3, 8, false, blackFigureImages[12]);
        putBishopOnBoard(6, 8, false, blackFigureImages[13]);
        putQueenOnBoard(4, 8, false, blackFigureImages[14]);
        putKingOnBoard(5, 8, false, blackFigureImages[15]);

        this.figuresManager.setWhiteFieldsInDanger();
        this.figuresManager.setBlackFieldsInDanger();
    }

    /**
     * Vytvorí novú figúrku - pešiaka, vloží ho na šachovnicu a pridá do aktívnych figúrok.
     * @param col Stlpec políčka kam sa má figúrka vložiť.
     * @param row Riadok políčka kam sa má figúrka vložiť.
     * @param isPawnWhite Informácia o farbe figúrky.
     * @param image Odkaz na obrázok figúrky z grafického rozhrania.
     */
    private void putPawnOnBoard(int col, int row, boolean isPawnWhite, ImageView image) {
        Field field = this.board.getField(col, row);
        if (field.isEmpty()) {
            Pawn pawn = new Pawn(isPawnWhite, image);
            field.put(pawn);
            this.figuresManager.addActiveFigure(pawn);
        }
    }

    /**
     * Vytvorí novú figúrku - pešiaka, vloží ho na šachovnicu a pridá do aktívnych figúrok.
     * @param col Stlpec políčka kam sa má figúrka vložiť.
     * @param row Riadok políčka kam sa má figúrka vložiť.
     * @param isRookWhite Informácia o farbe figúrky.
     * @param image Odkaz na obrázok figúrky z grafického rozhrania.
     */
    private void putRookOnBoard(int col, int row, boolean isRookWhite, ImageView image) {
        Field field = this.board.getField(col, row);
        if (field.isEmpty()) {
            Rook rook = new Rook(isRookWhite, image);
            field.put(rook);
            this.figuresManager.addActiveFigure(rook);
        }
    }

    /**
     * Vytvorí novú figúrku - pešiaka, vloží ho na šachovnicu a pridá do aktívnych figúrok.
     * @param col Stlpec políčka kam sa má figúrka vložiť.
     * @param row Riadok políčka kam sa má figúrka vložiť.
     * @param isRookWhite Informácia o farbe figúrky.
     * @param image Odkaz na obrázok figúrky z grafického rozhrania.
     */
    private void putKnightOnBoard(int col, int row, boolean isRookWhite, ImageView image) {
        Field field = this.board.getField(col, row);
        if (field.isEmpty()) {
            Knight knight = new Knight(isRookWhite, image);
            field.put(knight);
            this.figuresManager.addActiveFigure(knight);
        }
    }

    /**
     * Vytvorí novú figúrku - pešiaka, vloží ho na šachovnicu a pridá do aktívnych figúrok.
     * @param col Stlpec políčka kam sa má figúrka vložiť.
     * @param row Riadok políčka kam sa má figúrka vložiť.
     * @param isRookWhite Informácia o farbe figúrky.
     * @param image Odkaz na obrázok figúrky z grafického rozhrania.
     */
    private void putBishopOnBoard(int col, int row, boolean isRookWhite, ImageView image) {
        Field field = this.board.getField(col, row);
        if (field.isEmpty()) {
            Bishop bishop = new Bishop(isRookWhite, image);
            field.put(bishop);
            this.figuresManager.addActiveFigure(bishop);
        }
    }

    /**
     * Vytvorí novú figúrku - pešiaka, vloží ho na šachovnicu a pridá do aktívnych figúrok.
     * @param col Stlpec políčka kam sa má figúrka vložiť.
     * @param row Riadok políčka kam sa má figúrka vložiť.
     * @param isRookWhite Informácia o farbe figúrky.
     * @param image Odkaz na obrázok figúrky z grafického rozhrania.
     */
    private void putQueenOnBoard(int col, int row, boolean isRookWhite, ImageView image) {
        Field field = this.board.getField(col, row);
        if (field.isEmpty()) {
            Queen queen = new Queen(isRookWhite, image);
            field.put(queen);
            this.figuresManager.addActiveFigure(queen);
        }
    }

    /**
     * Vytvorí novú figúrku - pešiaka, vloží ho na šachovnicu a pridá do aktívnych figúrok.
     * @param col Stlpec políčka kam sa má figúrka vložiť.
     * @param row Riadok políčka kam sa má figúrka vložiť.
     * @param isRookWhite Informácia o farbe figúrky.
     * @param image Odkaz na obrázok figúrky z grafického rozhrania.
     */
    private void putKingOnBoard(int col, int row, boolean isRookWhite, ImageView image) {
        Field field = this.board.getField(col, row);
        if (field.isEmpty()) {
            King king = new King(isRookWhite, image);
            field.put(king);
            this.figuresManager.addActiveFigure(king);
            this.figuresManager.setKing(king);
        }
    }

    /**
     * Vracia cieľové políčko momentálne nastaveného ťahu.
     * @return Objekt políčka.
     */
    public Field getGoalField() { return this.movementManager.getGoalField(); }

    /**
     * Mení v jadre programu hráča, ktorý je na ťahu.
     */
    public void changePlayer() { this.movementManager.changePlayer(); }

    /**
     * Nastaví informáciu o tom či môže užívateľ vykonávať vlastné ťahy.
     * @param canPlayerPlay Hodnota informácie.
     */
    public void setCanPlayerPlay(boolean canPlayerPlay) { this.movementManager.setCanPlayerPlay(canPlayerPlay); }

    /**
     * Vynuluje informacie o ťahu v logike ktorý je reprezentovaný triedy MovementManager,
     * a tým pripraví jadro(logiku) na ďalší ťah.
     */
    public void nullMovementManager() { this.movementManager.nullMovementManager(); }

    /**
     * Nastavuje informácie o najbližšom ťahu hráča, ktorý sa má vykonať.
     * @param col Stĺpec políčka ktoré sa má do ťahu nastaviť.
     * @param row Riadok políčka ktoré sa má do ťahu nastaviť.
     * @return Úspešnosť metódy.
     */
    public boolean setPlayerMovement(int col, int row) { return this.movementManager.setPlayerMovement(col, row, this.board); }

    /**
     * Nastavuje informácie o najbližšom prehrávanom ťahu, ktorý sa má vykonať. Tieto informácie
     * sa vyberú z notácie partie.
     * @return Úspešnosť metódy.
     */
    public boolean setPlaybackMovement(){return this.movementManager.setPlaybackMovement(this.board, this.notation, this.figuresManager);}

    /**
     * Vykonáva už nastavený ťah hráča v jadre programu.
     * @return Úspešnosť ťahu.
     */
    public boolean performPlayerMovement() {return this.movementManager.performMovement(this.figuresManager);}

    /**
     * Vykonáva už nastavený prehrávaný ťah v jadre programu.
     * @return Úspešnosť ťahu.
     */
    public boolean performPlaybackMovement()
    {
        boolean flag = this.movementManager.performMovement(this.figuresManager);
        if (!flag)
            return false;

        if(this.movementManager.getIsRemovingFigure() != this.notation.getActualNotMov().getIsFigureRemoving())
            return false;

        if((this.movementManager.getIsChangingFigure()))
        {
            if(this.notation.getActualNotMov().getChangingFigureID() == -1)
                return false;
        }
        else
        {
            if(this.notation.getActualNotMov().getChangingFigureID() != -1)
                return false;
        }

        if(this.figuresManager.getChessMat() != this.notation.getActualNotMov().getIsChessMat())
            if(this.figuresManager.getChess() != this.notation.getActualNotMov().getIsChess())
                return false;

        return true;
    }

    /**
     * Nastaví momentálny ťah v jadre programu a testuje správnosť/povolenosť ťahu.
     * @return Úspešnosť testu.
     */
    public boolean setPlaybackUndoMovement(){return this.movementManager.setPlaybackUndoMovement(this.notation.getPrevNotationMovement(), this.board, this.figuresManager);}

    /**
     * Vykonáva momentálne nastavený ťah v jadre programu a testuje správnosť/povolenosť ťahu.
     * @return Úspešnosť testu.
     */
    public boolean performPlaybackUndoMovement(){return this.movementManager.performPlaybackUndoMovement(this.notation.getPrevNotationMovement(), this.figuresManager);}

    /**
     * @return Informáciu o tom či je na ťahu biely hráč.
     */
    public boolean isWhiteOnTheMove() {return this.movementManager.isWhiteOnTheMove(); }

    /**
     * Vracia obrazok figúrky, ktorá sa v danom ťahu posúva.
     * @return Odkaz na daný obrázok.
     */
    public ImageView getImageOfMovFigure() { return this.movementManager.getMovementFigure().getImage();}

    /**
     * Vracia obrazok figúrky, ktorá sa na začiatku ťahu nachádza na cieľovom políčku (vyhadzovaná figúrka).
     * @return Odkaz na daný obrázok.
     */
    public ImageView getImageOfGoalFieldFigure() { return this.movementManager.getGoalFieldFigure().getImage();}

    /**
     * Vracia notáciu šachovej partie, ktorá je uložena v Stringovom ArraListe po riadku.
     * @return ArrayList notácie.
     */
    public List<String> getGameNotation(){ return this.notation.getGameNotationLines(); }

    /**
     * Vracia riadok poradie riadku notácie, ktorý má byť vyznačený.
     * @return Poradie riadku.
     */
    public int getIndexOfGameNotation(){return this.notation.getIndexProcNotMov();}

    /**
     * Vracia informáciu o tom či je momentálne ťah kompletne nastavený. (Je nastavené
     * štartovacie aj cieľové políčko.)
     * @return Uspešnosť testu.
     */
    public boolean isMovementCompletlySet() {return this.movementManager.isMovementCompletlySet();}

    /**
     * Testuje to či sa v momentalne nastaveném ťahu nachádza vyhadzovanie figurky.
     * @return Úspešnosť testu.
     */
    public boolean isRemovingFigure() {return this.movementManager.getIsRemovingFigure();}

    /**
     * Vracia informáciu o tom či v momentálnom ťahu nastal šach.
     * @return Hodnota informácie.
     */
    public boolean getChess() {return this.figuresManager.getChess();}

    /**
     * Vracia informáciu o tom či v momentálnom ťahu nastal šachmat.
     * @return Hodnota informácie.
     */
    public boolean getChessMat() {return this.figuresManager.getChessMat();}

    /**
     * Testuje to či sa v momentalne nastaveném ťahu nachádza výmena pešiaka za novú figúrku.
     * @return Úspešnosť testu.
     */
    public boolean getIsChangingFigure () { return this.movementManager.getIsChangingFigure();}

    /**
     * Vytvára v jadre programu novú figúrku a položí ju na šachovnicu.
     * @param image Obrázok novej figúrky, ktorý sa uloži ako premenna objektu novej figúrky.
     * @param id Identifikátor typu figúrky, ktorá sa má vytvoriť.
     * @return Úspešnosť vytvorenia.
     */
    public boolean createNewFigure(ImageView image, int id)
    {
        switch (id)
        {
            case 4:
                setChangedFigureField();
                putRookOnBoard(this.movementManager.getGoalField().getColPos(), this.movementManager.getGoalField().getRowPos(),
                        this.movementManager.isWhiteOnTheMove(), image);
                return this.figuresManager.updateFigures(this.movementManager.isWhiteOnTheMove(), this.movementManager.getGoalField().get(),
                        null);
            case 3:
                setChangedFigureField();
                putKnightOnBoard(this.movementManager.getGoalField().getColPos(), this.movementManager.getGoalField().getRowPos(),
                        this.movementManager.isWhiteOnTheMove(), image);
                return this.figuresManager.updateFigures(this.movementManager.isWhiteOnTheMove(), this.movementManager.getGoalField().get(),
                        null);
            case 2:
                setChangedFigureField();
                putBishopOnBoard(this.movementManager.getGoalField().getColPos(), this.movementManager.getGoalField().getRowPos(),
                        this.movementManager.isWhiteOnTheMove(), image);
                return this.figuresManager.updateFigures(this.movementManager.isWhiteOnTheMove(), this.movementManager.getGoalField().get(),
                        null);
            case 1:
                setChangedFigureField();
                putQueenOnBoard(this.movementManager.getGoalField().getColPos(), this.movementManager.getGoalField().getRowPos(),
                        this.movementManager.isWhiteOnTheMove(), image);
                return this.figuresManager.updateFigures(this.movementManager.isWhiteOnTheMove(), this.movementManager.getGoalField().get(),
                        null);
            default:
                return false;
        }
    }

    /**
     * Zamení pešiaka za náhradnú figúrku.
     */
    private void setChangedFigureField()
    {
        Field field = this.movementManager.getGoalField();
        Figure changedFigure = field.get();
        field.remove(changedFigure);
        this.figuresManager.addChangedFigure(changedFigure);
    }

    /**
     * V prípade vlastného ťahu užívateľa pridá nový ťah do notácie partie.
     * Informácie sa získavajú z aktuálne nastaveného ťahu.
     */
    public void addPlayerNotationMovement(){this.notation.addPlayerNotationMovement(this.figuresManager, this.movementManager);}

    /**
     * Ukladá notáciu do súboru.
     * @return Úspešnosť uloženia.
     */
    public boolean saveNotation() {return this.notation.saveNotation();}

    /**
     * Vráti hodnotu identifikátoru figúrky ktorá nahradila pešiaka.
     * @return ID figúrky.
     */
    public int getChangingFigureID(){return this.notation.getChangingFigureID();}

    /**
     * Inkrementuje index notácie pre výber aktuálneho ťahu.
     */
    public void incrementIndexOfNotationLines()
    {
        this.notation.incrementIndexOfNotationLines();
    }

    /**
     * Dekrementuje index notácie pre výber aktuálneho ťahu.
     */
    public void decrementIndexOfNotationLines(){this.notation.decrementIndexOfNotationLines();}

    /**
     * Testovanie či je index ťahov notácie na začiatku (je nulový).
     * @return Úspešnosť testu.
     */
    public boolean isFirstIndexOfNotation() {return this.notation.isFirstIndex();}

    /**
     * Testovanie či je index ťahov notácie na konci (je posledný).
     * @return Úspešnosť testu.
     */
    public boolean isLastIndexOfNotation()
    {
        return this.notation.isLastIndex();
    }

    /**
     * Skompletuje informácie o ťahu notácie ak nie sú kompletné.
     * Využívané pri krátkej forme zápisu notácie.
     */
    public void completeNotationMovement()
    {
        this.notation.completeNotationMovement(this.movementManager);
    }

    /**
     * Vracia momentálne nastavený ťah partie.
     * @return Objekt ťahu.
     */
    public MovementManager getMovementManager(){return this.movementManager;}

    /**
     * Vracia informáciu o tom či zápis notácie partie správny.
     * @return Úspešnosť testu.
     */
    public boolean isNotationRight(){return this.notation.getIsRight();}


}
