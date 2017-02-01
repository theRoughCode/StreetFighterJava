import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ActionPanel
extends JPanel
implements ActionListener {
    Character user;
    Character user2;
    Image bg;
    Timer time;
    long gameStartTime;
    long gameTimeDiff;
    int gameTimeMins = 10;
    int gameTimeSec = 0;
    int changeX1 = 0;
    int changeX2 = 0;
    int frameWidth1 = 0;
    int frameWidth2 = 0;
    int frameHeight1 = 0;
    int frameHeight2 = 0;
    int bgWidth;
    int bgHeight;
    int endOption = 0;
    boolean gamePause = false;
    boolean gameEnd = false;
    boolean winAudio = false;
    Font razer;
    Rectangle atkRect1 = new Rectangle();
    Rectangle atkRect2 = new Rectangle();
    Rectangle testRect = new Rectangle();
    boolean startAniDone = false;
    int frame1 = 0;
    int frame2 = 0;
    int colour = 0;
    boolean pause = false;
    boolean reset = false;
    long gameInitTime;
    long fightStartTime = 0;
    long endTime = 0;
    long colourTime = 0;
    long lastFrameTime1 = 0;
    long lastFrameTime2 = 0;
    boolean collided = false;

    ActionPanel(int choice1, int choice2, int width, int height) throws IOException {
        if (choice1 == 1) {
            this.user = new Character("ryu");
        } else if (choice1 == 9) {
            this.user = new Character("dhalsim");
        }
        if (choice2 == 1) {
            this.user2 = new Character("ryu");
        } else if (choice2 == 9) {
            this.user2 = new Character("dhalsim");
        }
        this.bgWidth = width;
        this.bgHeight = height;
        this.user.move.maxX = this.bgWidth;
        this.user2.move.maxX = this.bgWidth;
        this.user2.move.x = this.user2.move.maxX - 90 - this.user2.move.x;
        this.user2.move.dxstore = -1;
        this.user2.move.userDir = -1;
        this.user2.userTiming = 1;
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("RAZEROBLIQUE.ttf");
        try {
            this.razer = Font.createFont(0, is);
        }
        catch (FontFormatException | IOException e) {
            System.out.println("cannot read");
            return;
        }
        this.addKeyListener((KeyListener)new AL(this));
        this.setFocusable(true);
        this.time = new Timer(5, this);
        this.gameStartTime = System.currentTimeMillis();
    }

    public void startGame() {
        this.time.start();
        this.gameInitTime = System.currentTimeMillis();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(this.bg, 0, 50, this.bgWidth, this.bgHeight, null);
        g2d.fillRect(0, 0, this.bgWidth, 50);
        g2d.setColor(new Color(16777164));
        g2d.fill3DRect(2, 2, this.bgWidth - 4, 45, true);
        g2d.setColor(Color.red.brighter());
        g2d.drawString("Player 1", 63, 14);
        g2d.setColor(Color.blue.brighter());
        g2d.drawString("Player 2", 463, 14);
        g2d.setColor(Color.black);
        g2d.fillRect(43, 19, 104, 14);
        g2d.setColor(new Color(6684672));
        g2d.fillRect(45, 21, 100, 10);
        int hpBar = this.user.hp * 100 / this.user.avatar.maxhp;
        if (hpBar > 50) {
            g2d.setColor(new Color(6750003));
        } else if (hpBar <= 50 && hpBar > 20) {
            g2d.setColor(new Color(16750899));
        } else if (hpBar <= 20 && hpBar > 0) {
            g2d.setColor(new Color(16711680));
        }
        g2d.fillRect(45, 21, hpBar, 10);
        g2d.setColor(Color.black);
        g2d.drawString("HP: ", 20, 30);
        g2d.setColor(Color.black);
        g2d.fillRect(443, 19, 104, 14);
        g2d.setColor(new Color(6684672));
        g2d.fillRect(445, 21, 100, 10);
        int hpBar2 = this.user2.hp * 100 / this.user2.avatar.maxhp;
        if (hpBar2 > 50) {
            g2d.setColor(new Color(6750003));
        } else if (hpBar2 <= 50 && hpBar2 > 20) {
            g2d.setColor(new Color(16750899));
        } else if (hpBar2 <= 20 && hpBar2 > 0) {
            g2d.setColor(new Color(16711680));
        }
        g2d.fillRect(445, 21, hpBar2, 10);
        g2d.setColor(Color.black);
        g2d.drawString("HP: ", 420, 30);
        if (!this.pause) {
            g2d.drawImage(this.user.avatar.startAni[0], this.user.getX() + this.changeX1 + this.user.avatar.AniWidth / 2 - this.user.move.userDir * this.user.avatar.AniWidth / 2, this.user.avatar.startAniY - this.user.avatar.AniHeight, this.user.move.userDir * this.user.avatar.AniWidth, this.user.avatar.AniHeight, this);
            g2d.drawImage(this.user2.avatar.startAni[0], this.user2.getX() + this.changeX2 + this.user2.avatar.AniWidth / 2 - this.user2.move.userDir * this.user2.avatar.AniWidth / 2, this.user2.avatar.startAniY - this.user2.avatar.AniHeight, this.user2.move.userDir * this.user2.avatar.AniWidth, this.user2.avatar.AniHeight, this);
            if (System.currentTimeMillis() - this.gameInitTime > 500) {
                this.pause = true;
            }
        } else if (!this.startAniDone) {
            if (this.frame1 < this.user.avatar.startAni.length - 2 || this.frame2 < this.user2.avatar.startAni.length - 2) {
                if (this.frame1 < this.user.avatar.startAni.length - 1) {
                    g2d.drawImage(this.animateArray(this.user.avatar.startAni, this.user.delay, 1), this.user.getX() + this.changeX1 + this.user.avatar.AniWidth / 2 - this.user.move.userDir * this.user.avatar.AniWidth / 2, this.user.avatar.startAniY - this.user.avatar.AniHeight, this.user.move.userDir * this.user.avatar.AniWidth, this.user.avatar.AniHeight, this);
                } else {
                    g2d.drawImage(this.user.avatar.startAni[this.user.avatar.startAni.length - 1], this.user.getX() + this.changeX1 + this.user.avatar.AniWidth / 2 - this.user.move.userDir * this.user.avatar.AniWidth / 2, this.user.avatar.startAniY - this.user.avatar.AniHeight, this.user.move.userDir * this.user.avatar.AniWidth, this.user.avatar.AniHeight, this);
                }
                if (this.frame2 < this.user2.avatar.startAni.length - 1) {
                    g2d.drawImage(this.animateArray(this.user2.avatar.startAni, this.user2.delay, 2), this.user2.getX() + this.changeX2 + this.user2.avatar.AniWidth / 2 - this.user2.move.userDir * this.user2.avatar.AniWidth / 2, this.user2.avatar.startAniY - this.user2.avatar.AniHeight, this.user2.move.userDir * this.user2.avatar.AniWidth, this.user2.avatar.AniHeight, this);
                } else {
                    g2d.drawImage(this.user2.avatar.startAni[this.user2.avatar.startAni.length - 1], this.user2.getX() + this.changeX2 + this.user2.avatar.AniWidth / 2 - this.user2.move.userDir * this.user2.avatar.AniWidth / 2, this.user2.avatar.startAniY - this.user2.avatar.AniHeight, this.user2.move.userDir * this.user2.avatar.AniWidth, this.user2.avatar.AniHeight, this);
                }
            } else {
                g2d.setFont(this.razer.deriveFont(1, 73.0f));
                g2d.setColor(Color.black.brighter());
                g2d.drawString("Fight", this.getWidth() / 2 - (int)g2d.getFontMetrics().getStringBounds("Fight", g2d).getWidth() / 2, this.getHeight() / 2);
                g2d.setFont(this.razer.deriveFont(1, 70.0f));
                g2d.setColor(Color.GREEN.brighter());
                g2d.drawString("Fight", this.getWidth() / 2 - (int)g2d.getFontMetrics().getStringBounds("Fight", g2d).getWidth() / 2, this.getHeight() / 2);
                if (this.fightStartTime == 0) {
                    this.fightStartTime = System.currentTimeMillis();
                    try {
                        Sound.fxPlay((String)"/Audio/fightFX.wav");
                    }
                    catch (IOException | LineUnavailableException | UnsupportedAudioFileException var5_5) {}
                } else if (System.currentTimeMillis() - this.fightStartTime > 800) {
                    this.startAniDone = true;
                    this.frame1 = 0;
                    this.frame2 = 0;
                }
                g2d.drawImage(this.user.avatar.startAni[this.user.avatar.startAni.length - 1], this.user.getX() + this.changeX1 + this.user.avatar.AniWidth / 2 - this.user.move.userDir * this.user.avatar.AniWidth / 2, this.user.avatar.startAniY - this.user.avatar.AniHeight, this.user.move.userDir * this.user.avatar.AniWidth, this.user.avatar.AniHeight, this);
                g2d.drawImage(this.user2.avatar.startAni[this.user2.avatar.startAni.length - 1], this.user2.getX() + this.changeX2 + this.user2.avatar.AniWidth / 2 - this.user2.move.userDir * this.user2.avatar.AniWidth / 2, this.user2.avatar.startAniY - this.user2.avatar.AniHeight, this.user2.move.userDir * this.user2.avatar.AniWidth, this.user2.avatar.AniHeight, this);
                try {
                    Thread.sleep(300);
                }
                catch (Exception var5_6) {
                    // empty catch block
                }
                this.gameStartTime = System.currentTimeMillis();
            }
        } else {
            this.gameTimeDiff = System.currentTimeMillis() - this.gameStartTime;
            long gameTimeLeft = 600000 - this.gameTimeDiff;
            g2d.drawString(String.format("%02d : %02d", gameTimeLeft / 60000, gameTimeLeft % 60000 / 1000), 300, 30);
            this.collisionCheck();
            g2d.drawImage(this.animateArray(this.user.getCharImageArray(), this.user.delay, 1), this.user.getX() + this.changeX1 + this.user.avatar.AniWidth / 2 - this.user.move.userDir * this.user.avatar.AniWidth / 2, this.user.getY() - this.frameHeight1, this.user.move.userDir * this.frameWidth1, this.frameHeight1, this);
            g2d.drawImage(this.animateArray(this.user2.getCharImageArray(), this.user2.delay, 2), this.user2.getX() + this.changeX2 + this.user2.avatar.AniWidth / 2 - this.user2.move.userDir * this.user2.avatar.AniWidth / 2, this.user2.getY() - this.frameHeight2, this.user2.move.userDir * this.frameWidth2, this.frameHeight2, this);
            g2d.setColor(Color.RED);
            g2d.drawString("Player 1", this.user.move.x + 10, this.user.getY() - this.frameHeight1 - 10);
            g2d.setColor(Color.BLUE);
            g2d.drawString("Player 2", this.user2.move.x + 10, this.user2.getY() - this.frameHeight2 - 10);
            if (this.gameEnd) {
                g2d.setFont(this.razer.deriveFont(1, 35.0f));
                g2d.setColor(Color.GREEN);
                int playerWin = this.user.dead ? 1 : 2;
                if (!this.winAudio) {
                    this.playWinAudio(playerWin);
                }
                g2d.drawString("Player " + playerWin + " Wins!", this.getWidth() / 2 - (int)g2d.getFontMetrics().getStringBounds("Player " + playerWin + " Wins!", g2d).getWidth() / 2, this.getHeight() / 2);
                if (this.endTime == 0) {
                    this.endTime = System.currentTimeMillis();
                }
                if (this.reset) {
                    g2d.setFont(new Font("Serif", 1, 15));
                    if (System.currentTimeMillis() - this.colourTime > 100) {
                        this.colour = this.colour == 4 ? 1 : ++this.colour;
                        this.colourTime = System.currentTimeMillis();
                    }
                    if (this.colour == 1) {
                        g2d.setColor(Color.yellow.brighter());
                    } else if (this.colour == 2) {
                        g2d.setColor(Color.orange.brighter());
                    } else if (this.colour == 3) {
                        g2d.setColor(Color.white.brighter());
                    } else {
                        g2d.setColor(Color.black.brighter());
                    }
                    g2d.drawString("Press SPACE to continue", this.getWidth() / 2 - (int)g2d.getFontMetrics().getStringBounds("Press SPACE to continue", g2d).getWidth() / 2, this.getHeight() - 50);
                }
            } else if (this.gamePause) {
                g2d.setFont(this.razer.deriveFont(1, 30.0f));
                g2d.setColor(Color.yellow.brighter());
                g2d.drawString("Paused", this.getWidth() / 2 - (int)g2d.getFontMetrics().getStringBounds("Paused", g2d).getWidth() / 2, this.getHeight() / 2);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.gameEnd) {
            if (System.currentTimeMillis() - this.endTime > 1000) {
                this.reset = true;
            }
        } else {
            this.user.setHitBox();
            this.user2.setHitBox();
            this.collisionCheck();
            this.checkDirection();
            this.user.move.move();
            this.user2.move.move();
            this.user2.dead = this.user.checkDeath(2);
            this.user.dead = this.user2.checkDeath(1);
            if (this.user2.dead || this.user.dead) {
                this.user.block = true;
                this.user2.block = true;
                this.gameEnd = true;
            }
        }
        this.repaint();
    }

    BufferedImage animateArray(BufferedImage[] aniArray, int delay, int userNo) {
        int frames = aniArray.length - 1;
        if (userNo == 1) {
            if (this.user.newImage) {
                this.user2.victim = false;
                this.frame1 = 0;
                this.user.move.attack.attack();
            }
            if (System.currentTimeMillis() - this.lastFrameTime1 > 50) {
                this.moveBack(1);
            }
            if (aniArray.equals(this.user.avatar.jumpr)) {
                this.jumpMovement(frames, 1, this.user.move.userDir);
            } else if (aniArray.equals(this.user.avatar.jumpl)) {
                this.jumpMovement(frames, 1, - this.user.move.userDir);
            }
            if (System.currentTimeMillis() - this.lastFrameTime1 > (long)delay) {
                this.lastFrameTime1 = System.currentTimeMillis();
                if (this.frame1 < frames) {
                    if (!aniArray.equals(this.user.avatar.jumpl) && !aniArray.equals(this.user.avatar.jumpr)) {
                        ++this.frame1;
                    }
                    this.attackDamage(1);
                    if (this.user.avatar instanceof Ryu) {
                        if (aniArray.equals(this.user.avatar.LK)) {
                            this.changeX1 = this.frame1 < 4 ? this.user.move.userDir * 15 : 0;
                        } else if (aniArray.equals(this.user.avatar.MK)) {
                            this.changeX1 = this.frame1 == 1 ? (- this.user.move.userDir) * 20 : (this.frame1 == 2 ? (- this.user.move.userDir) * 30 : 0);
                        }
                    }
                } else {
                    this.changeX1 = 0;
                    if (aniArray.equals(this.user.avatar.win1)) {
                        this.user.win1done = true;
                    } else if (aniArray.equals(this.user.avatar.jump)) {
                        if (this.user.move.y >= this.user.move.minJumpHeight) {
                            this.user.move.jumpDone = false;
                            this.user.move.jumpMax = false;
                            this.user.move.y = this.user.move.minJumpHeight;
                        } else {
                            this.user.move.moveDown();
                        }
                    } else if (!aniArray.equals(this.user.avatar.crouch)) {
                        if (aniArray.equals(this.user.avatar.jumpr)) {
                            this.jumpMovement(frames, 1, this.user.move.userDir);
                        } else if (aniArray.equals(this.user.avatar.jumpl)) {
                            this.jumpMovement(frames, 1, - this.user.move.userDir);
                        } else if (aniArray.equals(this.user.avatar.LP)) {
                            this.user.move.attack.LP = false;
                        } else if (aniArray.equals(this.user.avatar.MP)) {
                            this.user.move.attack.MP = false;
                        } else if (aniArray.equals(this.user.avatar.HP)) {
                            this.user.move.attack.HP = false;
                        } else if (aniArray.equals(this.user.avatar.LK)) {
                            this.user.move.attack.LK = false;
                        } else if (aniArray.equals(this.user.avatar.MK)) {
                            this.user.move.attack.MK = false;
                        } else if (aniArray.equals(this.user.avatar.HK)) {
                            this.user.move.attack.HK = false;
                        } else if (aniArray.equals(this.user.avatar.block)) {
                            if (!this.user.hit) {
                                this.user.block = false;
                            }
                        } else {
                            this.frame1 = 0;
                        }
                    }
                }
            }
            if (aniArray.equals(this.user.avatar.jump) && this.user.move.jumpDone) {
                if (this.user.move.y > this.user.move.maxJumpHeight && !this.user.move.jumpMax) {
                    this.user.move.moveUp();
                    if (this.frame1 < 3) {
                        ++this.frame1;
                    }
                } else {
                    this.user.move.jumpMax = true;
                    this.user.move.moveDown();
                    if (this.frame1 < frames) {
                        ++this.frame1;
                    }
                }
            }
            this.frameWidth1 = aniArray[this.frame1].getWidth();
            this.frameHeight1 = aniArray[this.frame1].getHeight();
            try {
                return aniArray[this.frame1];
            }
            catch (Exception e) {
                return null;
            }
        }
        if (userNo == 2) {
            if (this.user2.newImage) {
                this.user.victim = false;
                this.frame2 = 0;
                this.user2.move.attack.attack();
            }
            if (System.currentTimeMillis() - this.lastFrameTime2 > 50) {
                this.moveBack(2);
            }
            if (aniArray.equals(this.user2.avatar.jumpr)) {
                this.jumpMovement(frames, 2, this.user2.move.userDir);
            } else if (aniArray.equals(this.user2.avatar.jumpl)) {
                this.jumpMovement(frames, 2, - this.user2.move.userDir);
            }
            if (System.currentTimeMillis() - this.lastFrameTime2 > (long)delay) {
                this.lastFrameTime2 = System.currentTimeMillis();
                if (this.frame2 < frames) {
                    if (!aniArray.equals(this.user2.avatar.jumpl) && !aniArray.equals(this.user2.avatar.jumpr)) {
                        ++this.frame2;
                    }
                    this.attackDamage(2);
                    if (aniArray.equals(this.user2.avatar.LK)) {
                        this.changeX2 = this.frame2 < 4 ? 15 : 0;
                    } else if (aniArray.equals(this.user2.avatar.MK)) {
                        this.changeX2 = this.frame2 == 1 ? (- this.user2.move.userDir) * 20 : (this.frame2 == 2 ? (- this.user2.move.userDir) * 30 : 0);
                    }
                } else {
                    this.changeX2 = 0;
                    if (aniArray.equals(this.user2.avatar.win1)) {
                        this.user2.win1done = true;
                    } else if (aniArray.equals(this.user2.avatar.jump)) {
                        if (this.user2.move.y >= this.user2.move.minJumpHeight) {
                            this.user2.move.jumpDone = false;
                            this.user2.move.jumpMax = false;
                            this.user2.move.y = this.user2.move.minJumpHeight;
                        } else {
                            this.user2.move.moveDown();
                        }
                    } else if (!aniArray.equals(this.user2.avatar.crouch)) {
                        if (aniArray.equals(this.user2.avatar.jumpr)) {
                            this.jumpMovement(frames, 2, this.user2.move.userDir);
                        } else if (aniArray.equals(this.user2.avatar.jumpl)) {
                            this.jumpMovement(frames, 2, - this.user2.move.userDir);
                        } else if (aniArray.equals(this.user2.avatar.LP)) {
                            this.user2.move.attack.LP = false;
                        } else if (aniArray.equals(this.user2.avatar.MP)) {
                            this.user2.move.attack.MP = false;
                        } else if (aniArray.equals(this.user2.avatar.HP)) {
                            this.user2.move.attack.HP = false;
                        } else if (aniArray.equals(this.user2.avatar.LK)) {
                            this.user2.move.attack.LK = false;
                        } else if (aniArray.equals(this.user2.avatar.MK)) {
                            this.user2.move.attack.MK = false;
                        } else if (aniArray.equals(this.user2.avatar.HK)) {
                            this.user2.move.attack.HK = false;
                        } else if (aniArray.equals(this.user2.avatar.block)) {
                            if (!this.user2.hit) {
                                this.user2.block = false;
                            }
                        } else {
                            this.frame2 = 0;
                        }
                    }
                }
            }
            if (aniArray.equals(this.user2.avatar.jump) && this.user2.move.jumpDone) {
                if (this.user2.move.y > this.user2.move.maxJumpHeight && !this.user2.move.jumpMax) {
                    this.user2.move.moveUp();
                    if (this.frame2 < 3) {
                        ++this.frame2;
                    }
                } else {
                    this.user2.move.jumpMax = true;
                    this.user2.move.moveDown();
                    if (this.frame2 < frames) {
                        ++this.frame2;
                    }
                }
            }
            this.frameWidth2 = aniArray[this.frame2].getWidth();
            this.frameHeight2 = aniArray[this.frame2].getHeight();
            try {
                return aniArray[this.frame2];
            }
            catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    void jumpMovement(int totalFrames, int userNo, int dx) {
        if (userNo == 1) {
            if (!this.user.move.jumpMax) {
                if (this.user.move.y > this.user.move.maxJumpHeight) {
                    this.user.move.moveUp();
                } else {
                    this.user.move.jumpMax = true;
                }
                if (Math.abs(this.user.move.x - this.user.move.jumpStartPos) < 50) {
                    this.user.move.move(dx);
                }
            } else {
                if (Math.abs(this.user.move.x - this.user.move.jumpStartPos) < 140) {
                    this.user.move.move(dx);
                }
                if (this.frame1 < totalFrames - 1) {
                    if (System.currentTimeMillis() - this.user.move.jumpLastFrameTime > (long)this.user.delay) {
                        ++this.frame1;
                        this.user.move.jumpLastFrameTime = System.currentTimeMillis();
                    }
                } else if (this.user.move.y < this.user.move.minJumpHeight) {
                    this.frame1 = totalFrames;
                    this.user.move.dx = 0;
                    this.user.move.moveDown();
                } else {
                    this.user.move.y = this.user.move.minJumpHeight;
                    this.user.move.jumpR = false;
                    this.user.move.jumpL = false;
                    this.user.move.jumpMax = false;
                }
            }
        } else if (userNo == 2) {
            if (!this.user2.move.jumpMax) {
                if (this.user2.move.y > this.user2.move.maxJumpHeight) {
                    this.user2.move.moveUp();
                } else {
                    this.user2.move.jumpMax = true;
                }
                if (Math.abs(this.user2.move.x - this.user2.move.jumpStartPos) < 50) {
                    this.user2.move.move(dx);
                }
            } else {
                if (Math.abs(this.user2.move.x - this.user2.move.jumpStartPos) < 140) {
                    this.user2.move.move(dx);
                }
                if (this.frame2 < 6) {
                    if (System.currentTimeMillis() - this.user2.move.jumpLastFrameTime > (long)this.user2.delay) {
                        ++this.frame2;
                        this.user2.move.jumpLastFrameTime = System.currentTimeMillis();
                    }
                } else if (this.user2.move.y < this.user2.move.minJumpHeight) {
                    this.frame2 = 7;
                    this.user2.move.dx = 0;
                    this.user2.move.moveDown();
                } else {
                    this.user2.move.y = this.user2.move.minJumpHeight;
                    this.user2.move.jumpR = false;
                    this.user2.move.jumpL = false;
                    this.user2.move.jumpMax = false;
                }
            }
        }
    }

    void attackDamage(int userNo) {
        if (userNo == 1) {
            if (this.user.move.attack.LP) {
                this.user2.victim = true;
                if (this.frame1 == 1) {
                    if (this.user.move.userDir == 1) {
                        if (this.user.avatar instanceof Ryu) {
                            this.damage(2, new Rectangle(this.user.move.x + this.user.avatar.stanceWidth + 13, this.user.move.y - this.user.avatar.AniHeight + 20, 17, 15));
                        } else {
                            this.damage(2, new Rectangle(this.user.move.x + 90, this.user.move.y - 56, 17, 15));
                        }
                    } else if (this.user.avatar instanceof Ryu) {
                        this.damage(2, new Rectangle(this.user.move.x - 33, this.user.move.y - this.user2.avatar.AniHeight + 20, 17, 15));
                    } else {
                        this.damage(2, new Rectangle(this.user.move.x - 40, this.user.move.y - 53, 17, 15));
                    }
                }
            } else if (this.user.move.attack.MP) {
                this.user2.victim = true;
                if (this.frame1 == 2) {
                    if (this.user.move.userDir == 1) {
                        if (this.user.avatar instanceof Ryu) {
                            this.damage(2, new Rectangle(this.user.move.x + this.user.avatar.AniWidth + 18, this.user.move.y - this.user.avatar.AniHeight + 20, 17, 15));
                        } else {
                            this.damage(2, new Rectangle(this.user.move.x + 110, this.user.move.y - 65, 127, 15));
                        }
                    } else if (this.user.avatar instanceof Ryu) {
                        this.damage(2, new Rectangle(this.user.move.x - 38, this.user.move.y - this.user.avatar.AniHeight + 20, 17, 15));
                    } else {
                        this.damage(2, new Rectangle(this.user.move.x - 170, this.user.move.y - 65, 127, 15));
                    }
                }
            } else if (this.user.move.attack.HP) {
                this.user2.victim = true;
                if (this.frame1 == 1) {
                    if (this.user.move.userDir == 1) {
                        if (this.user.avatar instanceof Ryu) {
                            this.damage(2, new Rectangle(this.user.move.x + this.user.avatar.AniWidth + 13, this.user.move.y - this.user.avatar.AniHeight + 22, 17, 15));
                        } else {
                            this.damage(2, new Rectangle(this.user.move.x + 110, this.user.move.y - 57, 160, 15));
                        }
                    } else if (this.user.avatar instanceof Ryu) {
                        this.damage(2, new Rectangle(this.user.move.x - 33, this.user.move.y - this.user.avatar.AniHeight + 20, 17, 15));
                    } else {
                        this.damage(2, new Rectangle(this.user.move.x - 203, this.user.move.y - 57, 160, 15));
                    }
                }
            } else if (this.user.move.attack.LK) {
                this.user2.victim = true;
                if (this.frame1 == 1) {
                    if (this.user.move.userDir == 1) {
                        if (this.user.avatar instanceof Ryu) {
                            this.damage(2, new Rectangle(this.user.move.x + this.user.avatar.AniWidth + 28, this.user.move.y - 28, 17, 15));
                        } else {
                            this.damage(2, new Rectangle(this.user.move.x + 185, this.user.move.y - 27, 27, 15));
                        }
                    } else if (this.user.avatar instanceof Ryu) {
                        this.damage(2, new Rectangle(this.user.move.x - 48, this.user.move.y - 28, 17, 15));
                    } else {
                        this.damage(2, new Rectangle(this.user.move.x - 146, this.user.move.y - 27, 27, 15));
                    }
                }
            } else if (this.user.move.attack.MK) {
                this.user2.victim = true;
                if (this.frame1 == 2) {
                    if (this.user.move.userDir == 1) {
                        if (this.user.avatar instanceof Ryu) {
                            this.damage(2, new Rectangle(this.user.move.x + this.user.avatar.AniWidth, this.user.move.y - this.user.avatar.AniHeight + 15, 17, 15));
                        } else {
                            this.damage(2, new Rectangle(this.user.move.x + 200, this.user.move.y - 100, 35, 15));
                        }
                    } else if (this.user.avatar instanceof Ryu) {
                        this.damage(2, new Rectangle(this.user.move.x - 20, this.user.move.y - this.user.avatar.AniHeight + 15, 17, 15));
                    } else {
                        this.damage(2, new Rectangle(this.user.move.x - 165, this.user.move.y - 100, 35, 15));
                    }
                }
            } else if (this.user.move.attack.HK) {
                this.user2.victim = true;
                if (this.frame1 == 3) {
                    if (this.user.move.userDir == 1) {
                        if (this.user.avatar instanceof Ryu) {
                            this.damage(2, new Rectangle(this.user.move.x + this.user.avatar.AniWidth + 20, this.user.move.y - this.user.avatar.AniHeight + 7, 20, 18));
                        } else {
                            this.damage(2, new Rectangle(this.user.move.x + 190, this.user.move.y - 115, 35, 25));
                        }
                    } else if (this.user.avatar instanceof Ryu) {
                        this.damage(2, new Rectangle(this.user.move.x - 40, this.user.move.y - this.user.avatar.AniHeight + 7, 20, 18));
                    } else {
                        this.damage(2, new Rectangle(this.user.move.x - 155, this.user.move.y - 115, 35, 25));
                    }
                }
            }
        } else if (userNo == 2) {
            if (this.user2.move.attack.LP) {
                this.user.victim = true;
                if (this.frame2 == 1) {
                    if (this.user2.move.userDir == 1) {
                        if (this.user2.avatar instanceof Ryu) {
                            this.damage(1, new Rectangle(this.user2.move.x + this.user2.avatar.stanceWidth + 13, this.user2.move.y - this.user2.avatar.AniHeight + 20, 17, 15));
                        } else {
                            this.damage(1, new Rectangle(this.user2.move.x + 90, this.user2.move.y - 56, 17, 15));
                        }
                    } else if (this.user2.avatar instanceof Ryu) {
                        this.damage(1, new Rectangle(this.user2.move.x - 33, this.user2.move.y - this.user2.avatar.AniHeight + 20, 17, 15));
                    } else {
                        this.damage(1, new Rectangle(this.user2.move.x - 40, this.user2.move.y - 53, 17, 15));
                    }
                }
            } else if (this.user2.move.attack.MP) {
                this.user.victim = true;
                if (this.frame2 == 2) {
                    if (this.user2.move.userDir == 1) {
                        if (this.user2.avatar instanceof Ryu) {
                            this.damage(1, new Rectangle(this.user2.move.x + this.user2.avatar.AniWidth + 18, this.user2.move.y - this.user2.avatar.AniHeight + 20, 17, 15));
                        } else {
                            this.damage(1, new Rectangle(this.user2.move.x + 110, this.user2.move.y - 65, 127, 15));
                        }
                    } else if (this.user2.avatar instanceof Ryu) {
                        this.damage(1, new Rectangle(this.user2.move.x - 38, this.user2.move.y - this.user2.avatar.AniHeight + 20, 17, 15));
                    } else {
                        this.damage(1, new Rectangle(this.user2.move.x - 170, this.user2.move.y - 65, 127, 15));
                    }
                }
            } else if (this.user2.move.attack.HP) {
                this.user.victim = true;
                if (this.frame2 == 1) {
                    if (this.user2.move.userDir == 1) {
                        if (this.user2.avatar instanceof Ryu) {
                            this.damage(1, new Rectangle(this.user2.move.x + this.user2.avatar.AniWidth + 13, this.user2.move.y - this.user2.avatar.AniHeight + 22, 17, 15));
                        } else {
                            this.damage(1, new Rectangle(this.user2.move.x + 110, this.user2.move.y - 57, 160, 15));
                        }
                    } else if (this.user2.avatar instanceof Ryu) {
                        this.damage(1, new Rectangle(this.user2.move.x - 33, this.user2.move.y - this.user2.avatar.AniHeight + 20, 17, 15));
                    } else {
                        this.damage(1, new Rectangle(this.user2.move.x - 203, this.user2.move.y - 57, 160, 15));
                    }
                }
            } else if (this.user2.move.attack.LK) {
                this.user.victim = true;
                if (this.frame2 == 1) {
                    if (this.user2.move.userDir == 1) {
                        if (this.user2.avatar instanceof Ryu) {
                            this.damage(1, new Rectangle(this.user2.move.x + this.user2.avatar.AniWidth + 28, this.user2.move.y - 28, 17, 15));
                        } else {
                            this.damage(1, new Rectangle(this.user2.move.x + 185, this.user2.move.y - 27, 27, 15));
                        }
                    } else if (this.user2.avatar instanceof Ryu) {
                        this.damage(1, new Rectangle(this.user2.move.x - 48, this.user2.move.y - 28, 17, 15));
                    } else {
                        this.damage(1, new Rectangle(this.user2.move.x - 146, this.user2.move.y - 27, 27, 15));
                    }
                }
            } else if (this.user2.move.attack.MK) {
                this.user.victim = true;
                if (this.frame2 == 2) {
                    if (this.user2.move.userDir == 1) {
                        if (this.user2.avatar instanceof Ryu) {
                            this.damage(1, new Rectangle(this.user2.move.x + this.user2.avatar.AniWidth, this.user2.move.y - this.user2.avatar.AniHeight + 15, 17, 15));
                        } else {
                            this.damage(1, new Rectangle(this.user2.move.x + 200, this.user2.move.y - 100, 35, 15));
                        }
                    } else if (this.user2.avatar instanceof Ryu) {
                        this.damage(1, new Rectangle(this.user2.move.x - 20, this.user2.move.y - this.user2.avatar.AniHeight + 15, 17, 15));
                    } else {
                        this.damage(1, new Rectangle(this.user2.move.x - 165, this.user2.move.y - 100, 35, 15));
                    }
                }
            } else if (this.user2.move.attack.HK) {
                this.user.victim = true;
                if (this.frame2 == 3) {
                    if (this.user2.move.userDir == 1) {
                        if (this.user2.avatar instanceof Ryu) {
                            this.damage(1, new Rectangle(this.user2.move.x + this.user2.avatar.AniWidth + 20, this.user2.move.y - this.user2.avatar.AniHeight + 7, 20, 18));
                        } else {
                            this.damage(1, new Rectangle(this.user2.move.x + 190, this.user2.move.y - 115, 35, 25));
                        }
                    } else if (this.user2.avatar instanceof Ryu) {
                        this.damage(1, new Rectangle(this.user2.move.x - 40, this.user2.move.y - this.user2.avatar.AniHeight + 7, 20, 18));
                    } else {
                        this.damage(1, new Rectangle(this.user2.move.x - 155, this.user2.move.y - 115, 35, 25));
                    }
                }
            }
        }
    }

    void damage(int userNo, Rectangle atkBox) {
        if (userNo == 2) {
            this.user2.setHitBox();
            this.atkRect1 = atkBox;
            if (this.user2.arrowLeftPressed && !this.user2.arrowRightPressed) {
                this.user2.leftArrowPressed();
            } else if (this.user2.arrowRightPressed && !this.user2.arrowLeftPressed) {
                this.user2.rightArrowPressed();
            }
            if (this.user2.hitBox.intersects(atkBox)) {
                this.user2.hit = true;
                if (!this.user2.block) {
                    this.user2.hp -= this.user.move.attack.dmg;
                    if (this.user.move.attack.LP || this.user.move.attack.MP || this.user.move.attack.HP) {
                        try {
                            Sound.fxPlay((String)"/Audio/LP.wav");
                        }
                        catch (IOException | LineUnavailableException | UnsupportedAudioFileException var3_3) {}
                    } else if (this.user.move.attack.LK || this.user.move.attack.MK || this.user.move.attack.HK) {
                        try {
                            Sound.fxPlay((String)"/Audio/LK.wav");
                        }
                        catch (IOException | LineUnavailableException | UnsupportedAudioFileException var3_4) {
                            // empty catch block
                        }
                    }
                }
                if (this.user2.move.x <= 0 || this.user2.move.x >= this.user2.move.maxX - this.user2.move.userAniWidth) {
                    this.user.move.setLastPos();
                } else {
                    this.user2.move.setLastPos();
                }
            }
        } else if (userNo == 1) {
            this.user.setHitBox();
            this.atkRect2 = atkBox;
            if (this.user.arrowLeftPressed && !this.user.arrowRightPressed) {
                this.user.leftArrowPressed();
            } else if (this.user.arrowRightPressed && !this.user.arrowLeftPressed) {
                this.user.rightArrowPressed();
            }
            if (this.user.hitBox.intersects(atkBox)) {
                this.user.hit = true;
                if (!this.user.block) {
                    this.user.hp -= this.user2.move.attack.dmg;
                    if (this.user2.move.attack.LP || this.user2.move.attack.MP || this.user2.move.attack.HP) {
                        try {
                            Sound.fxPlay((String)"/Audio/LP.wav");
                        }
                        catch (IOException | LineUnavailableException | UnsupportedAudioFileException var3_5) {}
                    } else if (this.user2.move.attack.LK || this.user2.move.attack.MK || this.user2.move.attack.HK) {
                        try {
                            Sound.fxPlay((String)"/Audio/LK.wav");
                        }
                        catch (IOException | LineUnavailableException | UnsupportedAudioFileException var3_6) {
                            // empty catch block
                        }
                    }
                }
                if (this.user.move.x <= 0 || this.user.move.x >= this.user.move.maxX - this.user.move.userAniWidth) {
                    this.user2.move.setLastPos();
                } else {
                    this.user.move.setLastPos();
                }
            }
        }
    }

    void moveBack(int userNo) {
        if (userNo == 1) {
            if (this.user2.hit) {
                this.user2.hit = this.user2.move.x <= 0 || this.user2.move.x >= this.user2.move.maxX - this.user2.move.userAniWidth ? this.user.move.hitMove(this.user.move.dmgLvl, true) : this.user2.move.hitMove(this.user.move.dmgLvl, false);
            }
        } else if (userNo == 2 && this.user.hit) {
            this.user.hit = this.user.move.x <= 0 || this.user.move.x >= this.user.move.maxX - this.user.move.userAniWidth ? this.user2.move.hitMove(this.user2.move.dmgLvl, true) : this.user.move.hitMove(this.user2.move.dmgLvl, false);
        }
    }

    void checkDirection() {
        double user1Location = this.user.hitBox.getCenterX();
        double user2Location = this.user2.hitBox.getCenterX();
        if (!this.user.checkJumpFalse() && !this.user2.checkJumpFalse()) {
            if (user1Location < user2Location && this.user.move.userDir == -1) {
                this.user.move.userDir = 1;
                this.user2.move.userDir = -1;
            } else if (user1Location > user2Location && this.user.move.userDir == 1) {
                this.user.move.userDir = -1;
                this.user2.move.userDir = 1;
            }
            if (this.user.arrowLeftPressed) {
                this.user.leftArrowPressed();
            } else if (this.user.arrowRightPressed) {
                this.user.rightArrowPressed();
            }
            if (this.user2.arrowLeftPressed) {
                this.user2.leftArrowPressed();
            } else if (this.user2.arrowRightPressed) {
                this.user2.rightArrowPressed();
            }
        }
    }

    void collisionCheck() {
        this.user.checkEdges();
        this.user2.checkEdges();
        if (this.user.hitBox.intersects(this.user2.hitBox)) {
            if (this.user.move.userDir == 1) {
                int loc1 = (int)this.user.hitBox.getMaxX();
                int loc2 = (int)this.user2.hitBox.getMinX();
                int diff = (loc1 - loc2) / 2;
                if (loc1 > loc2 && !this.user.checkJumpFalse() && !this.user2.checkJumpFalse()) {
                    this.checkDirection();
                    this.user.move.ground();
                    this.user2.move.ground();
                    this.user.move.x -= diff;
                    this.user2.move.x += diff;
                    if (this.user.arrowLeftPressed) {
                        this.user.move.moveLeft();
                    }
                    if (this.user2.arrowRightPressed) {
                        this.user2.move.moveRight();
                    }
                } else {
                    this.user.move.stop = 1;
                    this.user2.move.stop = 1;
                }
            } else if (this.user.move.userDir == -1) {
                int loc1 = (int)this.user.hitBox.getMinX();
                int loc2 = (int)this.user2.hitBox.getMaxX();
                int diff = (loc2 - loc1) / 2;
                if (loc1 < loc2 && !this.user.checkJumpFalse() && !this.user2.checkJumpFalse()) {
                    this.checkDirection();
                    this.user.move.x += diff;
                    this.user2.move.x -= diff;
                    if (this.user.arrowRightPressed) {
                        this.user.move.moveRight();
                    }
                    if (this.user2.arrowLeftPressed) {
                        this.user2.move.moveLeft();
                    }
                } else {
                    this.user.move.stop = 1;
                    this.user2.move.stop = 1;
                }
            }
        } else {
            this.user.move.stop = 1;
            this.user2.move.stop = 1;
        }
    }

    void playWinAudio(int winner) {
        this.winAudio = true;
        if (winner == 1) {
            try {
                Sound.fxPlay((String)"/Audio/p1win.wav");
            }
            catch (IOException | LineUnavailableException | UnsupportedAudioFileException var2_2) {}
        } else {
            try {
                Sound.fxPlay((String)"/Audio/p2win.wav");
            }
            catch (IOException | LineUnavailableException | UnsupportedAudioFileException var2_3) {
                // empty catch block
            }
        }
    }

    void resume() {
        this.gamePause = false;
        this.time.start();
        Sound.resume();
    }
}

class AL extends KeyAdapter {
    final /* synthetic */ ActionPanel this$0;

    AL(ActionPanel actionPanel) {
        this.this$0 = actionPanel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (this.this$0.startAniDone) {
            int key = e.getKeyCode();
            if (this.this$0.reset) {
                if (key == 32) {
                    Sound.stopAudio();
                    this.this$0.endOption = 1;
                }
            } else {
                if (key == 65) {
                    this.this$0.user.leftArrowPressed();
                }
                if (key == 68) {
                    this.this$0.user.rightArrowPressed();
                }
                if (key == 87) {
                    this.this$0.user.upArrowPressed();
                }
                if (key == 83) {
                    this.this$0.user.downArrowPressed();
                }
                if (key == 67) {
                    this.this$0.user.lightPunchPressed();
                }
                if (key == 86) {
                    this.this$0.user.mediumPunchPressed();
                }
                if (key == 66) {
                    this.this$0.user.hardPunchPressed();
                }
                if (key == 70) {
                    this.this$0.user.lightKickPressed();
                }
                if (key == 71) {
                    this.this$0.user.mediumKickPressed();
                }
                if (key == 72) {
                    this.this$0.user.hardKickPressed();
                }
                if (key == 76) {
                    this.this$0.user2.leftArrowPressed();
                }
                if (key == 222) {
                    this.this$0.user2.rightArrowPressed();
                }
                if (key == 80) {
                    this.this$0.user2.upArrowPressed();
                }
                if (key == 59) {
                    this.this$0.user2.downArrowPressed();
                }
                if (key == 97) {
                    this.this$0.user2.lightPunchPressed();
                }
                if (key == 98) {
                    this.this$0.user2.mediumPunchPressed();
                }
                if (key == 99) {
                    this.this$0.user2.hardPunchPressed();
                }
                if (key == 100) {
                    this.this$0.user2.lightKickPressed();
                }
                if (key == 101) {
                    this.this$0.user2.mediumKickPressed();
                }
                if (key == 102) {
                    this.this$0.user2.hardKickPressed();
                }
                if (key == 27 && this.this$0.startAniDone && !this.this$0.gamePause) {
                    this.this$0.time.stop();
                    Sound.stopAudio();
                    try {
                        Sound.fxPlay("/Audio/pause.wav");
                    }
                    catch (IOException | LineUnavailableException | UnsupportedAudioFileException var3_3) {
                        // empty catch block
                    }
                    this.this$0.gamePause = true;
                    this.this$0.repaint();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (this.this$0.startAniDone) {
            int key = e.getKeyCode();
            if (key == 65) {
                this.this$0.user.leftArrowReleased();
            }
            if (key == 68) {
                this.this$0.user.rightArrowReleased();
            }
            if (key == 87) {
                this.this$0.user.upArrowReleased();
            }
            if (key == 83) {
                this.this$0.user.downArrowReleased();
            }
            if (key == 67) {
                this.this$0.user.lightPunchReleased();
            }
            if (key == 86) {
                this.this$0.user.mediumPunchReleased();
            }
            if (key == 66) {
                this.this$0.user.hardPunchReleased();
            }
            if (key == 70) {
                this.this$0.user.lightKickReleased();
            }
            if (key == 71) {
                this.this$0.user.mediumKickReleased();
            }
            if (key == 72) {
                this.this$0.user.hardKickReleased();
            }
            if (key == 76) {
                this.this$0.user2.leftArrowReleased();
            }
            if (key == 222) {
                this.this$0.user2.rightArrowReleased();
            }
            if (key == 80) {
                this.this$0.user2.upArrowReleased();
            }
            if (key == 59) {
                this.this$0.user2.downArrowReleased();
            }
            if (key == 97) {
                this.this$0.user2.lightPunchReleased();
            }
            if (key == 98) {
                this.this$0.user2.mediumPunchReleased();
            }
            if (key == 99) {
                this.this$0.user2.hardPunchReleased();
            }
            if (key == 100) {
                this.this$0.user2.lightKickReleased();
            }
            if (key == 101) {
                this.this$0.user2.mediumKickReleased();
            }
            if (key == 102) {
                this.this$0.user2.hardKickReleased();
            }
        }
    }
}