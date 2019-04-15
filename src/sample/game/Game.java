package sample.game;
import javafx.scene.image.ImageView;
import sample.board.Field;
import sample.figures.Figure;

public interface Game
{
    void nullMovementManager();
    boolean setMovement(int col, int row);
    int performMovement();
    ImageView getImageOfMovFigure();
    ImageView getImageOfGoalFieldFigure();
    void changePlayer();
    Field getGoalField();
    boolean isWhiteOnTheMove();

}
