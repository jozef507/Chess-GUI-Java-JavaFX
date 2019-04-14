package sample.game;
import sample.figures.*;
import sample.board.*;


import java.util.ArrayList;
import java.util.List;

public class ChessGame implements Game
{
    public List<Figure> previousPositionsFigures = new ArrayList<Figure>();
    public List<Field> previousPositionsFields = new ArrayList<Field>();
    public List<Figure> previousPositionsRemovedFigures = new ArrayList<Figure>();

    public ChessGame(Board board)
    {
        putRookOnBoard(board,1,1, true);
        putRookOnBoard(board,8,1, true);

        putPawnOnBoard(board,1,2, true);
        putPawnOnBoard(board,2,2, true);
        putPawnOnBoard(board,3,2, true);
        putPawnOnBoard(board,4,2, true);
        putPawnOnBoard(board,5,2, true);
        putPawnOnBoard(board,6,2, true);
        putPawnOnBoard(board,7,2, true);
        putPawnOnBoard(board,8,2, true);


        putRookOnBoard(board,1,8, false);
        putRookOnBoard(board,8,8, false);

        putPawnOnBoard(board,1,7, false);
        putPawnOnBoard(board,2,7, false);
        putPawnOnBoard(board,3,7, false);
        putPawnOnBoard(board,4,7, false);
        putPawnOnBoard(board,5,7, false);
        putPawnOnBoard(board,6,7, false);
        putPawnOnBoard(board,7,7, false);
        putPawnOnBoard(board,8,7, false);
    }

    private void putRookOnBoard(Board board, int col, int row, boolean isRookWhite)
    {
        Field field = board.getField(col, row);
        if(field.isEmpty())
        {
            Rook rook = new Rook(isRookWhite);
            field.put(rook);
        }
    }

    private void putPawnOnBoard(Board board, int col, int row, boolean isPawnWhite)
    {
        Field field = board.getField(col, row);
        if(field.isEmpty())
        {
            Pawn pawn = new Pawn(isPawnWhite);
            field.put(pawn);
        }
    }


    public boolean move(Figure figure, Field moveTo)
    {
        Figure moveToFigure=null;
        if(!moveTo.isEmpty())
        {
            moveToFigure = moveTo.get();
        }

        Field figurePrevField = figure.getActField();
        if (!figure.move(moveTo))
        {
            return false;
        }

        previousPositionsFigures.add(figure);
        previousPositionsFields.add(figurePrevField);
        previousPositionsRemovedFigures.add(moveToFigure);

        return true;
    }

    public void undo()
    {
        Figure lastMovedFigure = previousPositionsFigures.get(previousPositionsFigures.size() - 1);
        previousPositionsFigures.remove(previousPositionsFigures.size() - 1);
        Field lastFieldOfMovedFigure = previousPositionsFields.get(previousPositionsFields.size() - 1);
        previousPositionsFields.remove(previousPositionsFields.size() - 1);
        Figure removedFigure = previousPositionsRemovedFigures.get(previousPositionsRemovedFigures.size() - 1);
        previousPositionsRemovedFigures.remove(previousPositionsRemovedFigures.size() - 1);

        Field actualField = lastMovedFigure.getActField();
        actualField.remove(lastMovedFigure);
        lastFieldOfMovedFigure.put(lastMovedFigure);

        if (removedFigure != null)
        {
            actualField.put(removedFigure);
        }
    }
}
