package sample.figures;
import javafx.scene.image.ImageView;
import sample.board.Field;

import java.util.List;


public interface Figure
{
    boolean isWhite();
    int move(Field moveTo, FiguresManager figuresManager);
    int hashCode();
    boolean equals(Object obj);
    void setActualPosition(Field field);
    void nulActualPosition();
    Field getActField();

    ImageView getImage();
    void setFieldsInDanger();
    List<Field> getFieldsInDanger();
    List<Field> getFieldsForPossMov();
    List<Field> getFieldsInDangerChesMat();
    int getID();
    List<Field> getFieldsOfDirectionToField(Field field);



}
