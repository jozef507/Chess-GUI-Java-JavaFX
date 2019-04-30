package sample.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Tab;

import java.io.IOException;

public class GuiChessTab
{
    private Tab tab;
    public GuiChessTab(int gameNumber)
    {
        try {
            tab = new Tab();
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("chessboard.fxml"));
            Parent root = (Parent) loader.load();
            tab.setContent((Node) root);
            tab.setText("Game " + gameNumber);
            GuiGameContr controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Tab getTab()
    {
        return this.tab;
    }

}
