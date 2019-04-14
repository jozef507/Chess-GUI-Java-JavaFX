package sample.gui;

import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

abstract class GuiGameFactory
{
    public static void createChessScreen(String[] filepathArray)
    {
        // set title for the stage
        Stage chessStage = new Stage();
        chessStage.setTitle("Creating Tab");

        // create a tabpane
        TabPane tabpane = new TabPane();
        tabpane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // create multiple tabs
        int gameCount = filepathArray.length;
        for (int i = 0; i < gameCount; i++)
        {
            GuiChessTab tab = new GuiChessTab(i+1);
            tabpane.getTabs().add(tab.getTab());
        }

        Scene scene = new Scene(tabpane, 900, 740);
        chessStage.setScene(scene);
        chessStage.show();

    }
}