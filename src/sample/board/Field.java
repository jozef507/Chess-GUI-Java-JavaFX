package sample.board;
import sample.figures.Figure;

public interface Field
{
    enum Direction
    {
        D, L, LD, LU, R, RD, RU, U, NONE

    }


    static Direction[] myValues()
    {
        Direction[] directions = { Direction.D, Direction.L, Direction.LD, Direction.LU,
                Direction.R, Direction.RD, Direction.RU, Direction.U};

        return directions;
    }





    static Direction valueOf (String name) throws IllegalArgumentException,
            NullPointerException
    {
        if(name == null)
        {
            throw new NullPointerException();
        }

        if(name.equals(Direction.D.toString()))
            return Direction.D;
        else if(name.equals(Direction.L.toString()))
            return Direction.L;
        else if(name.equals(Direction.LD.toString()))
            return Direction.LD;
        else if(name.equals(Direction.LU.toString()))
            return Direction.LU;
        else if(name.equals(Direction.R.toString()))
            return Direction.R;
        else if(name.equals(Direction.RD.toString()))
            return Direction.RD;
        else if(name.equals(Direction.RU.toString()))
            return Direction.RU;
        else if(name.equals(Direction.U.toString()))
            return Direction.U;
        else
            throw new IllegalArgumentException();
    }


    public int getColPos();
    public int getRowPos();
    public void addNextField(Direction dirs, Field field);
    public Figure get();
    public boolean isEmpty();
    public Field nextField(Direction dirs);
    public boolean put(Figure figure);
    public boolean remove(Figure figure);

}
