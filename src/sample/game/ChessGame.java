package sample.game;
import javafx.scene.image.ImageView;
import sample.figures.*;
import sample.board.*;



import java.util.ArrayList;
import java.util.List;

public class ChessGame implements Game
{
    private Board board;
    //Notation notation;
    private List<Figure> activeWhiteFigures;
    private List<Figure> activeBlackFigures;


    private boolean canPlayerPlay;
    private boolean isWhiteOnTheMove;
    //private boolean isFigureChoosen;
    private Field startField;
    private Figure movementFigure;
    private Field goalField;
    private Figure goalFieldFigure;


    public ChessGame(ImageView[] whiteFigureImages, ImageView[] blackFigureImages)
    {
        this.board = new Board(8);
        this.activeWhiteFigures = new ArrayList<Figure>();
        this.activeBlackFigures = new ArrayList<Figure>();

        this.canPlayerPlay = true;
        this.isWhiteOnTheMove = true;
        this.startField = null;
        this.movementFigure = null;
        this.goalField = null;
        this.goalFieldFigure = null;

        putPawnOnBoard(1, 2, true, whiteFigureImages[0]);
        putPawnOnBoard(2, 2, true, whiteFigureImages[1]);
        putPawnOnBoard(3, 2, true, whiteFigureImages[2]);
        putPawnOnBoard(4, 2, true, whiteFigureImages[3]);
        putPawnOnBoard(5, 2, true, whiteFigureImages[4]);
        putPawnOnBoard(6, 2, true, whiteFigureImages[5]);
        putPawnOnBoard(7, 2, true, whiteFigureImages[6]);
        putPawnOnBoard(8, 2, true, whiteFigureImages[7]);

        putPawnOnBoard(1, 7, false, blackFigureImages[0]);
        putPawnOnBoard(2, 7, false, blackFigureImages[1]);
        putPawnOnBoard(3, 7, false, blackFigureImages[2]);
        putPawnOnBoard(4, 7, false, blackFigureImages[3]);
        putPawnOnBoard(5, 7, false, blackFigureImages[4]);
        putPawnOnBoard(6, 7, false, blackFigureImages[5]);
        putPawnOnBoard(7, 7, false, blackFigureImages[6]);
        putPawnOnBoard(8, 7, false, blackFigureImages[7]);
    }

    private void putPawnOnBoard(int col, int row, boolean isPawnWhite, ImageView image)
    {
        Field field = this.board.getField(col, row);
        if(field.isEmpty())
        {
            Pawn pawn = new Pawn(isPawnWhite, image);
            field.put(pawn);
        }
    }

    public Field getGoalField()
    {
        return this.goalField;
    }

    public void changePlayer()
    {
        this.isWhiteOnTheMove = !isWhiteOnTheMove;
    }

    public void setCanPlayerPlay(boolean canPlayerPlay)
    {
        this.canPlayerPlay = canPlayerPlay;
    }

    public void nullMovementManager()
    {
        this.startField = null;
        this.movementFigure = null;
        this.goalField = null;
        this.goalFieldFigure = null;
    }

    public boolean setMovement(int col, int row)
    {
        if (!canPlayerPlay)
        {
            return false;
        }

        if(this.startField == null)
        {
            this.startField = this.board.getField(col, row);
            if(this.startField.isEmpty())
            {
                this.nullMovementManager();
                return false;
            }

            this.movementFigure = this.startField.get();
            if(this.movementFigure.isWhite() != this.isWhiteOnTheMove)
            {
                this.nullMovementManager();
                return false;
            }
            return true;
        }
        else
        {
            this.goalField = this.board.getField(col, row);
            if(!this.goalField.isEmpty())
                this.goalFieldFigure = goalField.get();
            return true;
        }
    }

    public int performMovement()
    {
        if(this.startField != null && this.movementFigure != null && this.goalField != null)
        {
           return (this.movementFigure.move(this.goalField));
        }
        else
        {
            return 0;
        }
    }

    public ImageView getImageOfMovFigure()
    {
        return this.movementFigure.getImage();
    }

    public ImageView getImageOfGoalFieldFigure()
    {
        return this.goalFieldFigure.getImage();
    }

    public boolean isWhiteOnTheMove()
    {
        return this.isWhiteOnTheMove;
    }
}
