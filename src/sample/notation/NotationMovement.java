/**
 * Trieda reprezentujúca notáciu jedneho ťahu hry/partie.
 * @author Ján Folenta
 * @author Jozef Ondria
 */

package sample.notation;

import sample.board.Field;
import sample.board.MovementManager;
import sample.figures.Figure;
import sample.figures.FiguresManager;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Trieda reprezentujúca jeden ťah a hlavne jeho informácii získanych z notácie šachovej partie.
 * Pri vlastných ťahoch užívateľa sa vytvarajú nové ťahy notácie.
 */
public class NotationMovement
{
    private boolean isLongNotation;

    private int startFieldCol;
    private int startFieldRow;
    private int movementFigureID;
    private int goalFieldCol;
    private int goalFieldRow;
    private int changingFigureID;

    private boolean isFigureRemoving;
    private boolean isChess;
    private boolean isChessMat;

    /**
     * Inicializuje ťah notácie.
     */
    public NotationMovement()
    {
        this.startFieldCol = -1;
        this.startFieldRow = -1;
        this.movementFigureID = -1;
        this.goalFieldCol = -1;
        this.goalFieldRow = -1;
        this.changingFigureID = -1;
    }

    /**
     * Nastaví ťah notácie z uživateľoveho ťahu. Je to dôležite pre budúce volanie ťahov spať.
     * @param isLongNotation Informacia či ide o krátku/dlhú formu zápisu.
     * @param figuresManager Manažer aktivných/pasívnych figúrok.
     * @param movementManager Manažer realneo ťahu.
     */
    public void setNotationMovementFromUserMov(boolean isLongNotation, FiguresManager figuresManager, MovementManager movementManager)
    {
        this.isLongNotation = isLongNotation;
        this.startFieldCol = movementManager.getStartField().getColPos();
        this.startFieldRow = movementManager.getStartField().getRowPos();
        this.movementFigureID = movementManager.getMovementFigure().getID();
        this.goalFieldCol = movementManager.getGoalField().getColPos();
        this.goalFieldRow = movementManager.getGoalField().getRowPos();
        if(movementManager.getIsChangingFigure())
            this.changingFigureID = movementManager.getGoalField().get().getID();
        this.isFigureRemoving = movementManager.getIsRemovingFigure();
        this.isChessMat = figuresManager.getChessMat();
        if(this.isChessMat)
            this.isChess = false;
        else
            this.isChess = figuresManager.getChess();

    }

    /**
     * Nastavuje ťah notácie zo zápisu z notácie.
     * @param s Reťazec zápisu ťahu.
     * @return Úspešnosť operácie.
     */
    public boolean setNotationMovementFromString(String s)
    {
        if(s.matches("[KDVSJ]?[abcdefgh][12345678][x]?[abcdefgh][12345678][VDSJ]?[+#]?"))
        {
            this.isLongNotation = true;

            if(s.matches("[a-zA-Z_0-9]+[#]"))
            {
                this.isChessMat = true;
                s = s.replace("#", "");
            }
            else
                this.isChessMat = false;


            if(s.matches("[a-zA-Z_0-9]+[+]"))
            {
                this.isChess = true;
                s = s.replace("+", "");
            }
            else
                this.isChess = false;

            if(s.matches("[a-zA-Z_0-9]*[x][a-zA-Z_0-9]+")) {
                this.isFigureRemoving = true;
                s = s.replace("x", "");
            }
            else
                this.isFigureRemoving = false;


            String figure = null;
            Pattern pattern;
            Matcher matcher;
            if(s.matches("[KDVSJ][a-zA-Z_0-9]+"))
            {
                figure = null;
                pattern = Pattern.compile("([KDVSJ])\\w+");
                matcher = pattern.matcher(s);
                if (matcher.find())
                    figure = matcher.group(1);

                this.movementFigureID = getFigureID(figure);
            }
            else
                this.movementFigureID = getFigureID("p");


            if(s.matches("[a-zA-Z_0-9]+[VDSJ]"))
            {
                figure = null;
                pattern = Pattern.compile("\\w+([VDSJ])");
                matcher = pattern.matcher(s);
                if (matcher.find())
                {
                    figure = matcher.group(1);
                }
                this.changingFigureID = getFigureID(figure);
            }
            else
                this.changingFigureID = -1;


            if(s.matches("[KDVSJ]?[abcdefgh][12345678][abcdefgh][12345678][VDSJ]?"))
            {
                figure = null;
                pattern = Pattern.compile("[KDVSJ]?([abcdefgh])[12345678][abcdefgh][12345678][VDSJ]?");
                matcher = pattern.matcher(s);
                if (matcher.find())
                    figure = matcher.group(1);

                this.startFieldCol = getIntFieldColumn(figure);

                pattern = Pattern.compile("[KDVSJ]?[abcdefgh]([12345678])[abcdefgh][12345678][VDSJ]?");
                matcher = pattern.matcher(s);
                if (matcher.find())
                    figure = matcher.group(1);

                if(figure!=null)
                    this.startFieldRow = Integer.parseInt(figure);
                else
                    this.startFieldRow = -1;


                pattern = Pattern.compile("[KDVSJ]?[abcdefgh][12345678]([abcdefgh])[12345678][VDSJ]?");
                matcher = pattern.matcher(s);
                if (matcher.find())
                    figure = matcher.group(1);

                this.goalFieldCol = getIntFieldColumn(figure);

                pattern = Pattern.compile("[KDVSJ]?[abcdefgh][12345678][abcdefgh]([12345678])[VDSJ]?");
                matcher = pattern.matcher(s);
                if (matcher.find())
                    figure = matcher.group(1);

                if(figure!=null)
                    this.goalFieldRow = Integer.parseInt(figure);
                else
                    this.startFieldRow = -1;
            }
            else
            {
                return false;
            }
        }
        else if(s.matches("[KDVSJ]?[abcdefgh12345678]?[x]?[abcdefgh][12345678][VDSJ]?[+#]?"))
        {
            this.isLongNotation = false;

            if(s.matches("[a-zA-Z_0-9]+[#]"))
            {
                this.isChessMat = true;
                s = s.replace("#", "");
            }
            else
                this.isChessMat = false;

            if(s.matches("[a-zA-Z_0-9]+[+]"))
            {
                this.isChess = true;
                s = s.replace("+", "");
            }
            else
                this.isChess = false;


            if(s.matches("[a-zA-Z_0-9]*[x][a-zA-Z_0-9]+"))
            {
                this.isFigureRemoving = true;
                s = s.replace("x", "");
            }
            else
                this.isFigureRemoving = false;


            String figure = null;
            Pattern pattern;
            Matcher matcher;
            if(s.matches("[KDVSJ][a-zA-Z_0-9]+"))
            {
                figure = null;
                pattern = Pattern.compile("([KDVSJ])\\w+");
                matcher = pattern.matcher(s);
                if (matcher.find())
                    figure = matcher.group(1);

                this.movementFigureID = getFigureID(figure);
            }
            else
                this.movementFigureID = getFigureID("p");

            if(s.matches("[a-zA-Z_0-9]+[VDSJ]"))
            {
                figure = null;
                pattern = Pattern.compile("\\w+([VDSJ])");
                matcher = pattern.matcher(s);
                if (matcher.find())
                    figure = matcher.group(1);
                this.changingFigureID = getFigureID(figure);
            }
            else
                this.changingFigureID = -1;

            if(s.matches("[KDVSJ]?[abcdefgh12345678]?[abcdefgh][12345678][VDSJ]?")) {
                figure = null;
                pattern = Pattern.compile("[KDVSJ]?([abcdefgh12345678])[abcdefgh][12345678][VDSJ]?");
                matcher = pattern.matcher(s);
                if (matcher.find())
                    figure = matcher.group(1);

                if(figure!=null)
                {
                    this.startFieldCol = getIntFieldColumn(figure);
                    if(this.startFieldCol == -1)
                        this.startFieldRow = Integer.parseInt(figure);
                    else
                        this.startFieldRow = -1;
                }
                else
                {
                    this.startFieldCol = -1;
                    this.startFieldRow = -1;
                }


                pattern = Pattern.compile("[KDVSJ]?[abcdefgh12345678]?([abcdefgh])[12345678][VDSJ]?");
                matcher = pattern.matcher(s);
                if (matcher.find())
                    figure = matcher.group(1);

                this.goalFieldCol = getIntFieldColumn(figure);

                pattern = Pattern.compile("[KDVSJ]?[abcdefgh12345678]?[abcdefgh]([12345678])[VDSJ]?");
                matcher = pattern.matcher(s);
                if (matcher.find())
                    figure = matcher.group(1);

                if(figure!=null)
                    this.goalFieldRow = Integer.parseInt(figure);
                else
                    this.startFieldRow = -1;
            }
        }
        else
        {
            return false;
        }
        return true;
    }

    /**
     * Pomocná funkcia pre konvertovanie značky figúrky do id figúrky.
     * @param s String značky figúrky.
     * @return ID figúrky.
     */
    private int getFigureID(String s)
    {
        if (s==null)
            return -1;

        if (s.equals("K"))
            return 0;
        else if(s.equals("D"))
            return 1;
         else if(s.equals("S"))
            return 2;
         else if(s.equals("J"))
            return 3;
         else if(s.equals("V"))
            return 4;
         else if(s.equals("p"))
            return 5;
         else
             return -1;
    }

    /**
     * Pomocná funkcia pre konvertovanie id figúrky do značky danej figúrky.
     * @param id Id figúrky.
     * @return Reťazec značky figúrky.
     */
    private String getStrFigureID(int id)
    {
        if (id==0)
            return "K";
        else if(id==1)
            return "D";
        else if(id==2)
            return "S";
        else if(id==3)
            return "J";
        else if(id==4)
            return "V";
        else if(id==5)
            return "";
        else
            return null;
    }

    /**
     * Pomocná funkcia pre konvertovanie značky stlpca na šachovnici do číselneho poradia stlpca.
     * @param s String označenia stlpca.
     * @return Číselné poradie stlpca.
     */
    private int getIntFieldColumn(String s)
    {
        if (s==null)
            return -1;

        if (s.equals("a"))
            return 1;
        else if(s.equals("b"))
            return 2;
        else if(s.equals("c"))
            return 3;
        else if(s.equals("d"))
            return 4;
        else if(s.equals("e"))
            return 5;
        else if(s.equals("f"))
            return 6;
        else if(s.equals("g"))
            return 7;
        else if(s.equals("h"))
            return 8;
        else
            return -1;
    }

    /**
     * Pomocna funkcia pre konvertovanie čiselneho poradia stĺpca do značky strlpca.
     * @param i Číselné poradie stlpca.
     * @return String označenia stlpca.
     */
    private String  getStrFieldColumn(int i)
    {
        if (i==1)
            return "a";
        else if(i==2)
            return "b";
        else if(i==3)
            return "c";
        else if(i==4)
            return "d";
        else if(i==5)
            return "e";
        else if(i==6)
            return "f";
        else if(i==7)
            return "g";
        else if(i==8)
            return "h";
        else
            return null;
    }

    /**
     * Vracia infomáciu o tom či ide o dlhý zápis ťahu v notácii.
     * @return Pravdivostna hodnota.
     */
    public boolean getIsLongNotation() {return this.isLongNotation;}

    /**
     * Vygeneruje string/zapis ťahu z tohto ťahu notácie.
     * @param activeFigures Aktívne figúrky na šachovnici - štruktúra.
     * @param movementFigure Posúvacia figúrka.
     * @param startFieldOfMovementFig Štartovacie políčko ťahu.
     * @return Vygenerovaný string.
     */
    public String getGeneratedStrOfNotMov(List<Figure> activeFigures, Figure movementFigure, Field startFieldOfMovementFig)
    {
        String s = "";
        if (this.isLongNotation)
        {
            s = s+getStrFigureID(this.movementFigureID);
            s = s+getStrFieldColumn(this.startFieldCol);
            s = s+this.startFieldRow;
            if(this.isFigureRemoving)
                s = s+"x";
            s = s+getStrFieldColumn(this.goalFieldCol);
            s = s+this.goalFieldRow;
            if(this.changingFigureID!=-1)
                s = s+getStrFigureID(this.changingFigureID);
            if(this.isChess)
                s = s+"+";
            if(this.isChessMat)
                s = s+"#";

        }
        else
        {
            s = s+getStrFigureID(this.movementFigureID);

            List<Figure> tmp = activeFigures;
            int length = tmp.size();
            for(int i = 0; i<length; i++)
            {
                if(tmp.get(i).getID() == this.movementFigureID)
                {
                    List<Field> fields = tmp.get(i).getFieldsInDanger();
                    if(tmp.get(i) != movementFigure && fields.contains(movementFigure.getActField()))
                    {
                        if(startFieldOfMovementFig.getColPos() != tmp.get(i).getActField().getColPos())
                            s = s + getStrFieldColumn(startFieldOfMovementFig.getColPos());
                        else
                            s = s + startFieldOfMovementFig.getRowPos();

                        break;
                    }
                }
            }

            if(this.isFigureRemoving)
                s = s+"x";
            s = s+getStrFieldColumn(this.goalFieldCol);
            s = s+this.goalFieldRow;
            if(this.changingFigureID!=-1)
                s = s+getStrFigureID(this.changingFigureID);
            if(this.isChess)
                s = s+"+";
            if(this.isChessMat)
                s = s+"#";
        }

        return s;
    }

    /**
     * Vracia stlpec štartovaciaho políčka.
     * @return
     */
    public int getStartFieldCol() {
        return startFieldCol;
    }

    /**
     * Vracia riadok štartovaciaho políčka.
     * @return
     */
    public int getStartFieldRow() {
        return startFieldRow;
    }

    /**
     * Vracia stlpec cieľoveho políčka.
     * @return
     */
    public int getGoalFieldCol() {
        return goalFieldCol;
    }

    /**
     * Vracia riadok cieľoveho políčka.
     * @return
     */
    public int getGoalFieldRow() {
        return goalFieldRow;
    }

    /**
     * Vracia ID figúrky kotorá je pri ťahu posúvaná.
     * @return ID figúrky.
     */
    public int getMovementFigureID() {
        return movementFigureID;
    }

    /**
     * Vracia ID figúrky kotorá je zamenená za pešiaka.
     * @return ID figúrky.
     */
    public int getChangingFigureID() {
        return changingFigureID;
    }

    /**
     * Vracia ID figúrky kotorá je vyhodená.
     * @return ID figúrky.
     */
    public boolean getIsFigureRemoving(){
        return this.isFigureRemoving;
    }

    /**
     * Vráti informáciu o tom či je v tomto ťahu šach.
     * @return Pravdivostna hodnota.
     */
    public boolean getIsChess(){
        return this.isChess;
    }

    /**
     * Vráti informáciu o tom či je v tomto ťahu šachmat.
     * @return Pravdivostna hodnota.
     */
    public boolean getIsChessMat(){
        return this.isChessMat;
    }

    /**
     * Zkompletuje ťah notácie z ťahu užívateľa.
     * @param movementManager Manážer realneho ťahu.
     */
    public void completeNotationMovement(MovementManager movementManager)
    {
        if(this.startFieldCol == -1)
            this.startFieldCol = movementManager.getStartField().getColPos();

        if(this.startFieldRow == -1)
            this.startFieldRow = movementManager.getStartField().getRowPos();
    }


}
