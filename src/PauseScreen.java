import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class PauseScreen
extends JFrame
implements ActionListener {
    Timer time;
    int option = 1;
    boolean chosen = false;

    PauseScreen() {
        this.setSize(260, 250);
        this.setDefaultCloseOperation(3);
        this.setTitle("Street Fighter :: Paused");
        this.setFocusable(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.addKeyListener((KeyListener)new PauseScreenKeys(this));
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
        bufferGraphics.drawString("Pause", this.getWidth() / 2 - (int)bufferGraphics.getFontMetrics().getStringBounds("Pause", bufferGraphics).getWidth() / 2, 60);
        bufferGraphics.fillRect(30, 70, 200, 5);
        bufferGraphics.setFont(new Font("SansSerif", 1, 20));
        if (this.option == 1) {
            bufferGraphics.setColor(Color.yellow.brighter());
        } else {
            bufferGraphics.setColor(Color.yellow.darker());
        }
        bufferGraphics.drawString("Resume", this.getWidth() / 2 - (int)bufferGraphics.getFontMetrics().getStringBounds("Resume", bufferGraphics).getWidth() / 2, 115);
        if (this.option == 2) {
            bufferGraphics.setColor(Color.yellow.brighter());
        } else {
            bufferGraphics.setColor(Color.yellow.darker());
        }
        bufferGraphics.drawString("Stage Select", this.getWidth() / 2 - (int)bufferGraphics.getFontMetrics().getStringBounds("Stage Select", bufferGraphics).getWidth() / 2, 145);
        if (this.option == 3) {
            bufferGraphics.setColor(Color.yellow.brighter());
        } else {
            bufferGraphics.setColor(Color.yellow.darker());
        }
        bufferGraphics.drawString("Help", this.getWidth() / 2 - (int)bufferGraphics.getFontMetrics().getStringBounds("Help", bufferGraphics).getWidth() / 2, 175);
        if (this.option == 4) {
            bufferGraphics.setColor(Color.yellow.brighter());
        } else {
            bufferGraphics.setColor(Color.yellow.darker());
        }
        bufferGraphics.drawString("Controls", this.getWidth() / 2 - (int)bufferGraphics.getFontMetrics().getStringBounds("Controls", bufferGraphics).getWidth() / 2, 205);
        if (this.option == 5) {
            bufferGraphics.setColor(Color.yellow.brighter());
        } else {
            bufferGraphics.setColor(Color.yellow.darker());
        }
        bufferGraphics.drawString("Quit Game", this.getWidth() / 2 - (int)bufferGraphics.getFontMetrics().getStringBounds("Quit Game", bufferGraphics).getWidth() / 2, 235);
        g.drawImage(bufferImage, 0, 0, this);
    }

    @Override
    public void actionPerformed(ActionEvent w) {
        this.repaint();
        if (this.chosen && this.option != 4 && this.option != 3) {
            this.dispose();
        }
    }
}

class PauseScreenKeys
extends KeyAdapter {
    final /* synthetic */ PauseScreen this$0;

    PauseScreenKeys(PauseScreen pauseScreen) {
        this.this$0 = pauseScreen;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == 40) {
            try {
                Sound.fxPlay("/Audio/selectFX.wav");
            }
            catch (IOException | LineUnavailableException | UnsupportedAudioFileException e1) {
                e1.printStackTrace();
            }
            this.this$0.option = this.this$0.option == 5 ? 1 : ++this.this$0.option;
        } else if (key == 38) {
            try {
                Sound.fxPlay("/Audio/selectFX.wav");
            }
            catch (IOException | LineUnavailableException | UnsupportedAudioFileException e1) {
                e1.printStackTrace();
            }
            this.this$0.option = this.this$0.option == 1 ? 5 : --this.this$0.option;
        } else if (key == 32) {
            this.this$0.chosen = true;
        } else if (key == 27) {
            this.this$0.chosen = true;
            this.this$0.option = 1;
        }
    }
}