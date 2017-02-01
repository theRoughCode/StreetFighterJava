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

public class Controls
extends JFrame
implements ActionListener {
    Timer time;
    boolean finished = false;

    Controls() {
        this.setSize(560, 500);
        this.setDefaultCloseOperation(3);
        this.setTitle("Street Fighter :: Controls");
        this.setFocusable(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.addKeyListener((KeyListener)new controlsKeys(this));
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
        bufferGraphics.drawString("Controls", this.getWidth() / 2 - (int)bufferGraphics.getFontMetrics().getStringBounds("Controls", bufferGraphics).getWidth() / 2, 60);
        bufferGraphics.fillRect(30, 70, 500, 5);
        bufferGraphics.setFont(new Font("DialogInput", 1, 26));
        bufferGraphics.drawString("Jump", 20, 160);
        bufferGraphics.drawString("Crouch", 20, 190);
        bufferGraphics.drawString("Move Left", 20, 220);
        bufferGraphics.drawString("Move Right", 20, 250);
        bufferGraphics.drawString("Light Punch", 20, 280);
        bufferGraphics.drawString("Medium Punch", 20, 310);
        bufferGraphics.drawString("Hard Punch", 20, 340);
        bufferGraphics.drawString("Light Kick", 20, 370);
        bufferGraphics.drawString("Medium Kick", 20, 400);
        bufferGraphics.drawString("Hard Kick", 20, 430);
        bufferGraphics.setColor(Color.red.brighter());
        bufferGraphics.drawString("Player 1", 240, 120);
        bufferGraphics.drawString("W", 290, 160);
        bufferGraphics.drawString("S", 290, 190);
        bufferGraphics.drawString("A", 290, 220);
        bufferGraphics.drawString("D", 290, 250);
        bufferGraphics.drawString("C", 290, 280);
        bufferGraphics.drawString("V", 290, 310);
        bufferGraphics.drawString("B", 290, 340);
        bufferGraphics.drawString("F", 290, 370);
        bufferGraphics.drawString("G", 290, 400);
        bufferGraphics.drawString("H", 290, 430);
        bufferGraphics.setColor(Color.blue.brighter());
        bufferGraphics.drawString("Player 2", 400, 120);
        bufferGraphics.drawString("P", 450, 160);
        bufferGraphics.drawString(";", 450, 190);
        bufferGraphics.drawString("L", 450, 220);
        bufferGraphics.drawString("'", 450, 250);
        bufferGraphics.drawString("NUMPAD1", 410, 280);
        bufferGraphics.drawString("NUMPAD2", 410, 310);
        bufferGraphics.drawString("NUMPAD3", 410, 340);
        bufferGraphics.drawString("NUMPAD4", 410, 370);
        bufferGraphics.drawString("NUMPAD5", 410, 400);
        bufferGraphics.drawString("NUMPAD6", 410, 430);
        bufferGraphics.setColor(Color.white.brighter());
        bufferGraphics.drawString("Press SPACE to exit", this.getWidth() / 2 - (int)bufferGraphics.getFontMetrics().getStringBounds("Press SPACE to exit", bufferGraphics).getWidth() / 2, 480);
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

class controlsKeys extends KeyAdapter {
    final /* synthetic */ Controls this$0;

    controlsKeys(Controls controls) {
        this.this$0 = controls;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == 32) {
            this.this$0.finished = true;
        }
    }
}