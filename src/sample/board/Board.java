package sample.board;

/**
 * Trieda reprezentujúca šachovnicu. Šachovnica je reprezentovaná
 * dvojrozmerným poľom inštancii triedy/rozhrania Field.
 */
public class Board
{
    private BoardField[][] board;

    /**
     * Inicializuje šachovnicu.
     * @param size Počet riadov/stlpcov poličok šachovnice.
     */
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
                    tmp = chooseNextField(dirs, i, j, size);
                    board[i][j].addNextField(dirs, tmp);
                }
            }
        }
    }

    /**
     * Vracia veľkosť šachovnice.
     * @return Veľkosť šachovnice.
     */
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

    /**
     * Vracia odkaz na poličko (Field) na základe jeho pozície.
     * @param col Stlpec pozície políčka.
     * @param row Riadok pozície políčka.
     * @return Odkaz na políčko(Field).
     */
    public Field getField(int col, int row)
    {
        Field field = board[col-1][row-1];
        return field;
    }

    /**
     * Vracia odkaz na susedné políčko políčka určeného jeho pozíciou.
     * @param dirs Smer susedného políčka.
     * @param col Stlpec pozície aktuálneho políčka.
     * @param row Riadok pozície aktuálneho políčka.
     * @param size Veľkosť šachovnice.
     * @return Odkaz na susedné políčko (Field).
     */
    public Field chooseNextField(Field.Direction dirs, int col, int row, int size)
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

    /**
     * Vráti susedný stlpec na šachovnici.
     * @param dirs Smer na šachovnici.
     * @return Celočíselnu hodnotu o úspechu.
     */
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

    /**
     * Vráti susedný riadok na šachovnici.
     * @param dirs Smer na šachovnici.
     * @return Celočíselnu hodnotu o úspechu.
     */
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
