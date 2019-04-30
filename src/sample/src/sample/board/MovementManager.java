package sample.board;

import javafx.scene.image.ImageView;
import sample.board.Board;
import sample.board.Field;
import sample.figures.Figure;
import sample.figures.FiguresManager;

public class MovementManager
{
    private boolean canPlayerPlay;
    private boolean isWhiteOnTheMove;
    private boolean isMovementCompletlySet;
    private boolean isRemovingFigure;
    private boolean isChangingFigure;

    private Field startField;
    private Figure movementFigure;
    private Field goalField;
    private Figure goalFieldFigure;

    public MovementManager()
    {

        this.canPlayerPlay = true;
        this.isWhiteOnTheMove = true;
        this.isMovementCompletlySet = false;
        this.isRemovingFigure = false;
        this.isChangingFigure = false;
        this.startField = null;
        this.movementFigure = null;
        this.goalField = null;
        this.goalFieldFigure = null;
    }

    public Field getGoalField() {
        return this.goalField;
    }

    public void changePlayer() {
        this.isWhiteOnTheMove = !isWhiteOnTheMove;
    }

    public void setCanPlayerPlay(boolean canPlayerPlay) {
        this.canPlayerPlay = canPlayerPlay;
    }

    public void nullMovementManager() {
        this.isMovementCompletlySet = false;
        this.isRemovingFigure = false;
        this.isChangingFigure = false;
        this.startField = null;
        this.movementFigure = null;
        this.goalField = null;
        this.goalFieldFigure = null;
    }

    public boolean setMovement(int col, int row, Board board) {
        if (!canPlayerPlay) {
            return false;
        }

        if(this.isChangingFigure)
        {
            return false;
        }

        if (this.startField == null) {
            this.startField = board.getField(col, row);
            if (this.startField.isEmpty()) {
                this.nullMovementManager();
                return false;
            }

            this.movementFigure = this.startField.get();
            if (this.movementFigure.isWhite() != this.isWhiteOnTheMove) {
                this.nullMovementManager();
                return false;
            }
            return true;
        } else {
            this.goalField = board.getField(col, row);
            if (!this.goalField.isEmpty())
                this.goalFieldFigure = goalField.get();
            this.isMovementCompletlySet = true;
            return true;
        }
    }

    public boolean performMovement(FiguresManager figuresManager) {
        if (this.startField != null && this.movementFigure != null && this.goalField != null)
        {
            int flag = this.movementFigure.move(this.goalField, figuresManager);
            if(flag==-1)
            {
                return false;
            }
            else if(flag == 1)
            {
                return true;
            }
            else if(flag == 2)
            {
                this.isRemovingFigure = true;
                return true;
            }
            else if(flag == 3)
            {
                this.isChangingFigure = true;
                return true;
            }
            else if(flag == 4)
            {
                this.isChangingFigure = true;
                this.isRemovingFigure = true;
                return true;
            }
            else
            {
                return false;
            }

        } else {
            return false;
        }
    }

    public Figure getMovementFigure() {
        return this.movementFigure;
    }

    public Figure getGoalFieldFigure() {
        return this.goalFieldFigure;
    }

    public boolean isWhiteOnTheMove() {
        return this.isWhiteOnTheMove;
    }

    public boolean isMovementCompletlySet() {return this.isMovementCompletlySet;}

    public boolean getIsRemovingFigure () {return this.isRemovingFigure;}

    public boolean getIsChangingFigure() {return this.isChangingFigure;}

    public Field getStartField () { return this.startField;}


}
