/**
 * Trieda reprezentujúca aktuálny ťah hry.
 * @author Ján Folenta
 * @author Jozef Ondria
 */


package sample.board;

import javafx.scene.image.ImageView;
import sample.board.Board;
import sample.board.Field;
import sample.figures.Figure;
import sample.figures.FiguresManager;
import sample.notation.Notation;
import sample.notation.NotationMovement;

import java.util.ArrayList;
import java.util.List;

/**
 * Trieda ktorá reprezentuje ťah partie - jeho všetky dôležité informácie:
 * štartovacie políčko, cieľové políčko, firgúrky na týchto políčkach...
 */
public class MovementManager
{
    private boolean canPlayerPlay;
    private boolean isWhiteOnTheMove;
    private boolean isMovementCompletlySet;
    private boolean isRemovingFigure;
    private boolean isChangingFigure;

    private Field startField;
    private Figure movementFigure;
    private Field goalField;
    private Figure goalFieldFigure;
    private Figure changingFigure;

    /**
     * Inicializuje ťah partie.
     */
    public MovementManager()
    {
        this.canPlayerPlay = true;
        this.isWhiteOnTheMove = true;
        this.isMovementCompletlySet = false;
        this.isRemovingFigure = false;
        this.isChangingFigure = false;

        this.startField = null;
        this.movementFigure = null;
        this.goalField = null;
        this.goalFieldFigure = null;
        this.changingFigure = null;
    }


    /**
     * Vracia štartovacie políčko ťahu.
     * @return Odkaz na políčko.
     */
    public Field getStartField () { return this.startField;}

    /**
     * Vracia štartovacie políčko ťahu.
     * @return Odkaz na štartovacie políčko.
     */
    public Field getGoalField() {
        return this.goalField;
    }

    /**
     * Zmení hráča, ktorý je na ťahu.
     */
    public void changePlayer() {
        this.isWhiteOnTheMove = !isWhiteOnTheMove;
    }

    /**
     * Nastaví informáciu o tom či môže užívateľ previesť vlastný ťah.
     * @param canPlayerPlay
     */
    public void setCanPlayerPlay(boolean canPlayerPlay) {
        this.canPlayerPlay = canPlayerPlay;
    }

    /**
     * Vynuluje informácie o ťahu a tým prípráví aplikáciu na ďalší ťah.
     */
    public void nullMovementManager() {
        this.isMovementCompletlySet = false;
        this.isRemovingFigure = false;
        this.isChangingFigure = false;

        this.startField = null;
        this.movementFigure = null;
        this.goalField = null;
        this.goalFieldFigure = null;
        this.changingFigure = null;
    }

    /**
     * Nastavuje ťah ktorý sa má vykonať. Nastavuje štartovacie a cieľove políčko.
     * Ak je ťah vynulovaný a povola sa metóda tak sa nastaví štartovacie políčko ak nie nastavuje
     * sa cieľové políčko.
     * @param col Stlpec políčka.
     * @param row Riadok políčka.
     * @param board Šachovníca.
     * @return Úspešnosť nastavenia.
     */
    public boolean setPlayerMovement(int col, int row, Board board) {
        if (!canPlayerPlay) {
            return false;
        }

        if(this.isChangingFigure)
        {
            return false;
        }

        if (this.startField == null) {
            this.startField = board.getField(col, row);
            if (this.startField.isEmpty()) {
                this.nullMovementManager();
                return false;
            }

            this.movementFigure = this.startField.get();
            if (this.movementFigure.isWhite() != this.isWhiteOnTheMove) {
                this.nullMovementManager();
                return false;
            }
            return true;
        } else {
            this.goalField = board.getField(col, row);
            if (!this.goalField.isEmpty())
                this.goalFieldFigure = goalField.get();
            this.isMovementCompletlySet = true;
            return true;
        }
    }


    /**
     * Nastavuje prehrávaný ťah ktorý sa má vykonať. Nastavuje štartovacie a cieľove políčko.
     * Ak je ťah vynulovaný a povola sa metóda tak sa nastaví štartovacie políčko ak nie nastavuje
     * sa cieľové políčko. Tieto políčka sa získavajú z notácie šachovej partie.
     * @param board Šachovnica.
     * @param notation Notácia partie.
     * @param figuresManager Manažer aktívnych figúrok.
     * @return Úspešnosť nastavenia.
     */
    public boolean setPlaybackMovement(Board board, Notation notation, FiguresManager figuresManager) {

        if(this.isChangingFigure)
            return false;

        NotationMovement mov = notation.getActualNotMov();

        this.goalField = board.getField(mov.getGoalFieldCol(), mov.getGoalFieldRow());
        if (!this.goalField.isEmpty())
            this.goalFieldFigure = goalField.get();

        if((mov.getIsLongNotation()) || (!mov.getIsLongNotation() && mov.getStartFieldCol() != -1 && mov.getStartFieldRow() != -1))
        {
            this.startField = board.getField(mov.getStartFieldCol(),mov.getStartFieldRow());
        }
        else
        {
            int movementFigureID = mov.getMovementFigureID();
            List<Figure> activeFigures;
            if(this.isWhiteOnTheMove)
                activeFigures = figuresManager.getActiveWhiteFigures();
            else
                activeFigures = figuresManager.getActiveBlackFigures();

            List<Figure> tmpFigures = new ArrayList<Figure>();
            int ID = -1;
            List<Field> tmpFields;
            for (int i = 0; i<activeFigures.size(); i++)
            {
                ID = activeFigures.get(i).getID();
                tmpFields = activeFigures.get(i).getFieldsForPossMov();
                if( ID== movementFigureID
                    && tmpFields.contains(this.goalField))
                {
                    tmpFigures.add(activeFigures.get(i));
                }
            }

            if(tmpFigures.size()==1)
                this.startField = tmpFigures.get(0).getActField();
            else if(tmpFigures.size()>1)
            {
                if(mov.getStartFieldCol() != -1)
                {
                    for (int i = 0; i<tmpFigures.size(); i++)
                    {
                        if(mov.getStartFieldCol() == tmpFigures.get(i).getActField().getColPos())
                        {
                            this.startField = tmpFigures.get(i).getActField();
                            break;
                        }
                    }
                }
                else if(mov.getStartFieldRow() != -1)
                {
                    for (int i = 0; i<tmpFigures.size(); i++)
                    {
                        if(mov.getStartFieldRow() == tmpFigures.get(i).getActField().getRowPos())
                        {
                            this.startField = tmpFigures.get(i).getActField();
                            break;
                        }
                    }
                }
            }
        }

        if (this.startField.isEmpty()) {
            this.nullMovementManager();
            return false;
        }

        this.movementFigure = this.startField.get();
        if (this.movementFigure.isWhite() != this.isWhiteOnTheMove) {
            this.nullMovementManager();
            return false;
        }

        this.isMovementCompletlySet = true;
        return true;
    }

    /**
     * Vykoná už nastavený ťah prostredníctvom rozhrania na figúrku ktorý sa má presúvať.
     * Následne otestuje návratovú hodnotu od figúrky a nastavý ďalšie dôležíte informácie o ťahu.
     * @param figuresManager Manažer aktívnych figúrok.
     * @return Úspešnosť ťahu.
     */
    public boolean performMovement(FiguresManager figuresManager) {
        if (this.startField != null && this.movementFigure != null && this.goalField != null)
        {
            int flag = this.movementFigure.move(this.goalField, figuresManager);
            if(flag==-1)
            {
                return false;
            }
            else if(flag == 1)
            {
                return true;
            }
            else if(flag == 2)
            {
                this.isRemovingFigure = true;
                return true;
            }
            else if(flag == 3)
            {
                this.isChangingFigure = true;
                return true;
            }
            else if(flag == 4)
            {
                this.isChangingFigure = true;
                this.isRemovingFigure = true;
                return true;
            }
            else
            {
                return false;
            }

        } else {
            return false;
        }
    }

    /**
     * Nastaví prehravaný ťah do predu. Informácie o ťahu sa získavajú z notácie partie.
     * @param mov Ťah repretentovaný notáciou.
     * @param board Šachovnica.
     * @param figuresManager Manažer aktívných figúrok.
     * @return Úspešnosť nastavenia.
     */
    public boolean setPlaybackUndoMovement(NotationMovement mov, Board board, FiguresManager figuresManager)
    {
        this.startField = board.getField(mov.getStartFieldCol(), mov.getStartFieldRow());
        this.goalField = board.getField(mov.getGoalFieldCol(), mov.getGoalFieldRow());
        if(mov.getChangingFigureID()!=-1)
        {
            this.movementFigure = figuresManager.getLastChangedFigure();
            figuresManager.removeLastChangedFigure();
            this.changingFigure = this.goalField.get();
            this.isChangingFigure = true;
        }
        else
        {
            this.movementFigure = goalField.get();
        }


        this.isWhiteOnTheMove = this.movementFigure.isWhite();


        if(mov.getIsFigureRemoving())
        {
            this.goalFieldFigure = figuresManager.getLastRemovedFigure();
            figuresManager.removeLastRemovedFigure();
            this.isRemovingFigure = true;
        }

        this.isWhiteOnTheMove = this.movementFigure.isWhite();
        return true;
    }

    /**
     * Vykoná už nastavený ťah dozadu.
     * @param mov Ťah reprezentovaný notáciou.
     * @param figuresManager Manažer aktívnych figúrok.
     * @return Úspešnosť ťahu.
     */
    public boolean performPlaybackUndoMovement(NotationMovement mov, FiguresManager figuresManager)
    {
        if(this.isChangingFigure)
        {
            this.goalField.remove(this.changingFigure);
            figuresManager.removeActiveFigure(this.changingFigure);
            this.startField.put(this.movementFigure);
            figuresManager.addActiveFigure(this.movementFigure);
        }
        else
        {
            this.goalField.remove(this.movementFigure);
            this.startField.put(this.movementFigure);
        }



        if(this.isRemovingFigure)
        {
            this.goalField.put(this.goalFieldFigure);
            figuresManager.addActiveFigure(this.goalFieldFigure);
        }

        figuresManager.setChessMat(false);
        return true;
    }

    /**
     * Vracia figúrku ktorá nahrádza pešiaka.
     * @return Odkaz na figúrku.
     */
    public Figure getChangingFigure()
    {
        return this.changingFigure;
    }


    /**
     * Vracia figúrku ktorej ťah sa vykonáva.
     * @return Odkaz na figúrku.
     */
    public Figure getMovementFigure() {
        return this.movementFigure;
    }

    /**
     * Vracia figúrku ktorej ťah sa vykonáva.
     * @return Odkaz na figúrku.
     */
    public Figure getGoalFieldFigure() {
        return this.goalFieldFigure;
    }

    /**
     * Vracia informáciu o tom či je na ťahu práve biely hráč.
     * @return Pravdivostna informácia.
     */
    public boolean isWhiteOnTheMove() {
        return this.isWhiteOnTheMove;
    }

    /**
     * Vracia informáciu o tom či je ťah kompletne nastavený (či už je nastavené aj cieľové políčko).
     * @return Pravdivostna informácia.
     */
    public boolean isMovementCompletlySet() {return this.isMovementCompletlySet;}

    /**
     * Vracia informáciu o tom či dochádza k vyhodeniu figúrky.
     * @return Pravdivostna informácia.
     */
    public boolean getIsRemovingFigure () {return this.isRemovingFigure;}

    /**
     * Vracia informáciu o tom či dochádza k zámene pešiaka.
     * @return Pravdivostna informácia.
     */
    public boolean getIsChangingFigure() {return this.isChangingFigure;}


}
