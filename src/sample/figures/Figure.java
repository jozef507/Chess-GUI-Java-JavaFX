package sample.figures;
import javafx.scene.image.ImageView;
import sample.board.Field;

import java.util.List;

/**
 * Rozhranie reprezentujúce figúrku.
 */
public interface Figure
{
    /**
     * Vracia informáciu o tom či je figúrka biela. Ak nie je čierna.
     * @return Pravdivostna hodnota.
     */
    boolean isWhite();

    /**
     * Vykonáva ťah figúrky a zároveń testuje či je ťah možný.
     * @param moveTo Cieľove políčko ťahu.
     * @param figuresManager Manažer aktívnych figúrok.
     * @return Stav úspešnosti ťahu.
     */
    int move(Field moveTo, FiguresManager figuresManager);

    /**
     * Nastaví aktuálnu pozíciu figúrky.
     * @param field Políčko pozície na ktorú sa má figurka nastaviť.
     */
    void setActualPosition(Field field);

    /**
     * Vynulovanie/zrušenie aktualnej pozície figúrky.
     */
    void nulActualPosition();

    /**
     * Vráti pozíciu aktualnej pozície figúrky.
     * @return Odkaz na políčko.
     */
    Field getActField();

    /**
     * Vracia odkaz na obrázok figúrky používajucí v grafickom rozhraní.
     * @return Odkaz na obrázok.
     */
    ImageView getImage();

    /**
     * Nastaví pre figúrku všetky políčka ktoré táto figúrka ohrozuej.
     */
    void setFieldsInDanger();

    /**
     * Vracia štruktúru políčok ktoré figúrka ohrozuje.
     * @return Štruktúra políčok.
     */
    List<Field> getFieldsInDanger();

    /**
     * Vracia štruktúru všetkých políčok ktorými môže ohrozovať súperoveho kráľa.
     * @return Štruktúra políčok.
     */
    List<Field> getFieldsForPossMov();

    /**
     * Vracia štruktúru všetkých políčok ktorými môže figúrka vytvárať šachmat.
     * @return Štruktúra políčok.
     */
    List<Field> getFieldsInDangerChesMat();

    /**
     * Vracia id figúrky.
     * @return ID figúrky.
     */
    int getID();

    /**
     * Vráti štruktúru všetkych políčok smerujúcich k danému fieldu v parametri.
     * @param field Odkaz na field.
     * @return Štruktúra políčok.
     */
    List<Field> getFieldsOfDirectionToField(Field field);

    int hashCode();

    boolean equals(Object obj);



}
