package sample.board;
import sample.figures.Figure;

/**
 * Rozhranie pre políčko na šachovnici.
 */
public interface Field
{
    /**
     * Vyčtový typ všetkých možných smerov na šachovnici.
     */
    enum Direction
    {
        D, L, LD, LU, R, RD, RU, U, NONE
    }

    /**
     * Spracuje pole všetkých smerov na šachovnici.
     * @return Dané pole.
     */
    static Direction[] myValues()
    {
        Direction[] directions = { Direction.D, Direction.L, Direction.LD, Direction.LU,
                Direction.R, Direction.RD, Direction.RU, Direction.U};

        return directions;
    }



    static Direction valueOf (String name) throws IllegalArgumentException,
            NullPointerException
    {
        if(name == null)
        {
            throw new NullPointerException();
        }

        if(name.equals(Direction.D.toString()))
            return Direction.D;
        else if(name.equals(Direction.L.toString()))
            return Direction.L;
        else if(name.equals(Direction.LD.toString()))
            return Direction.LD;
        else if(name.equals(Direction.LU.toString()))
            return Direction.LU;
        else if(name.equals(Direction.R.toString()))
            return Direction.R;
        else if(name.equals(Direction.RD.toString()))
            return Direction.RD;
        else if(name.equals(Direction.RU.toString()))
            return Direction.RU;
        else if(name.equals(Direction.U.toString()))
            return Direction.U;
        else
            throw new IllegalArgumentException();
    }


    /**
     * Vráti pozíciu stlpca na šachovnici tohto políčka.
     * @return Hodnotu slpca.
     */
    int getColPos();

    /**
     * Vráti pozíciu riadka na šachovnici tohto políčka.
     * @return Hodnotu riadka.
     */
    int getRowPos();

    /**
     * Pridáva políčku susedné políčko v smere na šachovnici.
     * @param dirs Smer na šachovnici.
     * @param field Plíčko ktoré prídá ako susedné.
     */
    void addNextField(Direction dirs, Field field);

    /**
     * Vráti figúrku ležiacu na danom políčku.
     * @return Odkaz na figúrku.
     */
    Figure get();

    /**
     * Testovanie či je dané políčko prázdne.
     * @return Úspešnosť testu.
     */
    boolean isEmpty();

    /**
     * Vracia susedné políčko tohto políčka v danom smere.
     * @param dirs Smer na šachovnici.
     * @return Odkaz na políčko.
     */
    Field nextField(Direction dirs);

    /**
     * Vloží danú figúrku na toto políčko a testuje úspešnosť tohto úkonu.
     * @param figure Figúrka ktorá má byť vložená na políčko.
     * @return Úspešnosť testu.
     */
    boolean put(Figure figure);

    /**
     * Odstráni danú figúrku z tohto políčka a testuje úspešnosť tohto úkonu.
     * @param figure Figúrka ktorá má byť odstránená z políčka.
     * @return Úspešnosť testu.
     */
    boolean remove(Figure figure);

}
