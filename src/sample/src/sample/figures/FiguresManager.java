package sample.figures;

import sample.board.Field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FiguresManager
{
    //private boolean chess;
    private boolean chessMat;

    private King whiteKing;
    private King blackKing;

    private List<Figure> activeWhiteFigures;
    private List<Figure> activeBlackFigures;
    private List<Figure> removedFigures;
    private List<Figure> changedFigures;

    private List<Field> whiteFieldsInDanger;
    private List<Field> blackFieldsInDanger;



    public FiguresManager()
    {
        //this.chess = false;
        this.chessMat = false;
        this.activeWhiteFigures = new ArrayList<Figure>();
        this.activeBlackFigures = new ArrayList<Figure>();
        this.removedFigures = new ArrayList<Figure>();
        this.changedFigures = new ArrayList<Figure>();
        this.whiteFieldsInDanger = new ArrayList<Field>();
        this.blackFieldsInDanger = new ArrayList<Field>();
    }

    public boolean updateFigures(boolean whiteOnTheMove, Figure movingFigure, Figure removedFigure)
    {
        List<Figure> tmpActiveWhiteFigures = new ArrayList<>(this.activeWhiteFigures);
        List<Figure> tmpActiveBlackFigures = new ArrayList<>(this.activeBlackFigures);
        List<Figure> tmpRemovedFigures = new ArrayList<>(this.removedFigures);
        List<Field> tmpWhiteFieldsInDanger = new ArrayList<>(this.whiteFieldsInDanger);
        List<Field> tmpBlackFieldsInDanger = new ArrayList<>(this.blackFieldsInDanger);

        if(removedFigure != null)
        {
            this.addRemovedFigure(removedFigure);
        }

        this.setWhiteFieldsInDanger();
        this.setBlackFieldsInDanger();

        boolean chess;
        if(whiteOnTheMove)
        {
            Field kingField = this.whiteKing.getActField();
            if (this.blackFieldsInDanger.contains(kingField))
                chess = true;
            else
                chess = false;
        }
        else
        {
            Field kingField = this.blackKing.getActField();
            if (this.whiteFieldsInDanger.contains(kingField))
                chess = true;
            else
                chess = false;
        }

        if(chess)
        {
            this.activeWhiteFigures = tmpActiveWhiteFigures;
            this.activeBlackFigures = tmpActiveBlackFigures;
            this.removedFigures = tmpRemovedFigures;
            this.whiteFieldsInDanger = tmpWhiteFieldsInDanger;
            this.blackFieldsInDanger = tmpBlackFieldsInDanger;
            return false;
        }

        if (this.checkChess(whiteOnTheMove, movingFigure))
        {
            this.checkChessMat(whiteOnTheMove);
        }

        return true;

    }

    public void addActiveFigure(Figure figure)
    {
        if (figure.isWhite())
            this.activeWhiteFigures.add(figure);
        else
            this.activeBlackFigures.add(figure);
    }

    public void addRemovedFigure(Figure figure)
    {
        if (figure.isWhite())
        {
            if(this.activeWhiteFigures.contains(figure))
            {
                this.activeWhiteFigures.remove(figure);
            }
        }
        else
        {
            if(this.activeBlackFigures.contains(figure))
            {
                this.activeBlackFigures.remove(figure);
            }
        }

        this.removedFigures.add(figure);
    }

    public void addChangedFigure(Figure figure)
    {
        if (figure.isWhite())
        {
            if(this.activeWhiteFigures.contains(figure))
            {
                this.activeWhiteFigures.remove(figure);
            }
        }
        else
        {
            if(this.activeBlackFigures.contains(figure))
            {
                this.activeBlackFigures.remove(figure);
            }
        }

        this.changedFigures.add(figure);
    }

    public void setWhiteFieldsInDanger()
    {
        this.whiteFieldsInDanger.clear();
        int length = this.activeWhiteFigures.size();
        Figure tmpFigure;
        for(int i = 0; i < length; i++)
        {
            tmpFigure = activeWhiteFigures.get(i);
            tmpFigure.setFieldsInDanger();
            List<Field> tmpFields = tmpFigure.getFieldsInDanger();
            this.whiteFieldsInDanger.addAll(tmpFields);
        }
    }

    public void setBlackFieldsInDanger()
    {
        this.blackFieldsInDanger.clear();
        int length = this.activeBlackFigures.size();
        Figure tmpFigure;
        for(int i = 0; i < length; i++)
        {
            tmpFigure = activeBlackFigures.get(i);
            tmpFigure.setFieldsInDanger();
            List<Field> tmpFields = tmpFigure.getFieldsInDanger();
            this.blackFieldsInDanger.addAll(tmpFields);
        }
    }

    public List<Field> getWhiteFieldsInDangerWithoutKing()
    {
        List<Field> whiteFieldsInDangerWithoutKing = new ArrayList<Field>();
        int length = this.activeWhiteFigures.size();
        Figure tmpFigure;
        for(int i = 0; i < length; i++)
        {
            tmpFigure = activeWhiteFigures.get(i);
            if(tmpFigure.getID()!=0)
            {
                tmpFigure.setFieldsInDanger();
                List<Field> tmpFields = tmpFigure.getFieldsInDanger();
                whiteFieldsInDangerWithoutKing.addAll(tmpFields);
            }

        }
        return whiteFieldsInDangerWithoutKing;
    }

    public List<Field> getBlackFieldsInDangerWithoutKing()
    {
        List<Field> blackFieldsInDangerWithoutKing = new ArrayList<Field>();
        int length = this.activeBlackFigures.size();
        Figure tmpFigure;
        for(int i = 0; i < length; i++)
        {
            tmpFigure = activeBlackFigures.get(i);

            if(tmpFigure.getID()!=0)
            {
                tmpFigure.setFieldsInDanger();
                List<Field> tmpFields = tmpFigure.getFieldsInDanger();
                blackFieldsInDangerWithoutKing.addAll(tmpFields);
            }
        }

        return blackFieldsInDangerWithoutKing;
    }

    public boolean checkChess(boolean isWhiteOnTheMove, Figure movingFigure)
    {

        Field kingFieldBlack = this.blackKing.getActField();
        if (this.whiteFieldsInDanger.contains(kingFieldBlack))
        {
            this.blackKing.setInChess(true);
            this.blackKing.setChessBy(movingFigure);
            //this.chess = true;

            return true;
        }
        else
        {
            this.blackKing.setInChess(false);
            this.blackKing.setChessBy(null);
        }


        //this.chess = false;
        //return false;

        Field kingFieldWhite = this.whiteKing.getActField();
        if (this.blackFieldsInDanger.contains(kingFieldWhite))
        {
            this.whiteKing.setInChess(true);
            this.whiteKing.setChessBy(movingFigure);

            //this.chess = true;

            return true;
        }
        else
        {
            this.whiteKing.setInChess(false);
            this.whiteKing.setChessBy(movingFigure);
        }


        //this.chess = false;
        return false;

    }

    public boolean checkChessMat(boolean isWhiteOnTheMove)
    {
        if(isWhiteOnTheMove)
        {
            if(this.blackKing.canMove(this.whiteFieldsInDanger))
            {
                this.chessMat=false;
                return false;
            }
            else
            {
                List<Field> fieldsInChessDir = this.blackKing.getChessBy().getFieldsOfDirectionToField(this.blackKing.getActField());
                List<Field> blackFieldsInDangerWithoutKing = this.getBlackFieldsInDangerWithoutKing();

                int length = fieldsInChessDir.size();
                boolean chessmatFlag = true;
                for (int i = 0; i<length; i++)
                {
                    Field field = fieldsInChessDir.get(i);
                    if(blackFieldsInDangerWithoutKing.contains(field))
                    {
                        int fieldOccurrences = Collections.frequency(blackFieldsInDangerWithoutKing, field);
                        int pawnOccurrences = 0;

                        if(field.nextField(Field.Direction.LU).get().getID()==5)
                            pawnOccurrences++;
                        if(field.nextField(Field.Direction.RU).get().getID()==5)
                            pawnOccurrences++;
                        if(fieldOccurrences == pawnOccurrences)
                            continue;
                        chessmatFlag = false;
                    }
                }

                if(chessmatFlag)
                {
                    System.out.println("--------------------------------------------");
                    this.chessMat=true;
                    return true;
                }
                else
                {
                    return false;
                }

            }

        }
        else
        {
            if(this.whiteKing.canMove(this.blackFieldsInDanger))
            {
                this.chessMat=false;
                return false;
            }
            else
            {
                List<Field> fieldsInChessDir = this.whiteKing.getChessBy().getFieldsOfDirectionToField(this.whiteKing.getActField());
                List<Field> whiteFieldsInDangerWithoutKing = this.getWhiteFieldsInDangerWithoutKing();

                int length = fieldsInChessDir.size();
                boolean chessmatFlag = true;
                for (int i = 0; i<length; i++)
                {
                    Field field = fieldsInChessDir.get(i);
                    if(whiteFieldsInDangerWithoutKing.contains(field))
                    {
                        int fieldOccurrences = Collections.frequency(whiteFieldsInDangerWithoutKing, field);
                        int pawnOccurrences = 0;

                        Field nextField = field.nextField(Field.Direction.LD);
                        if(nextField!=null)
                            if(nextField.get() != null && nextField.get().getID()==5)
                                pawnOccurrences++;

                        nextField = field.nextField(Field.Direction.RD);
                        if(nextField!=null)
                            if(nextField.get() != null && nextField.get().getID()==5)
                                pawnOccurrences++;

                        if(fieldOccurrences == pawnOccurrences)
                            continue;
                        chessmatFlag = false;
                    }
                }

                if(chessmatFlag)
                {
                    System.out.println("--------------------------------------------");
                    this.chessMat = true;
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
    }

    public void setKing(King king)
    {
        if(king.isWhite())
        {
            this.whiteKing = king;
        }
        else
        {
            this.blackKing = king;
        }
    }

    public boolean getChess()
    {
        return this.whiteKing.getInChess() || this.blackKing.getInChess();
    }

    public boolean getChessMat()
    {
        return this.chessMat;
    }

    public Figure getLastChangedFigure () {return this.changedFigures.get(this.changedFigures.size()-1);}

    public List<Figure> getActiveWhiteFigures () {return this.activeWhiteFigures;}

    public List<Figure> getActiveBlackFigures () {return this.activeBlackFigures;}

}
