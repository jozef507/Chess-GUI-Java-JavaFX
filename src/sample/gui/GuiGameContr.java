package sample.gui;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class GuiGameContr implements Initializable
{

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
    public AnchorPane board;


    public void initialize(URL location, ResourceBundle resources)
    {
        board.setPickOnBounds(true);
        board.setOnMouseClicked(e -> {
            System.out.println("["+getGuiFieldOnClickX(e.getX())+", "+getGuiFieldOnClickY(e.getY())+"]");
        });

        /*pawnWhite1.setOnMouseClicked(event -> {
            PathTransition t = new PathTransition();
            t.setNode(pawnWhite1);
            t.setDuration(Duration.seconds(3));
            t.setPath(line);
            t.play();
        });*/
    }

    public void a1ButtonOnClick()
    {
        //System.out.println("ardflakjdfôladsjfaôsj");
    }

    public void boardClicked()
    {

    }


    public void pawnWhite1OnClick()
    {

        TranslateTransition move = new TranslateTransition(Duration.seconds(2));
        double fromY=0;
        double fromX=0;
        double toY = getGuiPositionOfImageY(5) - getGuiPositionOfImageY(getGuiFieldOfImageY(pawnWhite1.getLayoutY()));
        double toX = getGuiPositionOfImageX(5) - getGuiPositionOfImageX(getGuiFieldOfImageX(pawnWhite1.getLayoutX()));
        move.setFromY(fromY);
        move.setToY(toY);
        move.setFromX(fromX);
        move.setToX(toX);
        System.out.println(toX+ " " + toY);
        move.setNode(pawnWhite1);
        move.setAutoReverse(true);
        move.play();



        Timer timer = new Timer("Timer");
        TimerTask task = new TimerTask() {
            public void run() {
                move.setFromX(pawnWhite1.getTranslateX());
                move.setFromY(pawnWhite1.getTranslateY());
                move.setToX(getGuiPositionOfImageX(8) - getGuiPositionOfImageX(getGuiFieldOfImageX(pawnWhite1.getLayoutX())));
                move.setToY(getGuiPositionOfImageY(1) - getGuiPositionOfImageY(getGuiFieldOfImageY(pawnWhite1.getLayoutY())));
                move.setAutoReverse(true);
                move.setCycleCount(1);
                move.play();
                timer.cancel();
            }
        };


        timer.schedule(task, 2100);


        /*try {
            Thread.sleep(2100);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }*/




        System.out.println(pawnWhite1.getLayoutX()+ " " + pawnWhite1.getLayoutY()+ " " + pawnWhite1.getTranslateX()+ " " + pawnWhite1.getTranslateY());

        /*move.setOnFinished(event ->{
            System.out.println(pawnWhite1.getLayoutX()+ " " + pawnWhite1.getLayoutY()+ " " + pawnWhite1.getTranslateX()+ " " + pawnWhite1.getTranslateY());
        });*/

        /*final Circle circle = new Circle(20, 20, 15);
        circle.setFill(Color.DARKRED);*/


    }

    public int getGuiFieldOnClickX(double position)
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

    public int getGuiFieldOnClickY(double position)
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

