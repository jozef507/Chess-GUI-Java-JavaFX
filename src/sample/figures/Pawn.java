package sample.figures;
import sample.board.Field;
public class Pawn implements Figure
{
    private boolean isPawnWhite;
    private Field actField;

    public Pawn(boolean isWhite)
    {
        this.isPawnWhite = isWhite;
        this.actField = null;
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

    public boolean move(Field moveTo)
    {
        if(this.actField == null)
            return false;

        int actCol = this.actField.getColPos();
        int actRow = this.actField.getRowPos();
        Field.Direction checkDirection;
        switch(isDirectionCorrect(actCol, actRow, moveTo))
        {
            case -1:
                return false;
            case 0:
                return true;
            case 1:
                checkDirection = Field.Direction.D;
                break;
            case 2:
                checkDirection = Field.Direction.U;
                break;
        }

        if(!moveTo.isEmpty())
            return false;

        this.actField.remove(this);
        moveTo.put(this);
        return true;
    }

    private short isDirectionCorrect(int col, int row, Field moveTo)
    {
        if(col == moveTo.getColPos() && row == moveTo.getRowPos())
        {
            return 0;
        }
        else if (col == moveTo.getColPos())
        {
            if(Math.abs(moveTo.getRowPos()-this.actField.getRowPos())!=1)
                return -1;

            if (this.isPawnWhite)
            {
                if (row < moveTo.getRowPos())
                {
                    return 1;       //check direction -> down from moveTO
                }
                else
                {
                    return -1;
                }
            }
            else
            {
                if (row > moveTo.getRowPos())
                {
                    return 2;       //check direction -> up from moveTO
                }
                else
                {
                    return -1;
                }
            }
        }
        else
        {
            return -1;
        }
    }

    public String getState()
    {
        String color;
        if(this.isWhite())
            color = "W";
        else
            color = "B";

        return "P[" + color + "]" + this.actField.getColPos() + ":" + this.actField.getRowPos();
    }

}
