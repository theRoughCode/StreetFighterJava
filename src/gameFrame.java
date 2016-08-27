import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.LayoutManager;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class gameFrame
extends JFrame {
    ActionPanel actionPanel;

    gameFrame(int width, int height, int p1, int p2, Image stage) throws IOException {
        this.setDefaultCloseOperation(3);
        this.setSize(width + 5, height + 70);
        this.setVisible(false);
        this.setTitle("Street Fighter");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        Container containPanels = this.getContentPane();
        containPanels.setLayout(new BoxLayout(containPanels, 3));
        this.actionPanel = new ActionPanel(p1, p2, width, height);
        containPanels.add(this.actionPanel);
        this.actionPanel.bg = stage;
    }
}