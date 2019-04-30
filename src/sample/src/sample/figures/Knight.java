package sample.figures;

import javafx.scene.image.ImageView;
import sample.board.Field;

import java.util.ArrayList;
import java.util.List;

public class Knight implements Figure
{
    private int ID;
    private boolean isKnightWhite;
    private boolean isRemovingFigure;
    private Field actField;
    private ImageView image;
    private List<Field> fieldsInDanger;

    public Knight(boolean isWhite, ImageView image)
    {
        this.ID = 3;
        this.isKnightWhite = isWhite;
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

    public void setActualPosition(Field field)
    {
        this.actField = field;
    }

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

    public List<Field> getFieldsInDanger()
    {
        return this.fieldsInDanger;
    }

    public int getID() {return this.ID;}

    public List<Field> getFieldsOfDirectionToField(Field field)
    {
        if(!this.fieldsInDanger.contains(field))
            return null;

        List<Field> fieldsOfDirToField = new ArrayList<Field>();
        fieldsOfDirToField.add(this.actField);

        return fieldsOfDirToField;
    }

}
