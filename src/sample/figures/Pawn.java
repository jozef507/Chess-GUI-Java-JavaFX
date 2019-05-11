package sample.figures;
import javafx.scene.image.ImageView;
import sample.board.Field;

import java.util.ArrayList;
import java.util.List;


/**
 * Trieda reprezentujúca figúrku pešiaka.
 * Implementuje rozhranie Figure.
 */
public class Pawn implements Figure
{
    private int ID;
    private boolean isPawnWhite;
    private boolean firstMovementDone;
    private boolean isRemovingFigure;
    private Field actField;
    private ImageView image;
    private List<Field> fieldsInDanger;


    /**
     * Inicializacia pešiaka.
     * @param isWhite Informacia o farbe.
     * @param image Obrázok figúrky používaný v grafickom rozhraní.
     */
    public Pawn(boolean isWhite, ImageView image)
    {
        this.ID = 5;
        this.isPawnWhite = isWhite;
        this.firstMovementDone = false;
        this.isRemovingFigure = false;
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
        return this.isPawnWhite;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

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
            return -1;
        }

        if(isOnTheLastField())
        {
            if (flag==1)
                flag = 3;
            else
                flag = 4;
        }

        this.firstMovementDone = true;
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

        if(this.isWhite() && this.actField.getRowPos() == 2)
            this.firstMovementDone = false;
        else if(!this.isWhite() && this.actField.getRowPos() == 7)
            this.firstMovementDone = false;



        if (colDiff == 0)
        {
            if(!this.firstMovementDone)
            {
                if(rowDiff != 1 && rowDiff != 2)
                    return false;
            }
            else
            {
                if(rowDiff != 1)
                    return false;
            }


            Field.Direction dir;
            if (this.isPawnWhite)
            {
                if (!(actRow < movetoRow))
                    return false;
                dir = Field.Direction.U;
            }
            else
            {
                if (!(actRow > movetoRow))
                    return false;
                dir = Field.Direction.D;
            }

            if(!checkDirWithoutRemove(dir, rowDiff))
                return false;
        }
        else if(colDiff==1)
        {
            if(rowDiff!=1)
                return false;

            if (this.isPawnWhite)
            {
                if (!(actRow < movetoRow))
                    return false;
            }
            else
            {
                if (!(actRow > movetoRow))
                    return false;
            }

            if(moveTo.isEmpty())
                return false;

            Figure movetoFigure = moveTo.get();
            if(movetoFigure.isWhite() == this.isWhite())
                return false;

            if(movetoFigure instanceof King)
                return false;

            this.isRemovingFigure = true;
        }
        else
        {
            return false;
        }

        return true;
    }


    private boolean checkDirWithoutRemove(Field.Direction dir, int diff)
    {
        Field nextField=this.actField;
        for (int i = 0; i < diff; i++)
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

        if(this.isWhite())
        {
            Field nextField = this.actField.nextField(Field.Direction.LU);
            if(nextField!=null)
                this.fieldsInDanger.add(nextField);
            nextField = this.actField.nextField(Field.Direction.RU);
            if(nextField!=null)
                this.fieldsInDanger.add(nextField);
        }
        else
        {
            Field nextField = this.actField.nextField(Field.Direction.LD);
            if(nextField!=null)
                this.fieldsInDanger.add(nextField);
            nextField = this.actField.nextField(Field.Direction.RD);
            if(nextField!=null)
                this.fieldsInDanger.add(nextField);
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

    private boolean isOnTheLastField()
    {
        if(this.isWhite())
        {
            if(this.actField.getRowPos()==8)
                return true;
        }
        else
        {
            if(this.actField.getRowPos()==1)
                return true;
        }
        return false;
    }

    /**
     * Vracia štruktúru všetkých políčok ktorými môže ohrozovať súperoveho kráľa.
     * @return Štruktúra políčok.
     */
    public List<Field> getFieldsForPossMov() {
        List<Field> tmp = new ArrayList<Field>();
        if (this.isWhite()) {
            if (this.firstMovementDone) {
                Field field = this.actField.nextField(Field.Direction.U);
                if (field != null && field.isEmpty())
                    tmp.add(field);
            } else {
                Field field = this.actField.nextField(Field.Direction.U);
                if (field != null && field.isEmpty()) {
                    tmp.add(field);
                    field = field.nextField(Field.Direction.U);
                    if (field != null && field.isEmpty())
                        tmp.add(field);
                }

            }
        }
        else
        {
            if (this.firstMovementDone) {
                Field field = this.actField.nextField(Field.Direction.D);
                if (field != null && field.isEmpty())
                    tmp.add(field);
            } else {
                Field field = this.actField.nextField(Field.Direction.D);
                if (field != null && field.isEmpty()) {
                    tmp.add(field);
                    field = field.nextField(Field.Direction.D);
                    if (field != null && field.isEmpty())
                        tmp.add(field);
                }

            }
        }

        if(this.isWhite())
        {
            Field nextField = this.actField.nextField(Field.Direction.LU);
            if(nextField!=null && !nextField.isEmpty())
                tmp.add(nextField);
            nextField = this.actField.nextField(Field.Direction.RU);
            if(nextField!=null && !nextField.isEmpty())
                tmp.add(nextField);
        }
        else
        {
            Field nextField = this.actField.nextField(Field.Direction.LD);
            if(nextField!=null && !nextField.isEmpty())
                tmp.add(nextField);
            nextField = this.actField.nextField(Field.Direction.RD);
            if(nextField!=null && !nextField.isEmpty())
                tmp.add(nextField);
        }


        return tmp;
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
