/**
 * Trieda reprezentujúca figúrku veže.
 * @author Ján Folenta
 * @author Jozef Ondria
 */

package sample.figures;
import javafx.scene.image.ImageView;
import sample.board.Field;

import java.util.ArrayList;
import java.util.List;


/**
 * Trieda reprezentujúca figúrku veže.
 * Implementuje rozhranie Figure.
 */
public class Rook implements Figure
{
    private int ID;
    private boolean isRookWhite;
    private boolean isRemovingFigure;
    private Field actField;
    private ImageView image;
    private List<Field> fieldsInDanger;

    /**
     * Inicializacia veže.
     * @param isWhite Informacia o farbe.
     * @param image Obrázok figúrky používaný v grafickom rozhraní.
     */
    public Rook(boolean isWhite, ImageView image)
    {
        this.ID = 4;
        this.isRookWhite = isWhite;
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
        return this.isRookWhite;
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

        Field.Direction dir;
        if(actRow < movetoRow && colDiff == 0)
        {
            dir = Field.Direction.U;
        }
        else if(actRow > movetoRow && colDiff == 0)
        {
            dir = Field.Direction.D;
        }
        else if (actCol < movetoCol && rowDiff == 0)
        {
            dir = Field.Direction.R;
        }
        else if (actCol > movetoCol && rowDiff == 0)
        {
            dir = Field.Direction.L;
        }
        else
        {
            return false;
        }

        int diff = (rowDiff == 0 ? colDiff : rowDiff);
        if(!checkDirection(dir, diff))
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
     * Skontroluje polička v danom smere, či sa tam nenachádza iná figúrka.
     * @param dir Smer na šachovnici.
     * @param diff Rozdiel políčok ktorý treba otestovať.
     * @return Pravdivostna hodnota.
     */
    private boolean checkDirection(Field.Direction dir, int diff)
    {
        Field nextField=this.actField;
        for (int i = 0; i < diff-1; i++)
        {
            nextField = nextField.nextField(dir);
            if(!(nextField.isEmpty()))
                return false;
        }
        return true;
    }

    /**
     * Nastaví pre figúrku všetky políčka ktoré táto figúrka ohrozuej.
     */
    public void setFieldsInDanger()
    {
        this.fieldsInDanger.clear();

        this.appendFieldsInDanger(Field.Direction.U);
        this.appendFieldsInDanger(Field.Direction.D);
        this.appendFieldsInDanger(Field.Direction.R);
        this.appendFieldsInDanger(Field.Direction.L);
    }

    /**
     * Pridá polička v danom smere.
     * @param dir Smer na šachovnici.
     */
    private void appendFieldsInDanger(Field.Direction dir)
    {
        Field nextField = this.actField.nextField(dir);
        while (nextField != null)
        {
            this.fieldsInDanger.add(nextField);
            if (!nextField.isEmpty())
                break;
            nextField = nextField.nextField(dir);
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

        int colDiff = field.getColPos() - this.actField.getColPos();
        int rowDiff = field.getRowPos() - this.actField.getRowPos();


        Field.Direction dir;
        if (colDiff>0 && rowDiff==0)
            dir = Field.Direction.R;
        else if (colDiff<0 && rowDiff==0)
            dir = Field.Direction.L;
        else if (colDiff==0 && rowDiff>0)
            dir = Field.Direction.U;
        else if (colDiff==0 && rowDiff<0)
            dir = Field.Direction.D;
        else
            dir = Field.Direction.NONE;

        if(dir == Field.Direction.NONE)
            return null;

        List<Field> fieldsOfDirToField = new ArrayList<Field>();
        Field nextField = this.actField;
        while (nextField!=field)
        {
            fieldsOfDirToField.add(nextField);
            nextField = nextField.nextField(dir);
        }

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
        List<Field> tmp= new ArrayList<Field>();

        tmp.addAll(appendFieldsInDangerChessMat(Field.Direction.U));
        tmp.addAll(appendFieldsInDangerChessMat(Field.Direction.D));
        tmp.addAll(appendFieldsInDangerChessMat(Field.Direction.R));
        tmp.addAll(appendFieldsInDangerChessMat(Field.Direction.L));

        return tmp;
    }

    /**
     * Pridá polička v danom smere.
     * @param dir Smer na šachovnici.
     */
    private List<Field> appendFieldsInDangerChessMat(Field.Direction dir)
    {
        List<Field> tmp= new ArrayList<Field>();
        Field nextField = this.actField.nextField(dir);
        while (nextField != null)
        {
            tmp.add(nextField);
            if (!nextField.isEmpty())
                if(!(nextField.get().getID() == 0 && nextField.get().isWhite()!=this.isWhite()))
                    break;
            nextField = nextField.nextField(dir);
        }
        return tmp;
    }

}
