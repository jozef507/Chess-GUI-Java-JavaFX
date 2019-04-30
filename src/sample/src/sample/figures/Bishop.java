package sample.figures;
import javafx.scene.image.ImageView;
import sample.board.Field;

import java.util.ArrayList;
import java.util.List;

public class Bishop implements Figure
{
    private int ID;
    private boolean isBishopWhite;
    private boolean isRemovingFigure;
    private Field actField;
    private ImageView image;
    private List<Field> fieldsInDanger;


    public Bishop(boolean isWhite, ImageView image)
    {
        this.ID = 2;
        this.isBishopWhite = isWhite;
        this.actField = null;
        this.image = image;
        this.fieldsInDanger = new ArrayList<Field>();
    }

    public ImageView getImage()
    {
        return this.image;
    }


    public boolean isWhite()
    {
        return this.isBishopWhite;
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

    public void setActualPosition(Field field) { this.actField = field; }

    public void nulActualPosition()
    {
        this.actField = null;
    }

    public Field getActField()
    {
        return this.actField;
    }

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

    private boolean isMovementPossible(int actCol, int actRow, Field moveTo, int movetoCol, int movetoRow)
    {
        int colDiff = Math.abs(movetoCol-actCol);
        int rowDiff = Math.abs(movetoRow-actRow);

        if(colDiff != rowDiff)
        {
            return false;
        }

        Field.Direction dir;
        if(actRow < movetoRow && actCol < movetoCol)
        {
            dir = Field.Direction.RU;
        }
        else if(actRow < movetoRow && actCol > movetoCol)
        {
            dir = Field.Direction.LU;
        }
        else if (actRow > movetoRow && actCol < movetoCol)
        {
            dir = Field.Direction.RD;
        }
        else if (actRow > movetoRow && actCol > movetoCol)
        {
            dir = Field.Direction.LD;
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


    public void setFieldsInDanger()
    {
        this.fieldsInDanger.clear();
        this.appendFieldsInDanger(Field.Direction.RU);
        this.appendFieldsInDanger(Field.Direction.LU);
        this.appendFieldsInDanger(Field.Direction.RD);
        this.appendFieldsInDanger(Field.Direction.LD);
    }

    private void appendFieldsInDanger(Field.Direction dir)
    {
        Field nextField=this.actField.nextField(dir);
        while (nextField != null)
        {
            this.fieldsInDanger.add(nextField);
            if (!nextField.isEmpty())
                break;
            nextField = nextField.nextField(dir);
        }

    }

    public List<Field> getFieldsInDanger()
    {
        return this.fieldsInDanger;
    }

    public int getID() {return this.ID;}

    public List<Field> getFieldsOfDirectionToField(Field field)
    {
        if(!this.fieldsInDanger.contains(field))
            return null;

        int colDiff = field.getColPos() - this.actField.getColPos();
        int rowDiff = field.getRowPos() - this.actField.getRowPos();

        if(Math.abs(colDiff) != Math.abs(rowDiff))
            return null;

        Field.Direction dir;
        if (colDiff>0 && rowDiff>0)
            dir = Field.Direction.RU;
        else if (colDiff>0 && rowDiff<0)
            dir = Field.Direction.RD;
        else if (colDiff<0 && rowDiff>0)
            dir = Field.Direction.LU;
        else if (colDiff<0 && rowDiff<0)
            dir = Field.Direction.LD;
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
}
