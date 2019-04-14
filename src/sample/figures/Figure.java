package sample.figures;
import sample.board.Field;

public interface Figure
{
    boolean isWhite();
    boolean move(Field moveTo);
    int hashCode();
    boolean equals(Object obj);
    void setActualPosition(Field field);
    void nulActualPosition();
    Field getActField();
    String getState();

}
