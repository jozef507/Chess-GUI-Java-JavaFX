/**
 * Trieda reprezentujúca manažéra aktívnych/pasívnych figúrok.
 * @author Ján Folenta
 * @author Jozef Ondria
 */

package sample.figures;

import sample.board.Field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Trieda reprezentujúca manažera figúrok. Obsahuje informácie o aktívných figúrkach (na šachovnici)
 * vyhodených figurkách a takisto zamenených figúrkach.
 */
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


    /**
     * Inicializace objektu.
     */
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

    /**
     * Základná metoda tejto triedy, volaná po uskutočnení každého ťahu. Jej úlohou je aktualizovať
     * štruktúry aktívnych/pasívnych figúrok ale najmä aktualizovať štruktúry pre políčka v ohrození požívajuce
     * sa na detekciu šachu a šachmatu.
     * @param whiteOnTheMove Informácia o tom ktorý hráč je na ťahu.
     * @param movingFigure Odkaz na figúrku ktorá sa pri ťahu presúva.
     * @param removedFigure Odkaz na figúrku ktorý bola vyhodená.
     * @return Úspešnosť operácie.
     */
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

    /**
     * Pridá figúrku figure do štruktúry aktívnych figúrok podľa jej farby.
     * @param figure
     */
    public void addActiveFigure(Figure figure)
    {
        if (figure.isWhite())
            this.activeWhiteFigures.add(figure);
        else
            this.activeBlackFigures.add(figure);
    }

    /**
     * Pridá vyhodenú figúrku figure do štruktúry pre vyhodené figúrky.
     * @param figure
     */
    public void addRemovedFigure(Figure figure)
    {
        if (figure.isWhite())
        {
            if(this.activeWhiteFigures.contains(figure))
            {
                this.activeWhiteFigures.remove(figure);
                this.removedFigures.add(figure);
            }
        }
        else
        {
            if(this.activeBlackFigures.contains(figure))
            {
                this.activeBlackFigures.remove(figure);
                this.removedFigures.add(figure);
            }
        }
    }

    /**
     * Pridá zamenenú figúruku(pešiaka) figure do štruktúry pre zamenené figúrky.
     * @param figure
     */
    public void addChangedFigure(Figure figure)
    {
        if (figure.isWhite())
        {
            if(this.activeWhiteFigures.contains(figure))
            {
                this.activeWhiteFigures.remove(figure);
                this.changedFigures.add(figure);
            }
        }
        else
        {
            if(this.activeBlackFigures.contains(figure))
            {
                this.activeBlackFigures.remove(figure);
                this.changedFigures.add(figure);
            }
        }
    }

    /**
     * Nastaví/aktualizuje štruktúru ohrozených políčok bielymi figúrkami.
     */
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

    /**
     * Nastaví/aktualizuje štruktúru ohrozených políčok čiernymi figúrkami.
     */
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

    /**
     * Nastaví/aktualizuje štruktúru ohrozených políčok bielymi figúrkami okrem bieleho kráľa.
     * @return
     */
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

    /**
     * Nastaví/aktualizuje štruktúru ohrozených políčok čiernymi figúrkami okrem čierneho kráľa.
     * @return
     */
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

    /**
     * Metóda kontroluje či je prítomny na šachovnici šach.
     * @param isWhiteOnTheMove Hrač na ťahu.
     * @param movingFigure Presúvaná figúrka.
     * @return Výsledok kontroly.
     */
    public boolean checkChess(boolean isWhiteOnTheMove, Figure movingFigure)
    {
        Field kingFieldBlack = this.blackKing.getActField();
        if (this.whiteFieldsInDanger.contains(kingFieldBlack))
        {
            this.blackKing.setInChess(true);
            this.blackKing.setChessBy(movingFigure);
            return true;
        }
        else
        {
            this.blackKing.setInChess(false);
            this.blackKing.setChessBy(null);
        }


        Field kingFieldWhite = this.whiteKing.getActField();
        if (this.blackFieldsInDanger.contains(kingFieldWhite))
        {
            this.whiteKing.setInChess(true);
            this.whiteKing.setChessBy(movingFigure);
            return true;
        }
        else
        {
            this.whiteKing.setInChess(false);
            this.whiteKing.setChessBy(null);
        }
        return false;
    }

    /**
     * Metóda kontroluje či momentálny stav na šachovnici nespôsobuje šach mat.
     * @param isWhiteOnTheMove Hráč na ťahu.
     * @return Výsledok kotnroly.
     */
    public boolean checkChessMat(boolean isWhiteOnTheMove)
    {
        if(isWhiteOnTheMove)
        {
            ArrayList<Field> whiteFieldsInDangerChessMat = new ArrayList<Field>();
            Figure tmpFigure;
            for(int i = 0; i < activeWhiteFigures.size(); i++)
            {
                tmpFigure = activeWhiteFigures.get(i);
                whiteFieldsInDangerChessMat.addAll(tmpFigure.getFieldsInDangerChesMat());
            }

            if(this.blackKing.canMove(whiteFieldsInDangerChessMat))
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

                        Field nextField = field.nextField(Field.Direction.LU);
                        if(nextField!=null)
                            if(nextField.get()!= null && nextField.get().getID()==5)
                                pawnOccurrences++;

                        nextField = field.nextField(Field.Direction.RU);
                        if(nextField != null)
                            if(nextField.get()!=null && nextField.get().getID()==5)
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
            ArrayList<Field> blackieldsInDangerChessMat = new ArrayList<Field>();
            Figure tmpFigure;
            for(int i = 0; i < activeBlackFigures.size(); i++)
            {
                tmpFigure = activeBlackFigures.get(i);
                blackieldsInDangerChessMat.addAll(tmpFigure.getFieldsInDangerChesMat());
            }

            if(this.whiteKing.canMove(blackieldsInDangerChessMat))
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

    /**
     * Nastaví člena/premennu tejto triedy kráľa na kráľa daného parametrom.
     * @param king Kráľ ktorý má byť priradený objektu.
     */
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

    /**
     * Vracia informáciu o tom či je na šachovnici stav - šach.
     * @return Pravdivostná hodnota.
     */
    public boolean getChess()
    {
        return this.whiteKing.getInChess() || this.blackKing.getInChess();
    }

    /**
     * Vracia informáciu o tom či je na šachovnici stav - šachmat.
     * @return Pravdivostná hodnota.
     */
    public boolean getChessMat()
    {
        return this.chessMat;
    }

    /**
     * Vráti poslednú figúrku priradenú do štruktúry - zamenené figúrky.
     * @return Odkaz na figúrku.
     */
    public Figure getLastChangedFigure () {return this.changedFigures.get(this.changedFigures.size()-1);}

    /**
     * Odstráni poseldnú figúrku zo štruktúry - zamenené figúrky.
     */
    public void removeLastChangedFigure () {this.changedFigures.remove(this.changedFigures.size()-1);}

    /**
     * Vráti štruktúru aktívnych bielych figúrok.
     * @return Štruktúru figúrok.
     */
    public List<Figure> getActiveWhiteFigures () {return this.activeWhiteFigures;}

    /**
     * Vráti štruktúru aktívnych čiernych figúrok.
     * @return Štruktúru figúrok.
     */
    public List<Figure> getActiveBlackFigures () {return this.activeBlackFigures;}

    /**
     * Vráti poslednú figúrku priradenú do štruktúry - vyhodené figúrky.
     * @return Odkaz na figúrku.
     */
    public Figure getLastRemovedFigure()  { return  this.removedFigures.get(this.removedFigures.size()-1); }

    /**
     * Odstráni poseldnú figúrku zo štruktúry - vyhodené figúrky.
     */
    public void removeLastRemovedFigure()  { this.removedFigures.remove(this.removedFigures.size()-1); }

    /**
     * Odstráni z aktívnych figúrok figurkú danú parametrom.
     * @param figure Odkaz na danú figúrku.
     */
    public void  removeActiveFigure(Figure figure)
    {
        if(figure.isWhite())
            this.activeWhiteFigures.remove(figure);
        else
            this.activeBlackFigures.remove(figure);
    }

    /**
     * Nastaví člena tiredy šachmat podľa daného parametru.
     * @param is Pravdivostná hotnota.
     */
    public void setChessMat(boolean is)
    {
        this.chessMat = is;
    }

}
