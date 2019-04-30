package sample.board;
import sample.figures.Figure;

public class BoardField implements Field
{
    private int row;
    private int col;
    private Field[] nextFields;
    private Figure figure;

    public BoardField(int col, int row)
    {
        this.col = col;
        this.row = row;
        this.nextFields = new BoardField[8];
        this.figure = null;
    }

    public int getColPos()
    {
        return this.col;
    }

    public int getRowPos()
    {
        return this.row;
    }

    public void addNextField(Direction dirs, Field field)
    {
        nextFields[dirs.ordinal()] = field;
    }

    public Field nextField(Direction dirs)
    {
        Field tmp = nextFields[dirs.ordinal()];
        return tmp;
    }


    public boolean isEmpty()
    {
        if(this.figure == null)
            return true;
        else
            return false;
    }

    public Figure get()
    {
        Figure figure = this.figure;
        if (isEmpty())
        {
            return null;
        }
        else
        {
            return figure;
        }
    }

    public boolean put(Figure figure)
    {
        if(isEmpty())
        {
            this.figure = figure;
            figure.setActualPosition(this);
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean remove(Figure figure)
    {
        if(isEmpty())
        {
            return false;
        }
        else
        {
            if(this.figure.equals(figure))
            {
                this.figure = null;
                figure.nulActualPosition();
                return true;
            }
            else
            {
                return false;
            }
        }
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
}
