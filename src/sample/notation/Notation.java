/**
 * Trieda reprezentujúca notáciu hry/partie.
 * @author Jozef Ondria
 */

package sample.notation;

import sample.board.MovementManager;
import sample.figures.FiguresManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static jdk.nashorn.internal.objects.NativeString.trim;

/**
 * Trieda ktorá reprezentuje celú notáciu šachovej partie.
 */
public class Notation
{
    String filePath;
    List<String> gameNotationLines;
    List<NotationMovement> notationMovements;
    int indexProcNotMov;
    boolean isLongNotation;
    boolean isEmpty;
    boolean isRight;

    /**
     * Inicializuje notáciu.
     * @param path Cesta k súboru s obsahom notácie.
     */
    public Notation(String path)
    {
        this.filePath = path;
        try{
            gameNotationLines = Files.readAllLines(Paths.get(path), Charset.forName("ISO-8859-1"));
        } catch (IOException e){
            e.printStackTrace();
        }

        this.notationMovements = new ArrayList<NotationMovement>();
        this.indexProcNotMov = 0;
        this.isRight = true;

        if(gameNotationLines.size() == 0)
        {
            this.isEmpty = true;
            this.isLongNotation = true;
        }
        else
        {
            this.isEmpty = false;
            try {
                this.processNotation();
            }catch (Exception e){
                this.isRight = false;
            }
        }

        if(!this.isRight)
            this.setWrongNotation();

    }

    /**
     * Prechádza celú notáciu zo súboru a postupne spracováva riadok za riadkom a ťah za ťahom.
     * Každý ťah konvertuje na inštanciu triedy NotationMovement ktorý reprezentuje jeden ťah zápisu.
     * Zneho sa vyberajú najdôležitejšie informacie o ťahu a to pri prehrávaní partii.
     */
    public void processNotation()
    {
        int lenght = this.gameNotationLines.size();
        for (int i = 0; i<lenght; i++)
        {
            String line = this.gameNotationLines.get(i);
            line = trim(line);
            String[] lineArray = line.split("\\s+");
            if(lineArray.length != 3)
            {
                if((i+1)!=lenght)
                    this.isRight = false;
                else if(lineArray.length!=2)
                    this.isRight = false;
            }

            try
            {
                if((i+1)!= Integer.parseInt(lineArray[0].substring(0, lineArray[0].length() - 1)))
                    this.isRight = false;
            }
            catch (NumberFormatException e)
            {
                this.isRight = false;
            }


            NotationMovement mov = new NotationMovement();
            if(!mov.setNotationMovementFromString(lineArray[1]))
                this.isRight = false;
            this.notationMovements.add(mov);

            if(i==0)
                this.isLongNotation = mov.getIsLongNotation();
            else
                if(this.isLongNotation != mov.getIsLongNotation())
                    this.isRight = false;

            if(lineArray.length > 2)
            {
                mov = new NotationMovement();
                if(!mov.setNotationMovementFromString(lineArray[2]))
                    this.isRight = false;
                this.notationMovements.add(mov);
                if(this.isLongNotation != mov.getIsLongNotation())
                    this.isRight = false;
            }
        }
    }

    /**
     * Pri vlastnom ťahu užívateľa táto metóda odstráni ďalšie už zbytočné ťahy z notácie
     * a prída ťah užívateľa v do tejto notácii.
     * @param figuresManager Manažer aktívnych/pasívnych figúrok.
     * @param movementManager Manažer ťahu.
     */
    public void addPlayerNotationMovement(FiguresManager figuresManager, MovementManager movementManager)
    {
        this.removeUselessNotMovs();
        this.removeUselessLines();

        NotationMovement mov = new NotationMovement();
        mov.setNotationMovementFromUserMov(this.isLongNotation, figuresManager, movementManager);
        this.notationMovements.add(mov);
        this.indexProcNotMov++;
        this.addNotMovToNotationLines(figuresManager, movementManager, mov);
    }

    /**
     * Pomocná funkcia pre odstranenie všetkých už nepotrebných ťahov zo štrúktúry pre NotationMovements.
     */
    private void removeUselessNotMovs()
    {
        while(this.indexProcNotMov < this.notationMovements.size())
        {
            this.notationMovements.remove(this.indexProcNotMov);
        }
    }

    /**
     * Odstráni zo zápisu notácie (stringovo) už nepotrebne riadky tejto notácie.
     */
    private void removeUselessLines ()
    {
        int linesIndex = (this.indexProcNotMov+1)/2;
        int linesFlag = (this.indexProcNotMov+1)%2;

        int linesToRemove = this.gameNotationLines.size() - linesIndex;

        for (int i = 0; i < linesToRemove;i++)
        {
            this.gameNotationLines.remove(this.gameNotationLines.size()-1);
        }

        if(linesFlag==0 /*&& linesToRemove!=0*/)
        {
            String s = this.gameNotationLines.get(this.gameNotationLines.size()-1);
            this.gameNotationLines.remove(this.gameNotationLines.size()-1);

            s = s.trim();
            String[] lineArray = s.split("\\s+");
            s = ""+lineArray[0]+" "+lineArray[1];
            s.trim();
            this.gameNotationLines.add(s);
        }

    }

    /**
     * Pridá jeden ťah reprezentovaný NotationMovementom do zápisu notácie.
     * @param figuresManager Manžer aktívnych/pasívnych figúrok.
     * @param movementManager Manažer logického ťahu.
     * @param mov Ťah notácie.
     */
    private void addNotMovToNotationLines(FiguresManager figuresManager, MovementManager movementManager,
                                          NotationMovement mov)
    {
        int linesIndex = (this.indexProcNotMov+1)/2;
        int linesFlag = (this.indexProcNotMov+1)%2;
        String s;

        if(linesFlag == 1)
        {
            s = this.gameNotationLines.get(this.gameNotationLines.size()-1);
            this.gameNotationLines.remove(this.gameNotationLines.size()-1);
            s = s.trim();
            s = s + " ";

        }
        else
        {
            s = ""+ linesIndex +". ";
        }

        if(movementManager.isWhiteOnTheMove())
            s = s + mov.getGeneratedStrOfNotMov(figuresManager.getActiveWhiteFigures(),
                    movementManager.getMovementFigure(), movementManager.getStartField());
        else
            s = s + mov.getGeneratedStrOfNotMov(figuresManager.getActiveBlackFigures(),
                    movementManager.getMovementFigure(), movementManager.getStartField());
        this.gameNotationLines.add(s);
    }

    /**
     * Uloži notáciu do súboru z ktorého bola načítaná.
     * @return Úspešnosť uloženia.
     */
    public boolean saveNotation()
    {

        int length = this.gameNotationLines.size();
        try (PrintWriter out = new PrintWriter(this.filePath))
        {
            for (int i = 0 ; i<length;i++)
            {
                out.println(this.gameNotationLines.get(i));
            }
            return true;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Testuje či je index pre štruktúru ťahov notácie nulový ak áno znamena to že sme spracuvávame prvý prvok
     * štruktúry.
     * @return
     */
    public boolean isFirstIndex()
    {
        if(this.indexProcNotMov == 0)
            return true;
        else
            return false;
    }

    /**
     * Testuje či je index pre štruktúru ťahov notácie poslený, či pracujeme s poslednym prvkom štruktúry.
     * @return
     */
    public boolean isLastIndex()
    {
        if (this.indexProcNotMov == this.notationMovements.size())
            return true;
        else
            return false;
    }

    /**
     * Vracia zápis notácie implementovanú riadok po riadku v štruktúre ArrayList.
     * @return Štruktúru notácie.
     */
    public List<String> getGameNotationLines(){return this.gameNotationLines;}

    /**
     * Vracia index aktuálneho spracovaneho ťahu notácie ale prevedený na riadok notácie.
     * @return
     */
    public int getIndexProcNotMov(){return (this.indexProcNotMov-1)/2;}

    /**
     * Testovanie či je notácia prázdna.
     * @return Výsledok testu.
     */
    public boolean getIsEmpty(){return this.isEmpty;}

    /**
     * Vráti inštanciu/objekt triedy NotationMovement pre ťah ktorý sa aktuálne spracúváva.
     * @return
     */
    public NotationMovement getActualNotMov()
    {
        return this.notationMovements.get(this.indexProcNotMov);
    }

    /**
     * Inkrementuje index určujúci aktuálny spracovávaný ťah notácie.
     */
    public void incrementIndexOfNotationLines()
    {
        this.indexProcNotMov++;
    }

    /**
     * Dekrementuje index určujúci aktuálny spracovávaný ťah notácie.
     */
    public void decrementIndexOfNotationLines()
    {
        this.indexProcNotMov--;
    }

    /**
     * Vráti ID figúrky ktorá má v danom ťahu vymeniť pešiaka.
     * @return Hodnota ID.
     */
    public int getChangingFigureID(){return this.getActualNotMov().getChangingFigureID();}

    /**
     * Pri postupnom prehrávani/hraní partie táto metóda doplnňuje chýbajúce informácie do
     * aktuálneho ťahu notácie. (napríklad pri krátkom formáte notácie to je štartovacie políčko,
     * využívane neskôr pri kroku spať)
     * @param movementManager Manažer ťahu.
     */
    public void completeNotationMovement(MovementManager movementManager){
        this.notationMovements.get(this.indexProcNotMov-1).completeNotationMovement(movementManager);}

    /**
     * Vracia inštanciu triedy NotationMovement pre ťah inštancie predchadzajúceho ťahu.
     * Využíívane pri kroku spať(undo).
     * @return
     */
    public NotationMovement getPrevNotationMovement(){return this.notationMovements.get(this.indexProcNotMov-1);}

    /**
     * Vracia informáciu o tom či je formát notácie šachovej partie správny.
     * @return Pravdivostná hodnota.
     */
    public boolean getIsRight () {return this.isRight;}

    /**
     * Nastaví notáciu ako nesprávnu chybovou hláškou.
     */
    private void setWrongNotation(){
        this.gameNotationLines.clear();
        this.gameNotationLines.add("Chess notation is incorrect!");
    }

    public void setIsRight(boolean is)
    {
        this.isRight = is;
    }
}
