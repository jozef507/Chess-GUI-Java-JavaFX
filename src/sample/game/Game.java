package sample.game;
import sample.board.Field;
import sample.figures.Figure;

public interface Game
{
    boolean move(Figure figure, Field moveTo);
    void undo();
}
