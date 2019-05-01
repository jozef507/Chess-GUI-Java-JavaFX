package sample.board;

import javafx.scene.image.ImageView;
import sample.board.Board;
import sample.board.Field;
import sample.figures.Figure;
import sample.figures.FiguresManager;
import sample.notation.Notation;
import sample.notation.NotationMovement;

import java.util.ArrayList;
import java.util.List;

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
    private Figure changingFigure;

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
        this.changingFigure = null;
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

    public boolean setPlayerMovement(int col, int row, Board board) {
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


    public boolean setPlaybackMovement(Board board, Notation notation, FiguresManager figuresManager) {

        if(this.isChangingFigure)
            return false;

        NotationMovement mov = notation.getActualNotMov();

        this.goalField = board.getField(mov.getGoalFieldCol(), mov.getGoalFieldRow());
        if (!this.goalField.isEmpty())
            this.goalFieldFigure = goalField.get();

        if((mov.getIsLongNotation()) || (!mov.getIsLongNotation() && mov.getStartFieldCol() != -1 && mov.getStartFieldRow() != -1))
        {
            this.startField = board.getField(mov.getStartFieldCol(),mov.getStartFieldRow());
        }
        else
        {
            int movementFigureID = mov.getMovementFigureID();
            List<Figure> activeFigures;
            if(this.isWhiteOnTheMove)
                activeFigures = figuresManager.getActiveWhiteFigures();
            else
                activeFigures = figuresManager.getActiveBlackFigures();

            List<Figure> tmpFigures = new ArrayList<Figure>();
            int ID = -1;
            List<Field> tmpFields;
            for (int i = 0; i<activeFigures.size(); i++)
            {
                ID = activeFigures.get(i).getID();
                tmpFields = activeFigures.get(i).getFieldsForPossMov();
                if( ID== movementFigureID
                    && tmpFields.contains(this.goalField))
                {
                    tmpFigures.add(activeFigures.get(i));
                }
            }

            if(tmpFigures.size()==1)
                this.startField = tmpFigures.get(0).getActField();
            else if(tmpFigures.size()>1)
            {
                if(mov.getStartFieldCol() != -1)
                {
                    for (int i = 0; i<tmpFigures.size(); i++)
                    {
                        if(mov.getStartFieldCol() == tmpFigures.get(i).getActField().getColPos())
                        {
                            this.startField = tmpFigures.get(i).getActField();
                            break;
                        }
                    }
                }
                else if(mov.getStartFieldRow() != -1)
                {
                    for (int i = 0; i<tmpFigures.size(); i++)
                    {
                        if(mov.getStartFieldRow() == tmpFigures.get(i).getActField().getRowPos())
                        {
                            this.startField = tmpFigures.get(i).getActField();
                            break;
                        }
                    }
                }
            }
        }

        if (this.startField.isEmpty()) {
            this.nullMovementManager();
            return false;
        }

        this.movementFigure = this.startField.get();
        if (this.movementFigure.isWhite() != this.isWhiteOnTheMove) {
            this.nullMovementManager();
            return false;
        }

        this.isMovementCompletlySet = true;
        return true;
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

    public boolean setPlaybackUndoMovement(NotationMovement mov, Board board, FiguresManager figuresManager)
    {
        this.startField = board.getField(mov.getStartFieldCol(), mov.getStartFieldRow());
        this.goalField = board.getField(mov.getGoalFieldCol(), mov.getGoalFieldRow());
        if(mov.getChangingFigureID()!=-1)
        {
            this.movementFigure = figuresManager.getLastChangedFigure();
            figuresManager.removeLastChangedFigure();
            this.changingFigure = this.goalField.get();
            this.isChangingFigure = true;
        }
        else
        {
            this.movementFigure = goalField.get();
        }


        this.isWhiteOnTheMove = this.movementFigure.isWhite();


        if(mov.getIsFigureRemoving())
        {
            this.goalFieldFigure = figuresManager.getLastRemovedFigure();
            figuresManager.removeLastRemovedFigure();
            this.isRemovingFigure = true;
        }

        this.isWhiteOnTheMove = this.movementFigure.isWhite();
        return true;
    }

    public boolean performPlaybackUndoMovement(NotationMovement mov, FiguresManager figuresManager)
    {
        if(this.isChangingFigure)
        {
            this.goalField.remove(this.changingFigure);
            figuresManager.removeActiveFigure(this.changingFigure);
        }
        else
        {
            this.goalField.remove(this.movementFigure);
        }

        this.startField.put(this.movementFigure);

        if(this.isRemovingFigure)
        {
            this.goalField.put(this.goalFieldFigure);
        }

        figuresManager.setChessMat(false);
        return true;
    }

    public Figure getChangingFigure()
    {
        return this.changingFigure;
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
