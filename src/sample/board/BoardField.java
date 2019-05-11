/**
 * Trieda reprezentujúca poličko šachovnice.
 * @author Ján Folenta
 * @author Jozef Ondria
 */

package sample.board;
import sample.figures.Figure;

/**
 * Trieda reprezentujúca jedno políčko na šachovníci.
 * Implementuje rozhranie Field.
 */
public class BoardField implements Field
{
    private int row;
    private int col;
    private Field[] nextFields;
    private Figure figure;

    /**
     * Inicializuje políčko.
     * @param col Stlpec políčka na šachovnici.
     * @param row Riadok políčka na šachovnici.
     */
    public BoardField(int col, int row)
    {
        this.col = col;
        this.row = row;
        this.nextFields = new BoardField[8];
        this.figure = null;
    }

    /**
     * Vráti pozíciu stlpca na šachovnici tohto políčka.
     * @return Hodnotu slpca.
     */
    public int getColPos()
    {
        return this.col;
    }

    /**
     * Vráti pozíciu riadka na šachovnici tohto políčka.
     * @return Hodnotu riadka.
     */
    public int getRowPos()
    {
        return this.row;
    }

    /**
     * Pridáva políčku susedné políčko v smere na šachovnici.
     * @param dirs Smer na šachovnici.
     * @param field Plíčko ktoré prídá ako susedné.
     */
    public void addNextField(Direction dirs, Field field)
    {
        nextFields[dirs.ordinal()] = field;
    }

    /**
     * Vracia susedné políčko tohto políčka v danom smere.
     * @param dirs Smer na šachovnici.
     * @return Odkaz na políčko.
     */
    public Field nextField(Direction dirs)
    {
        Field tmp = nextFields[dirs.ordinal()];
        return tmp;
    }

    /**
     * Testovanie či je dané políčko prázdne.
     * @return Úspešnosť testu.
     */
    public boolean isEmpty()
    {
        if(this.figure == null)
            return true;
        else
            return false;
    }

    /**
     * Vráti figúrku ležiacu na danom políčku.
     * @return Odkaz na figúrku.
     */
    public Figure get()
    {
        Figure figure = this.figure;
        if (isEmpty())
        {
            return null;
        }
        else
        {
            return figure;
        }
    }

    /**
     * Vloží danú figúrku na toto políčko a testuje úspešnosť tohto úkonu.
     * @param figure Figúrka ktorá má byť vložená na políčko.
     * @return Úspešnosť testu.
     */
    public boolean put(Figure figure)
    {
        if(isEmpty())
        {
            this.figure = figure;
            figure.setActualPosition(this);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Odstráni danú figúrku z tohto políčka a testuje úspešnosť tohto úkonu.
     * @param figure Figúrka ktorá má byť odstránená z políčka.
     * @return Úspešnosť testu.
     */
    public boolean remove(Figure figure)
    {
        if(isEmpty())
        {
            return false;
        }
        else
        {
            if(this.figure.equals(figure))
            {
                this.figure = null;
                figure.nulActualPosition();
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return super.equals(obj);
    }
}
