package sample.notation;

import sample.board.Field;
import sample.board.MovementManager;
import sample.figures.Figure;
import sample.figures.FiguresManager;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public NotationMovement()
    {
        this.startFieldCol = -1;
        this.startFieldRow = -1;
        this.movementFigureID = -1;
        this.goalFieldCol = -1;
        this.goalFieldRow = -1;
        this.changingFigureID = -1;
    }

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


    public void setNotationMovementFromString(String s)
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
                System.exit(1);
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
            System.exit(1);
        }
    }

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

    public boolean getIsLongNotation() {return this.isLongNotation;}

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

    public int getStartFieldCol() {
        return startFieldCol;
    }

    public int getStartFieldRow() {
        return startFieldRow;
    }

    public int getGoalFieldCol() {
        return goalFieldCol;
    }

    public int getGoalFieldRow() {
        return goalFieldRow;
    }

    public int getMovementFigureID() {
        return movementFigureID;
    }

    public int getChangingFigureID() {
        return changingFigureID;
    }

    public boolean getIsFigureRemoving(){
        return this.isFigureRemoving;
    }

    public boolean getIsChess(){
        return this.isChess;
    }

    public boolean getIsChessMat(){
        return this.isChessMat;
    }

    public void completeNotationMovement(MovementManager movementManager)
    {
        if(this.startFieldCol == -1)
            this.startFieldCol = movementManager.getStartField().getColPos();

        if(this.startFieldRow == -1)
            this.startFieldRow = movementManager.getStartField().getRowPos();
    }


}
