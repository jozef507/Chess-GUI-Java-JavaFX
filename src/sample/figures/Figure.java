package sample.figures;
import javafx.scene.image.ImageView;
import sample.board.Field;


public interface Figure
{
    boolean isWhite();
    int move(Field moveTo);
    int hashCode();
    boolean equals(Object obj);
    void setActualPosition(Field field);
    void nulActualPosition();
    Field getActField();
    String getState();
    ImageView getImage();

}
