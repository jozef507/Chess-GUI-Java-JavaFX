package sample.game;
import javafx.scene.image.ImageView;
import sample.figures.*;
import sample.board.*;
import sample.notation.Notation;
import sample.gui.GuiGameFactory;


import java.util.ArrayList;
import java.util.List;

public class ChessGame implements Game {
    private Board board;
    private MovementManager movementManager;
    private FiguresManager figuresManager;
    private Notation notation;


    public ChessGame(ImageView[] whiteFigureImages, ImageView[] blackFigureImages) {
        this.board = new Board(8);
        this.movementManager = new MovementManager();
        this.figuresManager = new FiguresManager();
        this.notation = new Notation(GuiGameFactory.stringFilepathArray[GuiGameFactory.counter++]);


        putPawnOnBoard(1, 2, true, whiteFigureImages[0]);
        putPawnOnBoard(2, 2, true, whiteFigureImages[1]);
        putPawnOnBoard(3, 2, true, whiteFigureImages[2]);
        putPawnOnBoard(4, 2, true, whiteFigureImages[3]);
        putPawnOnBoard(5, 2, true, whiteFigureImages[4]);
        putPawnOnBoard(6, 2, true, whiteFigureImages[5]);
        putPawnOnBoard(7, 2, true, whiteFigureImages[6]);
        putPawnOnBoard(8, 2, true, whiteFigureImages[7]);

        putRookOnBoard(1, 1, true, whiteFigureImages[8]);
        putRookOnBoard(8, 1, true, whiteFigureImages[9]);

        putKnightOnBoard(2, 1, true, whiteFigureImages[10]);
        putKnightOnBoard(7, 1, true, whiteFigureImages[11]);

        putBishopOnBoard(3, 1, true, whiteFigureImages[12]);
        putBishopOnBoard(6, 1, true, whiteFigureImages[13]);

        putQueenOnBoard(4, 1, true, whiteFigureImages[14]);

        putKingOnBoard(5, 1, true, whiteFigureImages[15]);




        putPawnOnBoard(1, 7, false, blackFigureImages[0]);
        putPawnOnBoard(2, 7, false, blackFigureImages[1]);
        putPawnOnBoard(3, 7, false, blackFigureImages[2]);
        putPawnOnBoard(4, 7, false, blackFigureImages[3]);
        putPawnOnBoard(5, 7, false, blackFigureImages[4]);
        putPawnOnBoard(6, 7, false, blackFigureImages[5]);
        putPawnOnBoard(7, 7, false, blackFigureImages[6]);
        putPawnOnBoard(8, 7, false, blackFigureImages[7]);

        putRookOnBoard(1, 8, false, blackFigureImages[8]);
        putRookOnBoard(8, 8, false, blackFigureImages[9]);

        putKnightOnBoard(2, 8, false, blackFigureImages[10]);
        putKnightOnBoard(7, 8, false, blackFigureImages[11]);

        putBishopOnBoard(3, 8, false, blackFigureImages[12]);
        putBishopOnBoard(6, 8, false, blackFigureImages[13]);

        putQueenOnBoard(4, 8, false, blackFigureImages[14]);

        putKingOnBoard(5, 8, false, blackFigureImages[15]);
    }

    private void putPawnOnBoard(int col, int row, boolean isPawnWhite, ImageView image) {
        Field field = this.board.getField(col, row);
        if (field.isEmpty()) {
            Pawn pawn = new Pawn(isPawnWhite, image);
            field.put(pawn);
            this.figuresManager.addActiveFigure(pawn);
        }
    }

    private void putRookOnBoard(int col, int row, boolean isRookWhite, ImageView image) {
        Field field = this.board.getField(col, row);
        if (field.isEmpty()) {
            Rook rook = new Rook(isRookWhite, image);
            field.put(rook);
            this.figuresManager.addActiveFigure(rook);
        }
    }

    private void putKnightOnBoard(int col, int row, boolean isRookWhite, ImageView image) {
        Field field = this.board.getField(col, row);
        if (field.isEmpty()) {
            Knight knight = new Knight(isRookWhite, image);
            field.put(knight);
            this.figuresManager.addActiveFigure(knight);
        }
    }

    private void putBishopOnBoard(int col, int row, boolean isRookWhite, ImageView image) {
        Field field = this.board.getField(col, row);
        if (field.isEmpty()) {
            Bishop bishop = new Bishop(isRookWhite, image);
            field.put(bishop);
            this.figuresManager.addActiveFigure(bishop);
        }
    }

    private void putQueenOnBoard(int col, int row, boolean isRookWhite, ImageView image) {
        Field field = this.board.getField(col, row);
        if (field.isEmpty()) {
            Queen queen = new Queen(isRookWhite, image);
            field.put(queen);
            this.figuresManager.addActiveFigure(queen);
        }
    }
    private void putKingOnBoard(int col, int row, boolean isRookWhite, ImageView image) {
        Field field = this.board.getField(col, row);
        if (field.isEmpty()) {
            King king = new King(isRookWhite, image);
            field.put(king);
            this.figuresManager.addActiveFigure(king);
            this.figuresManager.setKing(king);
        }
    }

    public Field getGoalField() { return this.movementManager.getGoalField(); }

    public void changePlayer() { this.movementManager.changePlayer(); }

    public void setCanPlayerPlay(boolean canPlayerPlay) { this.movementManager.setCanPlayerPlay(canPlayerPlay); }

    public void nullMovementManager() { this.movementManager.nullMovementManager(); }

    public boolean setMovement(int col, int row) { return this.movementManager.setMovement(col, row, this.board); }

    public boolean performMovement()
    {
        boolean flag = this.movementManager.performMovement(this.figuresManager);
        return flag;
    }

    public boolean isWhiteOnTheMove() {return this.movementManager.isWhiteOnTheMove(); }

    public ImageView getImageOfMovFigure() { return this.movementManager.getMovementFigure().getImage();}

    public ImageView getImageOfGoalFieldFigure() { return this.movementManager.getGoalFieldFigure().getImage();}

    public List<String> getGameNotation(){ return this.notation.getGameNotationLines(); }

    public boolean isMovementCompletlySet() {return this.movementManager.isMovementCompletlySet();}

    public boolean isRemovingFigure() {return this.movementManager.getIsRemovingFigure();}

    public boolean getChess() {return this.figuresManager.getChess();}

    public boolean getChessMat() {return this.figuresManager.getChessMat();}

    public boolean getIsChangingFigure () { return this.movementManager.getIsChangingFigure();}

    public boolean createNewFigure(ImageView image, int id)
    {
        switch (id)
        {
            case 4:
                setChangedFigureField();
                putRookOnBoard(this.movementManager.getGoalField().getColPos(), this.movementManager.getGoalField().getRowPos(),
                        this.movementManager.isWhiteOnTheMove(), image);
                return this.figuresManager.updateFigures(this.movementManager.isWhiteOnTheMove(), this.movementManager.getGoalField().get(),
                        null);
            case 3:
                setChangedFigureField();
                putKnightOnBoard(this.movementManager.getGoalField().getColPos(), this.movementManager.getGoalField().getRowPos(),
                        this.movementManager.isWhiteOnTheMove(), image);
                return this.figuresManager.updateFigures(this.movementManager.isWhiteOnTheMove(), this.movementManager.getGoalField().get(),
                        null);
            case 2:
                setChangedFigureField();
                putBishopOnBoard(this.movementManager.getGoalField().getColPos(), this.movementManager.getGoalField().getRowPos(),
                        this.movementManager.isWhiteOnTheMove(), image);
                return this.figuresManager.updateFigures(this.movementManager.isWhiteOnTheMove(), this.movementManager.getGoalField().get(),
                        null);
            case 1:
                setChangedFigureField();
                putQueenOnBoard(this.movementManager.getGoalField().getColPos(), this.movementManager.getGoalField().getRowPos(),
                        this.movementManager.isWhiteOnTheMove(), image);
                return this.figuresManager.updateFigures(this.movementManager.isWhiteOnTheMove(), this.movementManager.getGoalField().get(),
                        null);
            default:
                return false;
        }
    }

    private void setChangedFigureField()
    {
        Field field = this.movementManager.getGoalField();
        Figure changedFigure = field.get();
        field.remove(changedFigure);
        this.figuresManager.addChangedFigure(changedFigure);
    }
}
