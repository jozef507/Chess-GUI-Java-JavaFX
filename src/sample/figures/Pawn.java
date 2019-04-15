package sample.figures;
import javafx.scene.image.ImageView;
import sample.board.Field;


public class Pawn implements Figure
{
    private boolean isPawnWhite;
    private boolean firstMovementDone;
    private boolean isRemovingFigure;
    private Field actField;
    private ImageView image;


    public Pawn(boolean isWhite, ImageView image)
    {
        this.isPawnWhite = isWhite;
        this.firstMovementDone = false;
        this.isRemovingFigure = false;
        this.actField = null;
        this.image = image;

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

    public int move(Field moveTo)
    {
        int flag = 1;
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

        if(this.isRemovingFigure)
        {
            Figure movetoFigure = moveTo.get();
            moveTo.remove(movetoFigure);
            this.isRemovingFigure = false;
            flag = 2;
        }

        this.actField.remove(this);
        moveTo.put(this);
        this.firstMovementDone = true;
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
