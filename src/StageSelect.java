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
import java.io.PrintStream;
import java.net.URL;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;

public class StageSelect
extends JFrame
implements ActionListener {
    Timer time;
    String[] mapName = new String[]{"Flaccid Snow", "Fishy Boardwalk", "Meditate", "Pussy Alley", "Midnight Jazz", "Chinatown", "Fortune", "Fight School", "Feast Hall", "Festival", "Nyan Cat", "Lost City", "Morning Delivery", "Pearl Harbour", "Random"};
    String[] mapAudioURL = new String[]{"song 20", "guile", "song 29", "pokemon", "jazz", "ryu", "chineserestaurant", "Juri", "feilong", "awesometothemax", "nyancat", "indianajones", "gundamseed", "Demons with Ryu", "Random"};
    Image[] mapImage = new Image[15];
    Image title;
    Image bg;
    Image select;
    ImageIcon img;
    int titleWidth;
    int titleHeight;
    int iconWidth = 621;
    int iconHeight = 240;
    int row = 1;
    int col = 1;
    int choice;
    boolean finished = false;
    Font razer;

    StageSelect() {
        InputStream is;
        this.setSize(960, 560);
        this.setDefaultCloseOperation(3);
        this.setTitle("Street Fighter :: Stage Select");
        this.setFocusable(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.addKeyListener((KeyListener)new StageSelectKeys(this));
        try {
            is = this.getClass().getClassLoader().getResource("RAZEROBLIQUE.ttf").openStream();
        }
        catch (IOException e1) {
            return;
        }
        try {
            this.razer = Font.createFont(0, is);
        }
        catch (FontFormatException | IOException e) {
            System.out.println("cannot read");
            return;
        }
        this.time = new Timer(5, this);
        this.time.start();
        this.initImages();
    }

    public void playAudio() {
        try {
            Sound.play("/Audio/menuAudio.wav", true);
        }
        catch (UnsupportedAudioFileException var1_1) {
        }
        catch (IOException var1_2) {
        }
        catch (LineUnavailableException var1_3) {
            // empty catch block
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.repaint();
        if (this.finished) {
            this.dispose();
        }
    }

    @Override
    public void paint(Graphics g) {
        Image bufferImage = this.createImage(this.getSize().width, this.getSize().height);
        Graphics bufferGraphics = bufferImage.getGraphics();
        bufferGraphics.drawImage(this.bg, 0, 0, this.getWidth(), this.getHeight(), null);
        bufferGraphics.drawImage(this.title, 220, 50, this.titleWidth / 2, this.titleHeight / 2, null);
        int i = 0;
        while (i < this.mapImage.length) {
            if (i < 5) {
                bufferGraphics.drawImage(this.mapImage[i], 100 + 163 * i, 220, this.iconWidth / 5, this.iconHeight / 5, this);
            } else if (i < 10) {
                bufferGraphics.drawImage(this.mapImage[i], 100 + 163 * (i - 5), 320, this.iconWidth / 5, this.iconHeight / 5, this);
            } else {
                bufferGraphics.drawImage(this.mapImage[i], 100 + 163 * (i - 10), 420, this.iconWidth / 5, this.iconHeight / 5, this);
            }
            ++i;
        }
        if (this.row == 1) {
            bufferGraphics.drawImage(this.select, 98 + 163 * (this.col - 1), 218, (this.iconWidth + 10) / 5, (this.iconHeight + 10) / 5, null);
        } else if (this.row == 2) {
            bufferGraphics.drawImage(this.select, 98 + 163 * (this.col - 1), 318, (this.iconWidth + 10) / 5, (this.iconHeight + 10) / 5, null);
        } else if (this.row == 3) {
            bufferGraphics.drawImage(this.select, 98 + 163 * (this.col - 1), 418, (this.iconWidth + 10) / 5, (this.iconHeight + 10) / 5, null);
        }
        bufferGraphics.setFont(this.razer.deriveFont(1, 30.0f));
        bufferGraphics.setColor(Color.yellow.brighter());
        bufferGraphics.drawString(this.mapName[(this.row - 1) * 5 + (this.col - 1)], this.getWidth() / 2 - (int)bufferGraphics.getFontMetrics().getStringBounds(this.mapName[(this.row - 1) * 5 + (this.col - 1)], bufferGraphics).getWidth() / 2, 180);
        g.drawImage(bufferImage, 0, 0, this);
    }

    int random() {
        return (int)(Math.random() * 13.0);
    }

    void playSelectFX() {
        try {
            Sound.fxPlay("/Audio/selectFX.wav");
        }
        catch (IOException | LineUnavailableException | UnsupportedAudioFileException var1_1) {
            // empty catch block
        }
    }

    void initImages() {
        this.img = new ImageIcon(this.getClass().getClassLoader().getResource("MapSelect/stageSelectTitle.png"));
        this.title = this.img.getImage();
        this.titleWidth = this.img.getIconWidth();
        this.titleHeight = this.img.getIconHeight();
        this.img = new ImageIcon(this.getClass().getClassLoader().getResource("MapSelect/stageSelectBg.jpg"));
        this.bg = this.img.getImage();
        this.img = new ImageIcon(this.getClass().getClassLoader().getResource("MapSelect/stageChoice.png"));
        this.select = this.img.getImage();
        int i = 0;
        while (i < this.mapImage.length) {
            this.img = i < 11 ? new ImageIcon(this.getClass().getClassLoader().getResource("MapSelect/map" + (i + 1) + ".gif")) : new ImageIcon(this.getClass().getClassLoader().getResource("MapSelect/map" + (i - 10) + ".jpg"));
            if (this.img.getImage() == null) {
                System.out.println("Image " + i + "doesnt exist");
            }
            this.mapImage[i] = this.img.getImage();
            ++i;
        }
    }
}

class StageSelectKeys
extends KeyAdapter {
    final /* synthetic */ StageSelect this$0;

    StageSelectKeys(StageSelect stageSelect) {
        this.this$0 = stageSelect;
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
            this.this$0.choice = this.this$0.row == 3 && this.this$0.col == 5 ? this.this$0.random() : (this.this$0.row - 1) * 5 + (this.this$0.col - 1);
            this.this$0.finished = true;
        }
        if (key == 39) {
            this.this$0.playSelectFX();
            this.this$0.col = this.this$0.col == 5 ? 1 : ++this.this$0.col;
        } else if (key == 37) {
            this.this$0.playSelectFX();
            this.this$0.col = this.this$0.col == 1 ? 5 : --this.this$0.col;
        } else if (key == 38) {
            this.this$0.playSelectFX();
            this.this$0.row = this.this$0.row == 1 ? 3 : --this.this$0.row;
        } else if (key == 40) {
            this.this$0.playSelectFX();
            this.this$0.row = this.this$0.row == 3 ? 1 : ++this.this$0.row;
        }
    }
}