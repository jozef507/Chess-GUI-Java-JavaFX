/**
 * Trieda graficky vytvorí potrebné taby pre hru.
 * @author Ján Folenta
 * @author Jozef Ondria
 */

package sample.gui;

import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 * Pomocná abstraktna trieda, ktorá vytvára potrebné taby šachových partií.
 */
public abstract class GuiGameFactory
{
    public static String[] stringFilepathArray;
    public static int counter = 0;

    /**
     * Vytvrára potrebné taby šachových partií.
     * @param filepathArray
     */
    public static void createChessScreen(String[] filepathArray)
    {
        // set title for the stage
        Stage chessStage = new Stage();
        chessStage.setTitle("IJA Chess");

        // create a tabpane
        TabPane tabpane = new TabPane();
        //tabpane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // create multiple tabs
        int gameCount = filepathArray.length;
        stringFilepathArray = filepathArray;
        for (int i = 0; i < gameCount; i++)
        {
            GuiChessTab tab = new GuiChessTab(i+1);
            tabpane.getTabs().add(tab.getTab());
        }

        Scene scene = new Scene(tabpane, 965, 740);
        chessStage.setScene(scene);
        chessStage.show();

    }
}
