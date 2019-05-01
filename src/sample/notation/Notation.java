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

public class Notation
{
    String filePath;
    List<String> gameNotationLines;
    List<NotationMovement> notationMovements;
    int indexProcNotMov;
    boolean isLongNotation;
    boolean isEmpty;
    boolean isRight;

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
            mov.setNotationMovementFromString(lineArray[1]);
            this.notationMovements.add(mov);

            if(i==0)
                this.isLongNotation = mov.getIsLongNotation();
            else
                if(this.isLongNotation != mov.getIsLongNotation())
                    this.isRight = false;

            if(lineArray.length > 2)
            {
                mov = new NotationMovement();
                mov.setNotationMovementFromString(lineArray[2]);
                this.notationMovements.add(mov);
                if(this.isLongNotation != mov.getIsLongNotation())
                    this.isRight = false;
            }
        }
    }



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

    private void removeUselessNotMovs()
    {
        while(this.indexProcNotMov < this.notationMovements.size())
        {
            this.notationMovements.remove(this.indexProcNotMov);
        }
    }

    private void removeUselessLines ()
    {
        int linesIndex = (this.indexProcNotMov+1)/2;
        int linesFlag = (this.indexProcNotMov+1)%2;

        int linesToRemove = this.gameNotationLines.size() - linesIndex;

        for (int i = 0; i < linesToRemove;i++)
        {
            this.gameNotationLines.remove(this.gameNotationLines.size()-1);
        }

        if(linesFlag==0 && linesToRemove!=0)
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

    public boolean isFirstIndex()
    {
        if(this.indexProcNotMov == 0)
            return true;
        else
            return false;
    }

    public boolean isLastIndex()
    {
        if (this.indexProcNotMov == this.notationMovements.size())
            return true;
        else
            return false;
    }

    public List<String> getGameNotationLines(){return this.gameNotationLines;}

    public int getIndexProcNotMov(){return (this.indexProcNotMov-1)/2;}

    public boolean getIsEmpty(){return this.isEmpty;}

    public NotationMovement getActualNotMov()
    {
        return this.notationMovements.get(this.indexProcNotMov);
    }

    public void incrementIndexOfNotationLines()
    {
        this.indexProcNotMov++;
    }

    public void decrementIndexOfNotationLines()
    {
        this.indexProcNotMov--;
    }

    public int getChangingFigureID(){return this.getActualNotMov().getChangingFigureID();}

    public void completeNotationMovement(MovementManager movementManager){
        this.notationMovements.get(this.indexProcNotMov-1).completeNotationMovement(movementManager);}

    public NotationMovement getPrevNotationMovement(){return this.notationMovements.get(this.indexProcNotMov-1);}

    public boolean getIsRight () {return this.isRight;}

    private void setWrongNotation() {
        this.gameNotationLines.clear();
        this.gameNotationLines.add("Chess notation is incorrect!");
    }

}
