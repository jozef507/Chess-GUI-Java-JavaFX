package sample.figures;

import javafx.scene.image.ImageView;
import sample.board.Field;

import java.util.ArrayList;
import java.util.List;

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


    public ImageView getImage()
    {
        return this.image;
    }


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
            flag = -1;
        }

        return flag;
    }

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

    private void appendFieldsInDanger(Field.Direction dir)
    {
        Field nextField=this.actField.nextField(dir);
        if(nextField != null)
            this.fieldsInDanger.add(nextField);
    }

    public List<Field> getFieldsInDanger()
    {
        return this.fieldsInDanger;
    }

    public boolean canMove(List<Field> fieldsInDanger)
    {
        Field nextField;

        nextField = this.actField.nextField(Field.Direction.U);
        if(nextField != null && !fieldsInDanger.contains(nextField))
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
        if(nextField != null && !fieldsInDanger.contains(nextField))
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
        if(nextField != null && !fieldsInDanger.contains(nextField))
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
        if(nextField != null && !fieldsInDanger.contains(nextField))
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
        if(nextField != null && !fieldsInDanger.contains(nextField))
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
        if(nextField != null && !fieldsInDanger.contains(nextField))
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
        if(nextField != null && !fieldsInDanger.contains(nextField))
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
        if(nextField != null && !fieldsInDanger.contains(nextField))
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

    public void setInChess(boolean inChess) {
        this.inChess = inChess;
    }

    public boolean getInChess()
    {
        return this.inChess;
    }

    public void setChessBy(Figure figure)
    {
        this.chessBy = figure;
    }

    public Figure getChessBy ()
    {
        return this.chessBy;
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

    public List<Field> getFieldsForPossMov()
    {
        return this.fieldsInDanger;
    }

    public  List<Field> getFieldsInDangerChesMat()
    {
        return this.fieldsInDanger;
    }

}
