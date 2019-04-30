package sample.game;
import javafx.scene.image.ImageView;
import sample.board.Field;
import sample.figures.Figure;

import java.util.List;

public interface Game
{
    void nullMovementManager();
    boolean setMovement(int col, int row);
    boolean performMovement();
    void changePlayer();
    boolean isWhiteOnTheMove();
    void setCanPlayerPlay(boolean canPlayerPlay);
    Field getGoalField();
    boolean isMovementCompletlySet();
    boolean isRemovingFigure();
    boolean getChess();
    boolean getChessMat();
    boolean getIsChangingFigure ();
    boolean createNewFigure(ImageView image, int id);

    ImageView getImageOfMovFigure();
    ImageView getImageOfGoalFieldFigure();


    List<String> getGameNotation();
    int getIndexOfGameNotation();
    void addPlayerNotationMovement();

}
