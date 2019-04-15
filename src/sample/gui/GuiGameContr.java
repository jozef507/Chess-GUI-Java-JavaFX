package sample.gui;

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
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import sample.game.*;

public class GuiGameContr implements Initializable
{
    private Game game;
    private int fieldDiff;
    private ImageView[] whiteFiguresImages;
    private ImageView[] blackFiguresImages;
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
    public AnchorPane board;


    public void initialize(URL location, ResourceBundle resources)
    {
        whiteFiguresImages = createWhiteFigureImagesArray();
        blackFiguresImages = createBlackFigureImagesArray();
        game = new ChessGame(whiteFiguresImages, blackFiguresImages);

        setOpacityForImages(whiteFiguresImages, 1);
        setOpacityForImages(blackFiguresImages, 0.87);
        setCursorForImages(whiteFiguresImages, Cursor.HAND);
        setCursorForImages(blackFiguresImages, Cursor.DISAPPEAR);

        board.setPickOnBounds(true);
        board.setOnMouseClicked(e -> {
            onBoardClick(e.getX(), e.getY());
        });
    }

    private ImageView[] createWhiteFigureImagesArray()
    {
        ImageView[] whiteFigures = new ImageView[8];
        whiteFigures[0] = pawnWhite1;
        whiteFigures[1] = pawnWhite2;
        whiteFigures[2] = pawnWhite3;
        whiteFigures[3] = pawnWhite4;
        whiteFigures[4] = pawnWhite5;
        whiteFigures[5] = pawnWhite6;
        whiteFigures[6] = pawnWhite7;
        whiteFigures[7] = pawnWhite8;
        return whiteFigures;

    }

    private ImageView[] createBlackFigureImagesArray()
    {
        ImageView[] blackFigures = new ImageView[8];
        blackFigures[0] = pawnBlack1;
        blackFigures[1] = pawnBlack2;
        blackFigures[2] = pawnBlack3;
        blackFigures[3] = pawnBlack4;
        blackFigures[4] = pawnBlack5;
        blackFigures[5] = pawnBlack6;
        blackFigures[6] = pawnBlack7;
        blackFigures[7] = pawnBlack8;
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

    private void setOpacityForImages(ImageView[] images, double opacity)
    {
        int size = images.length;
        for (int i = 0; i<size; i++)
        {
            images[i].setOpacity(opacity);
        }
    }

    private void setCursorForImages(ImageView[] images, Cursor cursor)
    {
        int size = images.length;
        for (int i = 0; i<size; i++)
        {
            images[i].setCursor(cursor);
        }
    }

    private void onBoardClick(double clickX, double clickY)
    {
        System.out.println("["+getGuiFieldOnClickX(clickX)+", "+getGuiFieldOnClickY(clickY)+"]");

        int clickedX = getGuiFieldOnClickX(clickX);
        int clickedY = getGuiFieldOnClickY(clickY);
        if(clickedX != -1 && clickedY != -1)
        {
            game.setMovement(clickedX, clickedY);

            int flagPerformMovement = game.performMovement();
            if(flagPerformMovement==1 || flagPerformMovement==2)
            {


                guiMoveFigureImage(game.getImageOfMovFigure(), game.getGoalField().getColPos(), game.getGoalField().getRowPos());
                board.setDisable(true);

                if(flagPerformMovement==2)
                    game.getImageOfGoalFieldFigure().setOpacity(0.35);

                Timer timer = new Timer("Timer");
                TimerTask task = new TimerTask() {
                    public void run() {
                        if(flagPerformMovement==2)
                            game.getImageOfGoalFieldFigure().setVisible(false);

                        game.nullMovementManager();
                        game.changePlayer();
                        guiChangePlayer();
                        board.setDisable(false);
                        timer.cancel();
                    }
                };
                timer.schedule(task, (100*this.fieldDiff)+10);
            }
            else if(flagPerformMovement==-1)
            {
                game.nullMovementManager();
            }
        }
    }

    private void guiMoveFigureImage(ImageView image, int toFieldX, int toFieldY)
    {
        image.toFront();
        int fromFieldX = getGuiFieldOfImageX(image.getLayoutX());
        int fromFieldY = getGuiFieldOfImageY(image.getLayoutY());

        if(Math.abs(fromFieldX-toFieldX) > Math.abs(fromFieldY-toFieldY))
            this.fieldDiff = Math.abs(fromFieldX-toFieldX);
        else
            this.fieldDiff = Math.abs(fromFieldY-toFieldY);

        double fromY=image.getTranslateY();
        double fromX=image.getTranslateX();
        double toY = getGuiPositionOfImageY(toFieldY) - getGuiPositionOfImageY(fromFieldY);
        double toX = getGuiPositionOfImageX(toFieldX) - getGuiPositionOfImageX(fromFieldX);
        TranslateTransition move = new TranslateTransition(Duration.millis(100*this.fieldDiff));
        move.setFromY(fromY);
        move.setToY(toY);
        move.setFromX(fromX);
        move.setToX(toX);
        move.setNode(image);
        move.setAutoReverse(true);
        move.play();
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

