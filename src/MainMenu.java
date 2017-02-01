import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
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
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;

public class MainMenu
extends JFrame
implements ActionListener {
    Timer time;
    Image bg;
    int width;
    int height;
    long lastTime = 0;
    boolean white = false;
    boolean start = false;
    Font razer;

    MainMenu() {
        InputStream is;
        try {
            Sound.play((String)"/Audio/startmenu.wav", (boolean)true);
        }
        catch (IOException | LineUnavailableException | UnsupportedAudioFileException var1_1) {
            // empty catch block
        }
        try {
            is = this.getClass().getResource("RAZEROBLIQUE.ttf").openStream();
        }
        catch (IOException e1) {
            e1.printStackTrace();
            return;
        }
        try {
            this.razer = Font.createFont(0, is);
        }
        catch (FontFormatException | IOException e) {
            return;
        }
        this.setSize(960, 600);
        this.setDefaultCloseOperation(3);
        this.setTitle("Street Fighter ::  An ICShit Game by Raphael Koh");
        this.setFocusable(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setBackground(Color.black);
        this.setResizable(false);
        this.addKeyListener((KeyListener)new MainMenuKeys(this));
        ImageIcon i = new ImageIcon(this.getClass().getClassLoader().getResource("sfmainmenu.png"));
        this.bg = i.getImage();
        this.width = i.getIconWidth();
        this.height = i.getIconHeight();
        this.time = new Timer(5, this);
        this.time.start();
    }

    @Override
    public void paint(Graphics g) {
        Image bufferImage = this.createImage(this.getSize().width, this.getSize().height);
        Graphics bufferGraphics = bufferImage.getGraphics();
        bufferGraphics.drawImage(this.bg, 85, 50, this.width / 2, this.height / 2, null);
        bufferGraphics.setFont(this.razer.deriveFont(1, 65.0f));
        bufferGraphics.setColor(Color.GREEN);
        bufferGraphics.drawString("JAVA EDITION", this.getWidth() / 2 - (int)bufferGraphics.getFontMetrics().getStringBounds("JAVA EDITION", bufferGraphics).getWidth() / 2, this.getHeight() - 100);
        bufferGraphics.setFont(new Font("Monospaced", 1, 17));
        bufferGraphics.setColor(Color.green.brighter());
        bufferGraphics.drawString("An ICShit Game by Raphael Koh", this.getWidth() - 300, this.getHeight() - 10);
        bufferGraphics.drawString("\u00a9 2012 - 2013", 20, this.getHeight() - 10);
        bufferGraphics.setFont(this.razer.deriveFont(1, 25.0f));
        if (System.currentTimeMillis() - this.lastTime > 100) {
            this.white = !this.white;
            this.lastTime = System.currentTimeMillis();
        }
        if (this.white) {
            bufferGraphics.setColor(Color.white.brighter());
        } else {
            bufferGraphics.setColor(Color.yellow.brighter());
        }
        bufferGraphics.drawString("Press SPACE to start", this.getWidth() / 2 - (int)bufferGraphics.getFontMetrics().getStringBounds("Press SPACE to start", bufferGraphics).getWidth() / 2, this.getHeight() - 50);
        g.drawImage(bufferImage, 0, 0, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.repaint();
        if (this.start) {
            this.dispose();
        }
    }
}

class MainMenuKeys
extends KeyAdapter {
    final /* synthetic */ MainMenu this$0;

    MainMenuKeys(MainMenu mainMenu) {
        this.this$0 = mainMenu;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == 32) {
            try {
                Sound.fxPlay("/Audio/startmenuFX.wav");
            }
            catch (IOException | LineUnavailableException | UnsupportedAudioFileException var3_3) {
                // empty catch block
            }
            this.this$0.start = true;
        }
    }
}