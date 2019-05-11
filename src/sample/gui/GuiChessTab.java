/**
 * Trieda reprezentujúca graficky vytvorí tab so šachovnicou a všetkými potrebnými komponentami.
 * @author Ján Folenta
 * @author Jozef Ondria
 */

package sample.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Tab;

import java.io.IOException;

/**
 * Objekt jedného tabu jednej šachovej partie.
 */
public class GuiChessTab
{
    private Tab tab;

    /**
     * Vytvorí tab s novou šachovou partiou.
     * @param gameNumber
     */
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

    /**
     * Vracia tab objektu.
     * @return
     */
    public Tab getTab()
    {
        return this.tab;
    }

}
