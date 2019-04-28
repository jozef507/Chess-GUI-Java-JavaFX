package sample.board;

public class Board
{
    private BoardField[][] board;

    public Board(int size)
    {
        board = new BoardField[size][size];

        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                board[i][j] = new BoardField(i+1,j+1);
            }
        }

        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                Field tmp;
                for (Field.Direction dirs : Field.myValues())
                {
                    tmp = chooseNextField(dirs, i, j, board, size);
                    board[i][j].addNextField(dirs, tmp);
                }
            }
        }
    }

    public int getSize ()
    {
        int rows = board.length;
        int columns = board[rows-1].length;

        if(rows == columns)
        {
            return rows;
        }
        else
        {
            return 0;
        }
    }

    public Field getField(int col, int row)
    {
        Field field = board[col-1][row-1];
        return field;
    }

    public Field chooseNextField(Field.Direction dirs, int col, int row, BoardField[][] board, int size)
    {
        int nextCol, nextRow;
        Field tmp;
        nextCol = getNextCol(dirs);
        nextRow = getNextRow(dirs);
        nextCol = col + nextCol;
        nextRow = row + nextRow;

        if(nextCol >= 0  &&  nextCol < size  &&  nextRow >=0  &&  nextRow < size)
        {
            tmp = this.board[nextCol][nextRow];
        }
        else
        {
            tmp = null;
        }

        return tmp;
    }

    public int getNextCol (Field.Direction dirs)
    {
        int newCol;
        switch (dirs)
        {
            case D:
                newCol = 0;
                break;
            case L:
                newCol = -1;
                break;
            case U:
                newCol = 0;
                break;
            case R:
                newCol = 1;
                break;
            case LD:
                newCol = -1;
                break;
            case LU:
                newCol = -1;
                break;
            case RD:
                newCol = 1;
                break;
            case RU:
                newCol = 1;
                break;
            default:
                newCol = 2;
                break;
        }
        return newCol;
    }

    public int getNextRow (Field.Direction dirs)
    {
        int newRow;
        switch (dirs)
        {
            case D:
                newRow = -1;
                break;
            case L:
                newRow = 0;
                break;
            case U:
                newRow = 1;
                break;
            case R:
                newRow = 0;
                break;
            case LD:
                newRow = -1;
                break;
            case LU:
                newRow = 1;
                break;
            case RD:
                newRow = -1;
                break;
            case RU:
                newRow = 1;
                break;
            default:
                newRow = 0;
                break;
        }
        return newRow;
    }
}
