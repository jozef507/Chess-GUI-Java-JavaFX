/**
 * Trieda reprezentujúca figúrku kráľa.
 * @author Jozef Ondria
 */


package sample.figures;

import javafx.scene.image.ImageView;
import sample.board.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * Trieda reprezentujúca figúrku kráľa.
 * Implementuje rozhranie Figure.
 */
public class King implements Figure
{
    private int ID;

    private boolean isKingWhite;
    private boolean isRemovingFigure;

    private Field actField;
    private ImageView image;
    private List<Field> fieldsInDanger;

    private boolean inChess;
    private Figure chessBy;

    /**
     * Inicializacia kráľa.
     * @param isWhite Informacia o farbe.
     * @param image Obrázok figúrky používaný v grafickom rozhraní.
     */
    public King(boolean isWhite, ImageView image)
    {
        this.ID = 0;
        this.isKingWhite = isWhite;
        this.inChess = false;
        this.actField = null;
        this.image = image;
        this.fieldsInDanger = new ArrayList<Field>();
        this.chessBy = null;
    }


    /**
     * Vracia odkaz na obrázok figúrky používajucí v grafickom rozhraní.
     * @return Odkaz na obrázok.
     */
    public ImageView getImage()
    {
        return this.image;
    }

    /**
     * Vracia informáciu o tom či je figúrka biela. Ak nie je čierna.
     * @return Pravdivostna hodnota.
     */
    public boolean isWhite()
    {
        return this.isKingWhite;
    }


    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return super.equals(obj);
    }

    /**
     * Nastaví aktuálnu pozíciu figúrky.
     * @param field Políčko pozície na ktorú sa má figurka nastaviť.
     */
    public void setActualPosition(Field field)
    {
        this.actField = field;
    }

    /**
     * Vynulovanie/zrušenie aktualnej pozície figúrky.
     */
    public void nulActualPosition()
    {
        this.actField = null;
    }

    /**
     * Vráti pozíciu aktualnej pozície figúrky.
     * @return Odkaz na políčko.
     */
    public Field getActField()
    {
        return this.actField;
    }

    /**
     * Vykonáva ťah figúrky a zároveń testuje či je ťah možný.
     * @param moveTo Cieľove políčko ťahu.
     * @param figuresManager Manažer aktívnych figúrok.
     * @return Stav úspešnosti ťahu.
     */
    public int move(Field moveTo, FiguresManager figuresManager)
    {
        if(this.actField == null)
            return -1;

        int actCol = this.actField.getColPos();
        int actRow = this.actField.getRowPos();
        int movetoCol = moveTo.getColPos();
        int movetoRow = moveTo.getRowPos();

        if(actCol == movetoCol && actRow == movetoRow)
            return -1;

        if(!isMovementPossible(actCol, actRow, moveTo, movetoCol, movetoRow))
            return -1;

        int flag = 1;
        Figure movetoFigure = null;
        Field prevField = this.actField;
        if(this.isRemovingFigure)
        {
            movetoFigure = moveTo.get();
            moveTo.remove(movetoFigure);
            this.isRemovingFigure = false;
            flag = 2;
        }
        this.actField.remove(this);
        moveTo.put(this);

        if(!figuresManager.updateFigures(this.isWhite(), this, movetoFigure))
        {
            moveTo.remove(this);
            prevField.put(this);
            if (flag == 2)
            {
                moveTo.put(movetoFigure);
            }
            flag = -1;
        }

        return flag;
    }

    /**
     * Testuje či je ťah na cieľove políčko možný.
     * @param actCol Stlpec aktuálneho polička.
     * @param actRow Riadok aktualneho políčka.
     * @param moveTo Cieľové políčko.
     * @param movetoCol Stlpec aktuálneho polička.
     * @param movetoRow Riadok aktualneho políčka.
     * @return Pravdivostná hodnota.
     */
    private boolean isMovementPossible(int actCol, int actRow, Field moveTo, int movetoCol, int movetoRow)
    {
        int colDiff = Math.abs(movetoCol-actCol);
        int rowDiff = Math.abs(movetoRow-actRow);

        if(colDiff > 1 || rowDiff > 1)
            return false;

        if(!moveTo.isEmpty())
        {
            Figure movetoFigure = moveTo.get();
            if(movetoFigure.isWhite() == this.isWhite())
                return false;

            if(movetoFigure instanceof King)
                return false;

            this.isRemovingFigure = true;
        }

        return true;
    }

    /**
     * Nastaví pre figúrku všetky políčka ktoré táto figúrka ohrozuej.
     */
    public void setFieldsInDanger()
    {
        this.fieldsInDanger.clear();

        this.appendFieldsInDanger(Field.Direction.RU);
        this.appendFieldsInDanger(Field.Direction.LU);
        this.appendFieldsInDanger(Field.Direction.RD);
        this.appendFieldsInDanger(Field.Direction.LD);
        this.appendFieldsInDanger(Field.Direction.R);
        this.appendFieldsInDanger(Field.Direction.L);
        this.appendFieldsInDanger(Field.Direction.U);
        this.appendFieldsInDanger(Field.Direction.D);
    }

    /**
     * Pridá polička v danom smere.
     * @param dir Smer na šachovnici.
     */
    private void appendFieldsInDanger(Field.Direction dir)
    {
        Field nextField=this.actField.nextField(dir);
        if(nextField != null)
            this.fieldsInDanger.add(nextField);
    }

    /**
     * Vracia štruktúru políčok ktoré figúrka ohrozuje.
     * @return Štruktúra políčok.
     */
    public List<Field> getFieldsInDanger()
    {
        return this.fieldsInDanger;
    }

    /**
     * Testuje pri šachu či sa tento kráľ môže niekam pohnúť.
     * @param fieldsInDangerOut Ohrozené políčka.
     * @return Výsledok testu.
     */
    public boolean canMove(List<Field> fieldsInDangerOut)
    {
        Field nextField;

        nextField = this.actField.nextField(Field.Direction.U);
        if(nextField != null && !fieldsInDangerOut.contains(nextField))
        {
            if(nextField.isEmpty())
            {
                return true;
            }
            else
            {
                Figure tmp = nextField.get();
                if(tmp.isWhite() != this.isWhite())
                    return true;
            }
        }


        nextField = this.actField.nextField(Field.Direction.RU);
        if(nextField != null && !fieldsInDangerOut.contains(nextField))
        {
            if(nextField.isEmpty())
            {
                return true;
            }
            else
            {
                Figure tmp = nextField.get();
                if(tmp.isWhite() != this.isWhite())
                    return true;
            }
        }

        nextField = this.actField.nextField(Field.Direction.R);
        if(nextField != null && !fieldsInDangerOut.contains(nextField))
        {
            if(nextField.isEmpty())
            {
                return true;
            }
            else
            {
                Figure tmp = nextField.get();
                if(tmp.isWhite() != this.isWhite())
                    return true;
            }
        }

        nextField = this.actField.nextField(Field.Direction.RD);
        if(nextField != null && !fieldsInDangerOut.contains(nextField))
        {
            if(nextField.isEmpty())
            {
                return true;
            }
            else
            {
                Figure tmp = nextField.get();
                if(tmp.isWhite() != this.isWhite())
                    return true;
            }
        }

        nextField = this.actField.nextField(Field.Direction.D);
        if(nextField != null && !fieldsInDangerOut.contains(nextField))
        {
            if(nextField.isEmpty())
            {
                return true;
            }
            else
            {
                Figure tmp = nextField.get();
                if(tmp.isWhite() != this.isWhite())
                    return true;
            }
        }

        nextField = this.actField.nextField(Field.Direction.LD);
        if(nextField != null && !fieldsInDangerOut.contains(nextField))
        {
            if(nextField.isEmpty())
            {
                return true;
            }
            else
            {
                Figure tmp = nextField.get();
                if(tmp.isWhite() != this.isWhite())
                    return true;
            }
        }

        nextField = this.actField.nextField(Field.Direction.L);
        if(nextField != null && !fieldsInDangerOut.contains(nextField))
        {
            if(nextField.isEmpty())
            {
                return true;
            }
            else
            {
                Figure tmp = nextField.get();
                if(tmp.isWhite() != this.isWhite())
                    return true;
            }
        }
        nextField = this.actField.nextField(Field.Direction.LU);
        if(nextField != null && !fieldsInDangerOut.contains(nextField))
        {
            if(nextField.isEmpty())
            {
                return true;
            }
            else
            {
                Figure tmp = nextField.get();
                if(tmp.isWhite() != this.isWhite())
                    return true;
            }
        }

        return false;
    }

    /**
     * Nastaví informáciu, či je kraľ v šachu.
     * @param inChess
     */
    public void setInChess(boolean inChess) {
        this.inChess = inChess;
    }

    /**
     * Vráti informáciu či je kráľ v šachu.
     * @return Pravdivostná hodnota.
     */
    public boolean getInChess()
    {
        return this.inChess;
    }

    /**
     * Nastaví informáciu že je kráľ v šachu danou figúrkou.
     * @param figure hodnota.
     */
    public void setChessBy(Figure figure)
    {
        this.chessBy = figure;
    }

    /**
     * Vráti figúrku ktorou je tento kraľ v šachu.
     * @return
     */
    public Figure getChessBy ()
    {
        return this.chessBy;
    }

    /**
     * Vracia id figúrky.
     * @return ID figúrky.
     */
    public int getID() {return this.ID;}

    /**
     * Vráti štruktúru všetkych políčok smerujúcich k danému fieldu v parametri.
     * @param field Odkaz na field.
     * @return Štruktúra políčok.
     */
    public List<Field> getFieldsOfDirectionToField(Field field)
    {
        if(!this.fieldsInDanger.contains(field))
            return null;

        List<Field> fieldsOfDirToField = new ArrayList<Field>();
        fieldsOfDirToField.add(this.actField);

        return fieldsOfDirToField;
    }

    /**
     * Vracia štruktúru všetkých políčok ktorými môže ohrozovať súperoveho kráľa.
     * @return Štruktúra políčok.
     */
    public List<Field> getFieldsForPossMov()
    {
        return this.fieldsInDanger;
    }

    /**
     * Vracia štruktúru všetkých políčok ktorými môže figúrka vytvárať šachmat.
     * @return Štruktúra políčok.
     */
    public  List<Field> getFieldsInDangerChesMat()
    {
        return this.fieldsInDanger;
    }

}
