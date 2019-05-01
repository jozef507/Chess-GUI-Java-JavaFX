package sample.game;
import javafx.scene.image.ImageView;
import sample.board.Field;
import sample.board.MovementManager;
import sample.figures.Figure;

import java.util.List;

public interface Game
{
    void nullMovementManager();
    boolean setPlayerMovement(int col, int row);
    boolean setPlaybackMovement();
    boolean performPlayerMovement();
    boolean performPlaybackMovement();
    void changePlayer();
    boolean isWhiteOnTheMove();
    Field getGoalField();
    MovementManager getMovementManager();
    boolean isMovementCompletlySet();
    boolean isRemovingFigure();
    boolean getIsChangingFigure();
    boolean createNewFigure(ImageView image, int id);


    ImageView getImageOfMovFigure();
    ImageView getImageOfGoalFieldFigure();


    List<String> getGameNotation();
    int getIndexOfGameNotation();
    void addPlayerNotationMovement();
    boolean saveNotation();
    int getChangingFigureID();
    void incrementIndexOfNotationLines();
    void decrementIndexOfNotationLines();
    boolean isFirstIndexOfNotation();
    boolean isLastIndexOfNotation();
    void completeNotationMovement();
    boolean performPlaybackUndoMovement();
    boolean setPlaybackUndoMovement();
    boolean isNotationRight();



}
