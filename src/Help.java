import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Help
extends JFrame
implements ActionListener {
    Timer time;
    boolean finished = false;

    Help() {
        this.setSize(560, 350);
        this.setDefaultCloseOperation(3);
        this.setTitle("Street Fighter :: Help");
        this.setFocusable(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.addKeyListener((KeyListener)new helpKeys(this));
        this.setBackground(Color.black);
        this.time = new Timer(5, this);
        this.time.start();
    }

    @Override
    public void paint(Graphics g) {
        Image bufferImage = this.createImage(this.getSize().width, this.getSize().height);
        Graphics bufferGraphics = bufferImage.getGraphics();
        bufferGraphics.setFont(new Font("DialogInput", 1, 40));
        bufferGraphics.setColor(Color.yellow.brighter());
        bufferGraphics.drawString("Help", this.getWidth() / 2 - (int)bufferGraphics.getFontMetrics().getStringBounds("Help", bufferGraphics).getWidth() / 2, 60);
        bufferGraphics.fillRect(30, 70, 500, 5);
        bufferGraphics.setFont(new Font("DialogInput", 1, 16));
        bufferGraphics.drawString("Street Fighter: Java Edition is based off the original", 10, 120);
        bufferGraphics.drawString("world-famous arcade game made in Japan by Capcom.", 11, 150);
        bufferGraphics.drawString("In this game, players can choose from a variety of ", 11, 180);
        bufferGraphics.drawString("characters and maps, and compete against a friend.", 11, 210);
        bufferGraphics.drawString("Street Fighter: Java Edition was created by ", 11, 240);
        bufferGraphics.setColor(Color.white.brighter());
        bufferGraphics.drawString("Press SPACE to exit", this.getWidth() / 2 - (int)bufferGraphics.getFontMetrics().getStringBounds("Press SPACE to exit", bufferGraphics).getWidth() / 2, this.getHeight() - 10);
        bufferGraphics.setFont(new Font("Dialog", 1, 40));
        bufferGraphics.setColor(Color.GREEN.brighter());
        bufferGraphics.drawString("Raphael Koh", this.getWidth() / 2 - (int)bufferGraphics.getFontMetrics().getStringBounds("Raphael Koh", bufferGraphics).getWidth() / 2, 300);
        g.drawImage(bufferImage, 0, 0, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.repaint();
        if (this.finished) {
            this.dispose();
        }
    }
}

class helpKeys
extends KeyAdapter {
    final /* synthetic */ Help this$0;

    helpKeys(Help help) {
        this.this$0 = help;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == 32) {
            this.this$0.finished = true;
        }
    }
}