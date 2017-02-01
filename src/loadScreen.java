import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

public class loadScreen
extends JFrame {
    BufferedImage[] loadingRyu = new BufferedImage[5];
    BufferedImage[] loadingBall = new BufferedImage[8];
    int[] imgHeights = new int[]{90, 85, 80, 77, 79};
    int counter = 0;
    int counter1 = 0;
    int delay;
    long lastFrameTime = 0;
    long timeStart = 0;
    int width = 75;
    int height = 37;
    boolean done = false;
    boolean hadoukenFX = false;

    loadScreen(int delay) {
        InputStream is;
        BufferedImage sheet;
        this.delay = delay;
        this.setVisible(true);
        this.setSize(360, 130);
        this.setDefaultCloseOperation(3);
        this.setTitle("Street Fighter :: Loading . . .");
        this.setFocusable(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setBackground(Color.black);
        try {
            is = this.getClass().getClassLoader().getResource("Ryu.gif").openStream();
        }
        catch (IOException e1) {
            return;
        }
        try {
            sheet = ImageIO.read(is);
        }
        catch (IOException e2) {
            return;
        }
        this.loadingRyu[0] = sheet.getSubimage(51, 1597, 74, 90);
        this.loadingRyu[1] = sheet.getSubimage(139, 1602, 91, 85);
        this.loadingRyu[2] = sheet.getSubimage(240, 1607, 96, 80);
        this.loadingRyu[3] = sheet.getSubimage(344, 1611, 114, 77);
        this.loadingRyu[4] = sheet.getSubimage(470, 1609, 114, 79);
        this.loadingBall[0] = sheet.getSubimage(5, 3146 - this.height, this.width, this.height);
        this.loadingBall[1] = sheet.getSubimage(82, 3146 - this.height, this.width, this.height);
        this.loadingBall[2] = sheet.getSubimage(156, 3146 - this.height, this.width, this.height);
        this.loadingBall[3] = sheet.getSubimage(231, 3146 - this.height, this.width, this.height);
        this.loadingBall[4] = sheet.getSubimage(314, 3146 - this.height, this.width, this.height);
        this.loadingBall[5] = sheet.getSubimage(389, 3146 - this.height, this.width, this.height);
        this.loadingBall[6] = sheet.getSubimage(467, 3146 - this.height, this.width, this.height);
        this.loadingBall[7] = sheet.getSubimage(542, 3146 - this.height, this.width, this.height);
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e2) {
            // empty catch block
        }
    }

    @Override
    public void paint(Graphics g) {
        Image bufferImage = this.createImage(this.getSize().width, this.getSize().height);
        Graphics bufferGraphics = bufferImage.getGraphics();
        bufferGraphics.drawImage(this.loadingRyu[this.counter], 10, 120 - this.imgHeights[this.counter], null);
        if (!this.hadoukenFX) {
            this.hadoukenFX = true;
            try {
                Sound.fxPlay((String)"Audio/hadouken.wav");
            }
            catch (IOException | LineUnavailableException | UnsupportedAudioFileException var4_4) {
                // empty catch block
            }
        }
        if (this.counter < this.loadingRyu.length - 1) {
            if (System.currentTimeMillis() - this.lastFrameTime > 130) {
                ++this.counter;
                this.lastFrameTime = System.currentTimeMillis();
            }
        } else {
            int xcoord;
            if (this.timeStart == 0) {
                this.timeStart = System.currentTimeMillis();
            }
            if ((xcoord = 120 + (int)(System.currentTimeMillis() - this.timeStart) / this.delay) + this.width >= this.getWidth()) {
                this.dispose();
                this.done = true;
            }
            bufferGraphics.drawImage(this.loadingBall[this.counter1], xcoord, 50, null);
            if (System.currentTimeMillis() - this.lastFrameTime > 30) {
                this.counter1 = this.counter1 == this.loadingBall.length - 1 ? 0 : ++this.counter1;
                this.lastFrameTime = System.currentTimeMillis();
            }
        }
        g.drawImage(bufferImage, 0, 0, this);
        this.repaint();
    }
}