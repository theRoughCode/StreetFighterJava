import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;

public class ChampSelect
extends JFrame
implements ActionListener {
    Image charSelect;
    Image selectTitle;
    Image p1Select;
    Image p2Select;
    Image ryu;
    Image dhalsim;
    Image chunli;
    Image ken;
    Image blanka;
    Image nanase;
    Image hokuto;
    Image cjack;
    Image sharon;
    Image vega;
    Image sakura;
    Image random;
    ImageIcon i;
    int charSelectWidth;
    int charSelectHeight;
    int iconWidth;
    int iconHeight;
    int titleWidth;
    int titleHeight;
    int charWidth;
    int charHeight;
    BufferedImage[] iconArray = new BufferedImage[12];
    String[] charName = new String[]{"Vega", "Ryu", "Sakura", "Hokuto", "Nanase", "Cracker Jack", "Chun Li", "Ken", "Blanka", "Dhalsim", "Sharon", "Random"};
    Timer time;
    int p1row = 1;
    int p1col = 1;
    int p2row = 1;
    int p2col = 6;
    boolean p1locked = false;
    boolean p2locked = false;
    boolean finished = false;
    boolean start = false;
    int p1Choice;
    int p2Choice;
    Font razer;

    ChampSelect() throws IOException {
        this.setVisible(false);
        this.setSize(960, 560);
        this.setDefaultCloseOperation(3);
        this.setTitle("Street Fighter :: Character Select");
        this.setFocusable(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.addKeyListener((KeyListener)new keys(this));
        InputStream is1 = this.getClass().getClassLoader().getResource("RAZEROBLIQUE.ttf").openStream();
        try {
            this.razer = Font.createFont(0, is1);
        }
        catch (FontFormatException | IOException e) {
            System.out.println("cannot read");
            return;
        }
        this.time = new Timer(5, this);
        this.time.start();
        this.i = new ImageIcon(this.getClass().getClassLoader().getResource("CharSelect/charSelectBG.jpg"));
        this.charSelect = this.i.getImage();
        this.charSelectWidth = this.i.getIconWidth();
        this.charSelectHeight = this.i.getIconHeight();
        this.i = new ImageIcon(this.getClass().getClassLoader().getResource("CharSelect/selectTitle2.png"));
        this.selectTitle = this.i.getImage();
        this.titleWidth = this.i.getIconWidth();
        this.titleHeight = this.i.getIconHeight();
        this.i = new ImageIcon(this.getClass().getClassLoader().getResource("CharSelect/ryuSelect.png"));
        this.ryu = this.i.getImage();
        this.i = new ImageIcon(this.getClass().getClassLoader().getResource("CharSelect/chunliSelect.png"));
        this.chunli = this.i.getImage();
        this.i = new ImageIcon(this.getClass().getClassLoader().getResource("CharSelect/kenSelect.png"));
        this.ken = this.i.getImage();
        this.i = new ImageIcon(this.getClass().getClassLoader().getResource("CharSelect/blankaSelect.png"));
        this.blanka = this.i.getImage();
        this.i = new ImageIcon(this.getClass().getClassLoader().getResource("CharSelect/nanaseSelect.png"));
        this.nanase = this.i.getImage();
        this.i = new ImageIcon(this.getClass().getClassLoader().getResource("CharSelect/hokutoSelect.png"));
        this.hokuto = this.i.getImage();
        this.i = new ImageIcon(this.getClass().getClassLoader().getResource("CharSelect/crackerjackSelect.png"));
        this.cjack = this.i.getImage();
        this.i = new ImageIcon(this.getClass().getClassLoader().getResource("CharSelect/sharonSelect.png"));
        this.sharon = this.i.getImage();
        this.i = new ImageIcon(this.getClass().getClassLoader().getResource("CharSelect/vegaSelect.png"));
        this.vega = this.i.getImage();
        this.i = new ImageIcon(this.getClass().getClassLoader().getResource("CharSelect/sakuraSelect.png"));
        this.sakura = this.i.getImage();
        this.i = new ImageIcon(this.getClass().getClassLoader().getResource("CharSelect/randomSelect.png"));
        this.random = this.i.getImage();
        this.i = new ImageIcon(this.getClass().getClassLoader().getResource("CharSelect/dhalsimSelect.png"));
        this.dhalsim = this.i.getImage();
        this.charWidth = this.i.getIconWidth();
        this.charHeight = this.i.getIconHeight();
        this.i = new ImageIcon(this.getClass().getClassLoader().getResource("CharSelect/p1.png"));
        this.p1Select = this.i.getImage();
        this.i = new ImageIcon(this.getClass().getClassLoader().getResource("CharSelect/p2.png"));
        this.p2Select = this.i.getImage();
        InputStream is = this.getClass().getClassLoader().getResource("CharSelect/charselect2.jpg").openStream();
        BufferedImage sheet = ImageIO.read(is);
        this.iconArray[0] = sheet.getSubimage(6, 3, 98, 123);
        this.iconArray[1] = sheet.getSubimage(133, 3, 98, 123);
        this.iconArray[2] = sheet.getSubimage(261, 3, 98, 123);
        this.iconArray[3] = sheet.getSubimage(390, 3, 98, 123);
        this.iconArray[4] = sheet.getSubimage(519, 3, 98, 123);
        this.iconArray[5] = sheet.getSubimage(646, 3, 98, 123);
        this.iconArray[6] = sheet.getSubimage(6, 133, 98, 123);
        this.iconArray[7] = sheet.getSubimage(133, 133, 98, 123);
        this.iconArray[8] = sheet.getSubimage(261, 133, 98, 123);
        this.iconArray[9] = sheet.getSubimage(390, 133, 98, 123);
        this.iconArray[10] = sheet.getSubimage(519, 133, 98, 123);
        this.iconArray[11] = sheet.getSubimage(646, 133, 98, 123);
        this.iconWidth = this.iconArray[0].getWidth();
        this.iconHeight = this.iconArray[0].getHeight();
        int i = 0;
        while (i < 12) {
            if (i != 1 && i != 9 && i != 11) {
                ColorSpace cs = ColorSpace.getInstance(1003);
                ColorConvertOp op = new ColorConvertOp(cs, null);
                this.iconArray[i] = op.filter(this.iconArray[i], null);
            }
            ++i;
        }
    }

    Image charChosen(int player) {
        if (player == 1) {
            if (this.p1row == 1 && this.p1col == 2) {
                this.p1Choice = 1;
                return this.ryu;
            }
            if (this.p1row == 2 && this.p1col == 4) {
                this.p1Choice = 9;
                return this.dhalsim;
            }
            if (this.p1row == 2 && this.p1col == 6) {
                this.p1Choice = 11;
                return this.random;
            }
            if (this.p1row == 1) {
                switch (this.p1col) {
                    case 1: {
                        this.p1Choice = 0;
                        return this.vega;
                    }
                    case 3: {
                        this.p1Choice = 2;
                        return this.sakura;
                    }
                    case 4: {
                        this.p1Choice = 3;
                        return this.hokuto;
                    }
                    case 5: {
                        this.p1Choice = 4;
                        return this.nanase;
                    }
                    case 6: {
                        this.p1Choice = 5;
                        return this.cjack;
                    }
                }
            } else {
                switch (this.p1col) {
                    case 1: {
                        this.p1Choice = 6;
                        return this.chunli;
                    }
                    case 2: {
                        this.p1Choice = 7;
                        return this.ken;
                    }
                    case 3: {
                        this.p1Choice = 8;
                        return this.blanka;
                    }
                    case 5: {
                        this.p1Choice = 10;
                        return this.sharon;
                    }
                }
            }
            return null;
        }
        if (this.p2row == 1 && this.p2col == 2) {
            this.p2Choice = 1;
            return this.ryu;
        }
        if (this.p2row == 2 && this.p2col == 4) {
            this.p2Choice = 9;
            return this.dhalsim;
        }
        if (this.p2row == 2 && this.p2col == 6) {
            this.p2Choice = 11;
            return this.random;
        }
        if (this.p2row == 1) {
            switch (this.p2col) {
                case 1: {
                    this.p2Choice = 0;
                    return this.vega;
                }
                case 3: {
                    this.p2Choice = 2;
                    return this.sakura;
                }
                case 4: {
                    this.p2Choice = 3;
                    return this.hokuto;
                }
                case 5: {
                    this.p2Choice = 4;
                    return this.nanase;
                }
                case 6: {
                    this.p2Choice = 5;
                    return this.cjack;
                }
            }
        } else {
            switch (this.p2col) {
                case 1: {
                    this.p2Choice = 6;
                    return this.chunli;
                }
                case 2: {
                    this.p2Choice = 7;
                    return this.ken;
                }
                case 3: {
                    this.p2Choice = 8;
                    return this.blanka;
                }
                case 5: {
                    this.p2Choice = 10;
                    return this.sharon;
                }
            }
        }
        return null;
    }

    void randomize(int player) {
        if (player == 1) {
            double random = Math.random();
            if (random < 0.5) {
                this.p1row = 1;
                this.p1col = 2;
            } else {
                this.p1row = 2;
                this.p1col = 4;
            }
        } else {
            double random = Math.random();
            if (random < 0.5) {
                this.p2row = 1;
                this.p2col = 2;
            } else {
                this.p2row = 2;
                this.p2col = 4;
            }
        }
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        Image bufferImage = this.createImage(this.getSize().width, this.getSize().height);
        Graphics2D bufferGraphics = (Graphics2D)bufferImage.getGraphics();
        bufferGraphics.drawImage(this.charSelect, 0, 20, this.charSelectWidth / 2, this.charSelectHeight / 2, null);
        bufferGraphics.drawImage(this.selectTitle, 280, 50, this.titleWidth, this.titleHeight, null);
        Image p1Char = this.charChosen(1);
        if (this.charChosen(1) == this.random) {
            bufferGraphics.drawImage(p1Char, 7, 557 - this.charHeight, this.charWidth, this.charHeight, null);
        } else {
            bufferGraphics.drawImage(p1Char, 303, 557 - this.charHeight, - this.charWidth, this.charHeight, null);
        }
        Image p2Char = this.charChosen(2);
        bufferGraphics.drawImage(p2Char, 657, 557 - this.charHeight, this.charWidth, this.charHeight, null);
        int k = 0;
        while (k < 6) {
            bufferGraphics.drawImage(this.iconArray[k], 300 + 63 * k, 220, this.iconWidth / 2, this.iconHeight / 2, this);
            ++k;
        }
        k = 6;
        while (k < 12) {
            bufferGraphics.drawImage(this.iconArray[k], 300 + 63 * (k - 6), 320, this.iconWidth / 2, this.iconHeight / 2, this);
            ++k;
        }
        if (this.p1row == 1) {
            bufferGraphics.drawImage(this.p1Select, 282 + 63 * (this.p1col - 1), 210, null);
        } else {
            bufferGraphics.drawImage(this.p1Select, 282 + 63 * (this.p1col - 1), 310, null);
        }
        if (this.p2row == 1) {
            bufferGraphics.drawImage(this.p2Select, 282 + 63 * (this.p2col - 1), 210, null);
        } else {
            bufferGraphics.drawImage(this.p2Select, 282 + 63 * (this.p2col - 1), 310, null);
        }
        GradientPaint p = new GradientPaint(0.0f, 0.0f, Color.lightGray, 350.0f, 100.0f, Color.darkGray, true);
        bufferGraphics.setPaint(p);
        Rectangle rect = new Rectangle(305, 415, 350, 100);
        bufferGraphics.fill(rect);
        if (this.finished) {
            bufferGraphics.setFont(new Font("SansSerif", 1, 21));
            bufferGraphics.setColor(Color.white);
            bufferGraphics.drawString("Press SPACE to continue...", 340, 470);
        } else {
            bufferGraphics.setFont(new Font("SansSerif", 1, 18));
            bufferGraphics.setColor(Color.red.brighter());
            bufferGraphics.drawString("Player 1 Controls:", 310, 440);
            bufferGraphics.setColor(Color.blue.brighter());
            bufferGraphics.drawString("Player 2 Controls:", 490, 440);
            bufferGraphics.setFont(new Font("SansSerif", 1, 16));
            bufferGraphics.drawString("P, L, ;, ' - Select", 490, 480);
            bufferGraphics.drawString("O - Confirm Selection", 490, 500);
            bufferGraphics.setColor(Color.red.brighter());
            bufferGraphics.drawString("W, A, S, D - Select", 310, 480);
            bufferGraphics.drawString("Q - Confirm Selection", 310, 500);
        }
        bufferGraphics.setFont(this.razer.deriveFont(1, 20.0f));
        bufferGraphics.setColor(Color.yellow.brighter());
        if (this.p1Choice != 1 && this.p1Choice != 9 && this.p1Choice != 11) {
            bufferGraphics.drawString("Unlock at 1000 Wins", 20, 200);
        }
        if (this.p2Choice != 1 && this.p2Choice != 9 && this.p2Choice != 11) {
            bufferGraphics.drawString("Unlock at 1000 Wins", 700, 200);
        }
        bufferGraphics.setFont(new Font("SansSerif", 1, 40));
        int strWidth1 = (int)bufferGraphics.getFontMetrics().getStringBounds(this.charName[this.p1Choice], bufferGraphics).getWidth();
        bufferGraphics.drawString(this.charName[this.p1Choice], 140 - strWidth1 / 2, 150);
        int strWidth2 = (int)bufferGraphics.getFontMetrics().getStringBounds(this.charName[this.p2Choice], bufferGraphics).getWidth();
        bufferGraphics.drawString(this.charName[this.p2Choice], this.getWidth() - 140 - strWidth2 / 2, 150);
        g.drawImage(bufferImage, 0, 0, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.repaint();
        if (this.p1locked && this.p2locked) {
            if (this.p1Choice == 11) {
                this.randomize(1);
            }
            if (this.p2Choice == 11) {
                this.randomize(2);
            }
            this.repaint();
            this.finished = true;
        }
        if (this.start) {
            this.dispose();
        }
    }
}

class keys extends KeyAdapter {
    final ChampSelect this$0;

    keys(ChampSelect champSelect) {
        this.this$0 = champSelect;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (this.this$0.finished && key == 32) {
            this.this$0.start = true;
            try {
                Sound.fxPlay("/Audio/startmenuFX.wav");
            }
            catch (IOException | LineUnavailableException | UnsupportedAudioFileException var3_3) {
                // empty catch block
            }
        }
        if (!this.this$0.p1locked) {
            if (key == 81) {
                if (this.this$0.p1Choice == 1 || this.this$0.p1Choice == 9 || this.this$0.p1Choice == 11) {
                    this.this$0.p1locked = true;
                    try {
                        Sound.fxPlay("/Audio/startmenuFX.wav");
                    }
                    catch (IOException | LineUnavailableException | UnsupportedAudioFileException var3_4) {}
                } else {
                    try {
                        Sound.fxPlay("/Audio/lockedFX.wav");
                    }
                    catch (IOException | LineUnavailableException | UnsupportedAudioFileException var3_5) {
                        // empty catch block
                    }
                }
            }
            if (key == 83) {
                this.this$0.p1row = 2;
                try {
                    Sound.fxPlay("/Audio/selectFX.wav");
                }
                catch (IOException | LineUnavailableException | UnsupportedAudioFileException var3_6) {}
            } else if (key == 87) {
                this.this$0.p1row = 1;
                try {
                    Sound.fxPlay("/Audio/selectFX.wav");
                }
                catch (IOException | LineUnavailableException | UnsupportedAudioFileException var3_7) {
                    // empty catch block
                }
            }
            if (key == 68) {
                try {
                    Sound.fxPlay("/Audio/selectFX.wav");
                }
                catch (IOException | LineUnavailableException | UnsupportedAudioFileException var3_8) {
                    // empty catch block
                }
                this.this$0.p1col = this.this$0.p1col == 6 ? 1 : ++this.this$0.p1col;
            } else if (key == 65) {
                try {
                    Sound.fxPlay("/Audio/selectFX.wav");
                }
                catch (IOException | LineUnavailableException | UnsupportedAudioFileException var3_9) {
                    // empty catch block
                }
                this.this$0.p1col = this.this$0.p1col == 1 ? 6 : --this.this$0.p1col;
            }
        }
        if (!this.this$0.p2locked) {
            if (key == 79) {
                if (this.this$0.p2Choice == 1 || this.this$0.p2Choice == 9 || this.this$0.p2Choice == 11) {
                    this.this$0.p2locked = true;
                    try {
                        Sound.fxPlay("/Audio/startmenuFX.wav");
                    }
                    catch (IOException | LineUnavailableException | UnsupportedAudioFileException var3_10) {}
                } else {
                    try {
                        Sound.fxPlay("/Audio/lockedFX.wav");
                    }
                    catch (IOException | LineUnavailableException | UnsupportedAudioFileException var3_11) {
                        // empty catch block
                    }
                }
            }
            if (key == 59) {
                this.this$0.p2row = 2;
                try {
                    Sound.fxPlay("/Audio/selectFX.wav");
                }
                catch (IOException | LineUnavailableException | UnsupportedAudioFileException var3_12) {}
            } else if (key == 80) {
                this.this$0.p2row = 1;
                try {
                    Sound.fxPlay("/Audio/selectFX.wav");
                }
                catch (IOException | LineUnavailableException | UnsupportedAudioFileException var3_13) {
                    // empty catch block
                }
            }
            if (key == 222) {
                try {
                    Sound.fxPlay("/Audio/selectFX.wav");
                }
                catch (IOException | LineUnavailableException | UnsupportedAudioFileException var3_14) {
                    // empty catch block
                }
                this.this$0.p2col = this.this$0.p2col == 6 ? 1 : ++this.this$0.p2col;
            } else if (key == 76) {
                try {
                    Sound.fxPlay("/Audio/selectFX.wav");
                }
                catch (IOException | LineUnavailableException | UnsupportedAudioFileException var3_15) {
                    // empty catch block
                }
                this.this$0.p2col = this.this$0.p2col == 1 ? 6 : --this.this$0.p2col;
            }
        }
    }
}