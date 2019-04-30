package sample.gui;

import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

import sample.game.*;

public class GuiGameContr implements Initializable
{
    private Game game;
    private int fieldDiff;
    private List<ImageView> whiteFiguresImages;
    private List<ImageView> blackFiguresImages;
    private int pc = 1;

    @FXML
    public ImageView pawnWhite1;
    @FXML
    public ImageView pawnWhite2;
    @FXML
    public ImageView pawnWhite3;
    @FXML
    public ImageView pawnWhite4;
    @FXML
    public ImageView pawnWhite5;
    @FXML
    public ImageView pawnWhite6;
    @FXML
    public ImageView pawnWhite7;
    @FXML
    public ImageView pawnWhite8;
    @FXML
    public ImageView pawnBlack1;
    @FXML
    public ImageView pawnBlack2;
    @FXML
    public ImageView pawnBlack3;
    @FXML
    public ImageView pawnBlack4;
    @FXML
    public ImageView pawnBlack5;
    @FXML
    public ImageView pawnBlack6;
    @FXML
    public ImageView pawnBlack7;
    @FXML
    public ImageView pawnBlack8;

    @FXML
    public ImageView rookWhite1;
    @FXML
    public ImageView rookWhite2;
    @FXML
    public ImageView rookBlack1;
    @FXML
    public ImageView rookBlack2;

    @FXML
    public ImageView knightWhite1;
    @FXML
    public ImageView knightWhite2;
    @FXML
    public ImageView knightBlack1;
    @FXML
    public ImageView knightBlack2;

    @FXML
    public ImageView bishopWhite1;
    @FXML
    public ImageView bishopWhite2;
    @FXML
    public ImageView bishopBlack1;
    @FXML
    public ImageView bishopBlack2;

    @FXML
    public ImageView queenWhite;
    @FXML
    public ImageView queenBlack;

    @FXML
    public ImageView kingWhite;
    @FXML
    public ImageView kingBlack;

    @FXML
    public HBox whiteFigures;
    @FXML
    public HBox blackFigures;

    @FXML
    public Button buttonRookWhite;
    @FXML
    public Button buttonKnightWhite;
    @FXML
    public Button buttonBishopWhite;
    @FXML
    public Button buttonQueenWhite;

    @FXML
    public Button buttonRookBlack;
    @FXML
    public Button buttonKnightBlack;
    @FXML
    public Button buttonBishopBlack;
    @FXML
    public Button buttonQueenBlack;



    @FXML
    public AnchorPane board;
    @FXML
    public ScrollPane scrollbar;
    @FXML
    public TextFlow text;


    public void initialize(URL location, ResourceBundle resources)
    {
        ImageView[] whiteFiguresImagesArray = createWhiteFigureImagesArray();
        ImageView[] blackFiguresImagesArray = createBlackFigureImagesArray();

        this.whiteFiguresImages = getListFromArray(whiteFiguresImagesArray) ;
        this.blackFiguresImages = getListFromArray(blackFiguresImagesArray);
        game = new ChessGame(whiteFiguresImagesArray, blackFiguresImagesArray);
        setTextArea(game.getGameNotation(), game.getIndexOfGameNotation()+1);

        setOpacityForImages(whiteFiguresImages, 1);
        setOpacityForImages(blackFiguresImages, 0.87);
        setCursorForImages(whiteFiguresImages, Cursor.HAND);
        setCursorForImages(blackFiguresImages, Cursor.DISAPPEAR);

        board.setPickOnBounds(true);
        board.setOnMouseClicked(e -> {
            onBoardClick(e.getX(), e.getY());
        });

        buttonRookWhite.setOnMouseClicked(e -> {
            ImageView imageView = new ImageView("sample/images/rook_white.png");

            double lX = game.getGoalField().get().getImage().getLayoutX();
            double lY = game.getGoalField().get().getImage().getLayoutY();
            double tX = game.getGoalField().get().getImage().getTranslateX();
            double tY = game.getGoalField().get().getImage().getTranslateY();
            game.getGoalField().get().getImage().setVisible(false);

            board.getChildren().add(imageView);
            imageView.setFitWidth(80);
            imageView.setFitHeight(79);
            imageView.setLayoutX(lX);
            imageView.setLayoutY(lY);
            imageView.setTranslateX(tX);
            imageView.setTranslateY(tY);


            game.createNewFigure(imageView, 4);
            hideChangingFigures();
            this.whiteFiguresImages.add(imageView);

            this.closePlayerMovement();

        });
        buttonKnightWhite.setOnMouseClicked(e -> {
            ImageView imageView = new ImageView("sample/images/knight_white.png");

            double lX = game.getGoalField().get().getImage().getLayoutX();
            double lY = game.getGoalField().get().getImage().getLayoutY();
            double tX = game.getGoalField().get().getImage().getTranslateX();
            double tY = game.getGoalField().get().getImage().getTranslateY();
            game.getGoalField().get().getImage().setVisible(false);

            board.getChildren().add(imageView);
            imageView.setFitWidth(80);
            imageView.setFitHeight(79);
            imageView.setLayoutX(lX);
            imageView.setLayoutY(lY);
            imageView.setTranslateX(tX);
            imageView.setTranslateY(tY);

            game.createNewFigure(imageView, 3);
            hideChangingFigures();
            this.whiteFiguresImages.add(imageView);

            this.closePlayerMovement();


        });
        buttonBishopWhite.setOnMouseClicked(e -> {
            ImageView imageView = new ImageView("sample/images/bishop_white.png");

            double lX = game.getGoalField().get().getImage().getLayoutX();
            double lY = game.getGoalField().get().getImage().getLayoutY();
            double tX = game.getGoalField().get().getImage().getTranslateX();
            double tY = game.getGoalField().get().getImage().getTranslateY();
            game.getGoalField().get().getImage().setVisible(false);

            board.getChildren().add(imageView);
            imageView.setFitWidth(80);
            imageView.setFitHeight(79);
            imageView.setLayoutX(lX);
            imageView.setLayoutY(lY);
            imageView.setTranslateX(tX);
            imageView.setTranslateY(tY);

            game.createNewFigure(imageView, 2);
            hideChangingFigures();
            this.whiteFiguresImages.add(imageView);

            this.closePlayerMovement();


        });
        buttonQueenWhite.setOnMouseClicked(e -> {
            ImageView imageView = new ImageView("sample/images/queen_white.png");

            double lX = game.getGoalField().get().getImage().getLayoutX();
            double lY = game.getGoalField().get().getImage().getLayoutY();
            double tX = game.getGoalField().get().getImage().getTranslateX();
            double tY = game.getGoalField().get().getImage().getTranslateY();
            game.getGoalField().get().getImage().setVisible(false);

            board.getChildren().add(imageView);
            imageView.setFitWidth(80);
            imageView.setFitHeight(79);
            imageView.setLayoutX(lX);
            imageView.setLayoutY(lY);
            imageView.setTranslateX(tX);
            imageView.setTranslateY(tY);

            game.createNewFigure(imageView, 1);
            hideChangingFigures();
            this.whiteFiguresImages.add(imageView);

            this.closePlayerMovement();


        });
        buttonRookBlack.setOnMouseClicked(e -> {
            ImageView imageView = new ImageView("sample/images/rook_dark.png");

            double lX = game.getGoalField().get().getImage().getLayoutX();
            double lY = game.getGoalField().get().getImage().getLayoutY();
            double tX = game.getGoalField().get().getImage().getTranslateX();
            double tY = game.getGoalField().get().getImage().getTranslateY();
            game.getGoalField().get().getImage().setVisible(false);

            board.getChildren().add(imageView);
            imageView.setFitWidth(80);
            imageView.setFitHeight(79);
            imageView.setLayoutX(lX);
            imageView.setLayoutY(lY);
            imageView.setTranslateX(tX);
            imageView.setTranslateY(tY);

            game.createNewFigure(imageView, 4);
            hideChangingFigures();
            this.blackFiguresImages.add(imageView);

            this.closePlayerMovement();

        });
        buttonKnightBlack.setOnMouseClicked(e -> {
            ImageView imageView = new ImageView("sample/images/knight_dark.png");

            double lX = game.getGoalField().get().getImage().getLayoutX();
            double lY = game.getGoalField().get().getImage().getLayoutY();
            double tX = game.getGoalField().get().getImage().getTranslateX();
            double tY = game.getGoalField().get().getImage().getTranslateY();
            game.getGoalField().get().getImage().setVisible(false);

            board.getChildren().add(imageView);
            imageView.setFitWidth(80);
            imageView.setFitHeight(79);
            imageView.setLayoutX(lX);
            imageView.setLayoutY(lY);
            imageView.setTranslateX(tX);
            imageView.setTranslateY(tY);


            game.createNewFigure(imageView, 3);
            hideChangingFigures();
            this.blackFiguresImages.add(imageView);

            this.closePlayerMovement();

        });
        buttonBishopBlack.setOnMouseClicked(e -> {
            ImageView imageView = new ImageView("sample/images/bishop_dark.png");

            double lX = game.getGoalField().get().getImage().getLayoutX();
            double lY = game.getGoalField().get().getImage().getLayoutY();
            double tX = game.getGoalField().get().getImage().getTranslateX();
            double tY = game.getGoalField().get().getImage().getTranslateY();
            game.getGoalField().get().getImage().setVisible(false);

            board.getChildren().add(imageView);
            imageView.setFitWidth(80);
            imageView.setFitHeight(79);
            imageView.setLayoutX(lX);
            imageView.setLayoutY(lY);
            imageView.setTranslateX(tX);
            imageView.setTranslateY(tY);

            game.createNewFigure(imageView, 2);
            hideChangingFigures();
            this.blackFiguresImages.add(imageView);

            this.closePlayerMovement();

        });
        buttonQueenBlack.setOnMouseClicked(e -> {
            ImageView imageView = new ImageView("sample/images/queen_dark.png");

            double lX = game.getGoalField().get().getImage().getLayoutX();
            double lY = game.getGoalField().get().getImage().getLayoutY();
            double tX = game.getGoalField().get().getImage().getTranslateX();
            double tY = game.getGoalField().get().getImage().getTranslateY();
            game.getGoalField().get().getImage().setVisible(false);

            board.getChildren().add(imageView);
            imageView.setFitWidth(80);
            imageView.setFitHeight(79);
            imageView.setLayoutX(lX);
            imageView.setLayoutY(lY);
            imageView.setTranslateX(tX);
            imageView.setTranslateY(tY);

            game.createNewFigure(imageView, 1);
            hideChangingFigures();
            this.blackFiguresImages.add(imageView);

            this.closePlayerMovement();

        });

    }

    private ImageView[] createWhiteFigureImagesArray()
    {
        ImageView[] whiteFigures = new ImageView[16];
        whiteFigures[0] = pawnWhite1;
        whiteFigures[1] = pawnWhite2;
        whiteFigures[2] = pawnWhite3;
        whiteFigures[3] = pawnWhite4;
        whiteFigures[4] = pawnWhite5;
        whiteFigures[5] = pawnWhite6;
        whiteFigures[6] = pawnWhite7;
        whiteFigures[7] = pawnWhite8;
        whiteFigures[8] = rookWhite1;
        whiteFigures[9] = rookWhite2;
        whiteFigures[10] = knightWhite1;
        whiteFigures[11] = knightWhite2;
        whiteFigures[12] = bishopWhite1;
        whiteFigures[13] = bishopWhite2;
        whiteFigures[14] = queenWhite;
        whiteFigures[15] = kingWhite;
        return whiteFigures;

    }

    private ImageView[] createBlackFigureImagesArray()
    {
        ImageView[] blackFigures = new ImageView[16];
        blackFigures[0] = pawnBlack1;
        blackFigures[1] = pawnBlack2;
        blackFigures[2] = pawnBlack3;
        blackFigures[3] = pawnBlack4;
        blackFigures[4] = pawnBlack5;
        blackFigures[5] = pawnBlack6;
        blackFigures[6] = pawnBlack7;
        blackFigures[7] = pawnBlack8;
        blackFigures[8] = rookBlack1;
        blackFigures[9] = rookBlack2;
        blackFigures[10] = knightBlack1;
        blackFigures[11] = knightBlack2;
        blackFigures[12] = bishopBlack1;
        blackFigures[13] = bishopBlack2;
        blackFigures[14] = queenBlack;
        blackFigures[15] = kingBlack;
        return blackFigures;
    }

    private void guiChangePlayer()
    {
        if(this.game.isWhiteOnTheMove())
        {
            setOpacityForImages(whiteFiguresImages, 1);
            setOpacityForImages(blackFiguresImages, 0.87);
            setCursorForImages(whiteFiguresImages, Cursor.HAND);
            setCursorForImages(blackFiguresImages, Cursor.DISAPPEAR);
        }
        else
        {
            setOpacityForImages(blackFiguresImages, 1);
            setOpacityForImages(whiteFiguresImages, 0.87);
            setCursorForImages(blackFiguresImages, Cursor.HAND);
            setCursorForImages(whiteFiguresImages, Cursor.DISAPPEAR);
        }
    }

    private void setOpacityForImages(List<ImageView> images, double opacity)
    {
        int size = images.size();
        for (int i = 0; i<size; i++)
        {
            images.get(i).setOpacity(opacity);
        }
    }

    private void setCursorForImages(List<ImageView> images, Cursor cursor)
    {
        int size = images.size();
        for (int i = 0; i<size; i++)
        {
            images.get(i).setCursor(cursor);
        }
    }

    private void onBoardClick(double clickX, double clickY)
    {
        System.out.println("["+getGuiFieldOnClickX(clickX)+", "+getGuiFieldOnClickY(clickY)+"]");

        int clickedX = getGuiFieldOnClickX(clickX);
        int clickedY = getGuiFieldOnClickY(clickY);
        if(clickedX != -1 && clickedY != -1)
        {
            if(game.setMovement(clickedX, clickedY) && game.isMovementCompletlySet())
            {
                boolean flagPerformMovement = game.performMovement();
                if(flagPerformMovement)
                {
                    guiMoveFigureImage(game.getImageOfMovFigure(), game.getGoalField().getColPos(), game.getGoalField().getRowPos());
                    board.setDisable(true);

                    if(game.isRemovingFigure())
                        game.getImageOfGoalFieldFigure().setOpacity(0.35);


                    Timeline timeline;
                    if(game.getIsChangingFigure())
                    {
                        timeline = new Timeline(new KeyFrame(Duration.millis((100*this.fieldDiff)+10), ev -> {
                            if(game.isRemovingFigure())
                                game.getImageOfGoalFieldFigure().setVisible(false);
                            board.setDisable(false);
                            showChangingFigures();
                            //timeline.cancel();
                        }));
                    }
                    else
                    {
                        timeline = new Timeline(new KeyFrame(Duration.millis((100*this.fieldDiff)+10), ev -> {
                            if(game.isRemovingFigure())
                                game.getImageOfGoalFieldFigure().setVisible(false);
                            board.setDisable(false);
                            this.closePlayerMovement();
                            //timeline.cancel();
                        }));
                    }
                    //timer.schedule(task, (100*this.fieldDiff)+10);
                    timeline.play();
                }
                else
                {
                    game.nullMovementManager();
                }
            }

        }
    }

    private void guiMoveFigureImage(ImageView image, int toFieldX, int toFieldY)
    {
        System.out.println("to: " +image.getTranslateX() +" " + image.getTranslateY());
        image.toFront();
        int fromFieldX = getGuiFieldOfImageX(image.getLayoutX());
        int fromFieldY = getGuiFieldOfImageY(image.getLayoutY());

        if(Math.abs(fromFieldX-toFieldX) > Math.abs(fromFieldY-toFieldY))
            this.fieldDiff = Math.abs(fromFieldX-toFieldX);
        else
            this.fieldDiff = Math.abs(fromFieldY-toFieldY);

        //double fromY = image.getTranslateY();
        //double fromX = image.getTranslateX();
        double toY = getGuiPositionOfImageY(toFieldY) - getGuiPositionOfImageY(fromFieldY);
        double toX = getGuiPositionOfImageX(toFieldX) - getGuiPositionOfImageX(fromFieldX);

        //System.out.println("from: " +fromX +" " + fromY);
        if(toX==0 && toY == 0)
        {
            image.setTranslateX(0);
            image.setTranslateY(0);
        }
        else
        {
            TranslateTransition move = new TranslateTransition(Duration.millis(100 * this.fieldDiff));
            move.setNode(image);
            //move.setFromY(fromY);
            move.setToY(toY);
            //move.setFromX(fromX);
            move.setToX(toX);
            move.play();
        }




    }


    private void setTextArea(List<String> arrayList, int lineToLight)
    {
        int size = arrayList.size();
        this.text.getChildren().clear();

        for (int i = 0; i<size; i++)
        {
            String element = arrayList.get(i)+"\n";
            Text t1 = new Text();
            t1.setStyle("-fx-fill: #000000; -fx-font-size: 16px;");
            if(i == lineToLight-1)
                t1.setStyle("-fx-fill: #FF0000;-fx-font-weight:bold; -fx-font-size: 16px;");
            t1.setText(element);
            this.text.getChildren().add(t1);
        }

        double one = 1/((double)size+1);
        if(lineToLight<0.1*size)
            this.scrollbar.setVvalue(((double) lineToLight/((double)size+1)) -one*1);
        else
            this.scrollbar.setVvalue(((double) lineToLight/((double)size+1)));
    }

    private void showChangingFigures()
    {
        if(this.game.isWhiteOnTheMove())
        {
            whiteFigures.setDisable(false);
            whiteFigures.setVisible(true);
            whiteFigures.toFront();
        }
        else
        {
            blackFigures.setDisable(false);
            blackFigures.setVisible(true);
            blackFigures.toFront();
        }
    }

    private void hideChangingFigures()
    {
        if(this.game.isWhiteOnTheMove())
        {
            whiteFigures.setDisable(true);
            whiteFigures.setVisible(false);
            whiteFigures.toBack();
        }
        else
        {
            blackFigures.setDisable(true);
            blackFigures.setVisible(false);
            blackFigures.toBack();
        }
    }

    private void closePlayerMovement()
    {
        game.addPlayerNotationMovement();
        setTextArea(game.getGameNotation(), game.getIndexOfGameNotation()+1);
        game.nullMovementManager();
        game.changePlayer();
        guiChangePlayer();
    }

    private List<ImageView> getListFromArray(ImageView[] array)
    {
        List<ImageView> list = new ArrayList<ImageView>();
        int length = array.length;
        for (int i = 0; i < length; i++)
        {
            list.add(array[i]);
        }

        return list;
    }

    private int getGuiFieldOnClickX(double position)
    {
        if(position>=35 && position<=110)
        {
            return 1;
        }
        else if(position>=114 && position<=188)
        {
            return 2;
        }
        else if(position>=194 && position<=267)
        {
            return 3;
        }
        else if(position>=273 && position<=347)
        {
            return 4;
        }
        else if(position>=352 && position<=426)
        {
            return 5;
        }
        else if(position>=431 && position<=506)
        {
            return 6;
        }
        else if(position>=511 && position<=587)
        {
            return 7;
        }
        else if(position>=591 && position<=665)
        {
            return 8;
        }
        else
        {
            return -1;
        }
    }

    private int getGuiFieldOnClickY(double position)
    {
        if(position>=35 && position<=110)
        {
            return 8;
        }
        else if(position>=114 && position<=188)
        {
            return 7;
        }
        else if(position>=194 && position<=267)
        {
            return 6;
        }
        else if(position>=273 && position<=347)
        {
            return 5;
        }
        else if(position>=352 && position<=426)
        {
            return 4;
        }
        else if(position>=431 && position<=506)
        {
            return 3;
        }
        else if(position>=511 && position<=587)
        {
            return 2;
        }
        else if(position>=591 && position<=665)
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }

    private double getGuiPositionOfImageX(int field)
    {
        if(field == 1)
        {
            return 32;
        }
        else if(field == 2)
        {
            return 112;
        }
        else if(field == 3)
        {
            return 191;
        }
        else if(field == 4)
        {
            return 271;
        }
        else if(field == 5)
        {
            return 350;
        }
        else if(field == 6)
        {
            return 430;
        }
        else if(field == 7)
        {
            return 509;
        }
        else if(field == 8)
        {
            return 588;
        }
        else
        {
            return -1;
        }
    }

    private double getGuiPositionOfImageY(int field)
    {
        if(field == 8)
        {
            return 32;
        }
        else if(field == 7)
        {
            return 112;
        }
        else if(field == 6)
        {
            return 191;
        }
        else if(field == 5)
        {
            return 271;
        }
        else if(field == 4)
        {
            return 350;
        }
        else if(field == 3)
        {
            return 430;
        }
        else if(field == 2)
        {
            return 509;
        }
        else if(field == 1)
        {
            return 588;
        }
        else
        {
            return -1;
        }
    }


    private int getGuiFieldOfImageX(double position)
    {
        if(position == 32)
        {
            return 1;
        }
        else if(position == 112)
        {
            return 2;
        }
        else if(position == 191)
        {
            return 3;
        }
        else if(position == 271)
        {
            return 4;
        }
        else if(position == 350)
        {
            return 5;
        }
        else if(position == 430)
        {
            return 6;
        }
        else if(position == 509)
        {
            return 7;
        }
        else if(position == 588)
        {
            return 8;
        }
        else
        {
            return -1;
        }
    }

    private int getGuiFieldOfImageY(double position)
    {
        if(position == 32)
        {
            return 8;
        }
        else if(position == 112)
        {
            return 7;
        }
        else if(position == 191)
        {
            return 6;
        }
        else if(position == 271)
        {
            return 5;
        }
        else if(position == 350)
        {
            return 4;
        }
        else if(position == 430)
        {
            return 3;
        }
        else if(position == 509)
        {
            return 2;
        }
        else if(position == 588)
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }
}

