package sample.figures;
import javafx.scene.image.ImageView;
import sample.board.Field;

import java.util.ArrayList;
import java.util.List;


public class Pawn implements Figure
{
    private int ID;
    private boolean isPawnWhite;
    private boolean firstMovementDone;
    private boolean isRemovingFigure;
    private Field actField;
    private ImageView image;
    private List<Field> fieldsInDanger;


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

    public ImageView getImage()
    {
        return this.image;
    }

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


        return flag;
    }

    private boolean isMovementPossible(int actCol, int actRow, Field moveTo, int movetoCol, int movetoRow)
    {
        int colDiff = Math.abs(movetoCol-actCol);
        int rowDiff = Math.abs(movetoRow-actRow);

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


}
