/**
 * Trieda reprezentujúca controller pre grafický návrh v gamemanager.fxml.
 * @author Jozef Ondria
 */

package sample.gui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Trieda, ktorá je controllerom(riadí tento grafický návrh) grafického
 * návrhu obsiahnutom v súbore gamemanager.fxml.
 */
public class GuiGamesManagerContr implements Initializable
{

    private int pathCounter;
    @FXML
    public VBox gamePaths;
    @FXML
    public Text infoText;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        pathCounter = 1;
    }

    /**
     * Definuje akciu po kliknutí na tlačítko Add.
     * @param actionEvent
     */
    public void AddButtonClick(ActionEvent actionEvent)
    {
        infoText.setVisible(false);
        if (pathCounter==10)
        {
            infoText.setText("You can run max 10 games!");
            infoText.setVisible(true);
            return;
        }
        pathCounter++;
        HBox newHBox = new HBox();
        newHBox.setAlignment(Pos.CENTER);
        newHBox.setPrefWidth(599);
        newHBox.setPrefHeight(89);
        newHBox.setMinHeight(89);
        newHBox.setSpacing(38);

        Text text = new Text("Notation "+pathCounter + " path");
        TextField newField = new TextField();
        newField.setPrefWidth(430);
        newField.setPrefHeight(26);
        newField.setId("path"+pathCounter);

        newHBox.getChildren().add(text);
        newHBox.getChildren().add(newField);

        try {
            gamePaths.getChildren().add(newHBox);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    /**
     * Definuje akciu po kliknutí na tlačitko Remove.
     * @param actionEvent
     */
    public void RemoveButtonClick(ActionEvent actionEvent)
    {
        infoText.setVisible(false);
        if(pathCounter==1)
        {
            infoText.setText("You can run min 1 game!");
            infoText.setVisible(true);
            return;
        }
        int size = gamePaths.getChildren().size();

        try {
            gamePaths.getChildren().remove(size-1);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        pathCounter--;
    }

    /**
     * Definuje akciu po kliknutí na tlačítko Submit.
     */
    public void SubmitButtonClick()
    {
        infoText.setVisible(false);
        ObservableList list = gamePaths.getChildren();
        int size = list.size();
        String filepathArray[] = new String[size];

        for(int i = 0; i<size; i++)
        {
            HBox filename = (HBox)list.get(i);
            TextField field = (TextField) filename.getChildren().get(1);
            String filepath = field.getText().trim();
            if(filepath.isEmpty())
            {
                filepathArray = null;
                infoText.setText("Text fields can not be empty!");
                infoText.setVisible(true);
                return;
            }

            File f = new File(filepath);
            if(!f.isFile())
            {
                infoText.setText("Path " + (i+1) + " to file is incorrect!");
                infoText.setVisible(true);
                return;
            }
            filepathArray[i] = filepath;
        }

        Stage stage = (Stage) gamePaths.getScene().getWindow();
        stage.close();
        GuiGameFactory.createChessScreen(filepathArray);
    }
}
