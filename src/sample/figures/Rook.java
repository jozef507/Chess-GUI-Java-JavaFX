package sample.figures;
import javafx.scene.image.ImageView;
import sample.board.Field;



public class Rook implements Figure
{
    private boolean isRookWhite;
    private Field actField;
    private ImageView image;

    public Rook(boolean isWhite)
    {
        this.isRookWhite = isWhite;

        this.actField = null;
    }


    public ImageView getImage()
    {
        return this.image;
    }


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
        if(this.actField == null)
            return -1;

        int actCol = this.actField.getColPos();
        int actRow = this.actField.getRowPos();
        Field.Direction checkDirection = null;
        switch(isDirectionCorrect(actCol, actRow, moveTo))
        {
            case -1:
                return -1;
            case 0:
                return 1;
            case 1:
                checkDirection = Field.Direction.D;
                break;
            case 2:
                checkDirection = Field.Direction.U;
                break;
            case 3:
                checkDirection = Field.Direction.L;
                break;
            case 4:
                checkDirection = Field.Direction.R;
                break;
        }


        if(!isMoveInDirectPoss(checkDirection, moveTo))
            return -1;


        if(!moveTo.isEmpty())
        {
            Figure figureOnMoveto = moveTo.get();
            moveTo.remove(figureOnMoveto);
        }
        this.actField.remove(this);
        moveTo.put(this);

        return 1;
    }

    private boolean isMoveInDirectPoss(Field.Direction dirs, Field moveTo)
    {
        if(!moveTo.isEmpty())
        {
            Figure figureOnMoveTo = moveTo.get();
            if(figureOnMoveTo.isWhite() == this.isRookWhite)
            {
                return false;
            }
        }

        int fieldDifference;
        if (dirs == Field.Direction.D || dirs == Field.Direction.U)
        {
            fieldDifference = (moveTo.getRowPos()) - this.actField.getRowPos();

        }
        else if (dirs == Field.Direction.R || dirs == Field.Direction.L)
        {
            fieldDifference = (moveTo.getColPos()) - this.actField.getColPos();
        }
        else
        {
            return false;
        }

        fieldDifference = Math.abs(fieldDifference);
        fieldDifference--;

        Field tmp = moveTo;
        for (int i = 0; i < fieldDifference; i++)
        {
            tmp = tmp.nextField(dirs);
            if(!(tmp.isEmpty()))
            {
                return false;
            }
        }

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
            if (row < moveTo.getRowPos())
            {
                return 1;       //check direction -> down from moveTO
            }
            else
            {
                return 2;       //check direction -> up from moveTO
            }
        }
        else if (row == moveTo.getRowPos())
        {
            if (col < moveTo.getColPos())
            {
                return 3;       //check direction -> left from moveTO
            }
            else
            {
                return 4;       //check direction -> right from moveTO
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

        return "V[" + color + "]" + this.actField.getColPos() + ":" + this.actField.getRowPos();
    }

}
