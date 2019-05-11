/**
 * Trieda reprezentujúca figúrku jazdca.
 * @author Ján Folenta
 * @author Jozef Ondria
 */

package sample.figures;

import javafx.scene.image.ImageView;
import sample.board.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * Trieda reprezentujúca figúrku jazdca.
 * Implementuje rozhranie Figure.
 */
public class Knight implements Figure
{
    private int ID;
    private boolean isKnightWhite;
    private boolean isRemovingFigure;
    private Field actField;
    private ImageView image;
    private List<Field> fieldsInDanger;

    /**
     * Inicializacia kráľa.
     * @param isWhite Informacia o farbe.
     * @param image Obrázok figúrky používaný v grafickom rozhraní.
     */
    public Knight(boolean isWhite, ImageView image)
    {
        this.ID = 3;
        this.isKnightWhite = isWhite;
        this.actField = null;
        this.image = image;
        this.fieldsInDanger = new ArrayList<Field>();
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
        return this.isKnightWhite;
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


        if(!figuresManager.updateFigures(this.isWhite(), this ,movetoFigure))
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

        if(!((colDiff == 2 && rowDiff == 1) || (colDiff == 1 && rowDiff == 2)))
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

        Field tmpField, nextField;

        nextField = this.actField.nextField(Field.Direction.U);
        if(nextField != null)
        {
            nextField = nextField.nextField(Field.Direction.U);
            if(nextField != null)
            {
                tmpField = nextField.nextField(Field.Direction.L);
                nextField = nextField.nextField(Field.Direction.R);

                if (tmpField != null)
                    this.fieldsInDanger.add(tmpField);
                if (nextField != null)
                    this.fieldsInDanger.add(nextField);
            }
        }

        nextField = this.actField.nextField(Field.Direction.L);
        if(nextField != null)
        {
            nextField = nextField.nextField(Field.Direction.L);
            if(nextField != null)
            {
                tmpField = nextField.nextField(Field.Direction.U);
                nextField = nextField.nextField(Field.Direction.D);

                if (tmpField != null)
                    this.fieldsInDanger.add(tmpField);
                if (nextField != null)
                    this.fieldsInDanger.add(nextField);
            }
        }
        nextField = this.actField.nextField(Field.Direction.D);
        if(nextField != null)
        {
            nextField = nextField.nextField(Field.Direction.D);
            if(nextField != null)
            {
                tmpField = nextField.nextField(Field.Direction.L);
                nextField = nextField.nextField(Field.Direction.R);

                if (tmpField != null)
                    this.fieldsInDanger.add(tmpField);
                if (nextField != null)
                    this.fieldsInDanger.add(nextField);
            }
        }
        nextField = this.actField.nextField(Field.Direction.R);
        if(nextField != null)
        {
            nextField = nextField.nextField(Field.Direction.R);
            if(nextField != null)
            {
                tmpField = nextField.nextField(Field.Direction.U);
                nextField = nextField.nextField(Field.Direction.D);

                if (tmpField != null)
                    this.fieldsInDanger.add(tmpField);
                if (nextField != null)
                    this.fieldsInDanger.add(nextField);
            }
        }
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
