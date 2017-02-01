import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Frame
implements ActionListener {
    static ChampSelect selectScreen;
    static StageSelect stageSelect;
    static loadScreen load;
    static gameFrame game;
    static Timer time;
    static MainMenu main;
    static PauseScreen pause;
    static Help help;
    static Controls control;
    static Thread t1;
    static long startTime;
    static boolean verify;
    static boolean mapSelectOpen;
    static boolean charSelectOpen;
    static boolean actionPanelOpen;
    static boolean done;
    static boolean pauseScreenOpen;
    static ImageIcon logo;

    static {
        verify = false;
        mapSelectOpen = false;
        charSelectOpen = false;
        actionPanelOpen = false;
        done = false;
        pauseScreenOpen = false;
        logo = new ImageIcon(Toolkit.getDefaultToolkit().getImage(Frame.class.getClass().getResource("/sflogo.png")));
    }

    public static void main(String[] args) throws IOException {
        new Frame();
    }

    Frame() throws IOException {
        main = new MainMenu();
        main.setIconImage(logo.getImage());
        time = new Timer(5, this);
        time.start();
        startTime = System.currentTimeMillis();
    }

    void start() throws IOException {
        if (!(load.isDisplayable() || Frame.stageSelect.finished || charSelectOpen || stageSelect.isVisible())) {
            stageSelect.setVisible(true);
            stageSelect.playAudio();
            Frame.load.done = false;
        }
        if (Frame.stageSelect.finished && !charSelectOpen) {
            charSelectOpen = true;
            stageSelect.dispose();
            selectScreen.setVisible(true);
        }
        if (charSelectOpen && Frame.selectScreen.start && !actionPanelOpen) {
            this.loadGame();
        }
    }

    void selectMap() {
        Frame1 r = new Frame1();
        Thread t = new Thread((Runnable)r);
        t.start();
        load = new loadScreen(20);
        load.setIconImage(logo.getImage());
    }

    void loadGame() {
        Sound.stopAudio();
        if (!(load.isDisplayable() || Frame.load.done || verify)) {
            verify = true;
            Frame2 r1 = new Frame2();
            t1 = new Thread((Runnable)r1);
            t1.start();
            load = new loadScreen(30);
            load.setIconImage(logo.getImage());
        }
        if (Frame.load.done) {
            actionPanelOpen = true;
            game.setVisible(true);
            Frame.game.actionPanel.startGame();
            try {
                Sound.stopAudio();
                try {
                    Sound.play((String)("/Audio/" + Frame.stageSelect.mapAudioURL[Frame.stageSelect.choice] + ".wav"), (boolean)true);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
            catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            done = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Frame.main.start && !mapSelectOpen) {
            Sound.stopAudio();
            this.selectMap();
            mapSelectOpen = true;
        }
        if (!actionPanelOpen && !done) {
            try {
                this.start();
            }
            catch (Exception var2_2) {
                // empty catch block
            }
        }
        if (actionPanelOpen) {
            if (Frame.game.actionPanel.gameEnd) {
                if (Frame.game.actionPanel.endOption == 1) {
                    game.dispose();
                    verify = false;
                    mapSelectOpen = false;
                    charSelectOpen = false;
                    actionPanelOpen = false;
                    done = false;
                    pauseScreenOpen = false;
                }
            } else {
                if (Frame.game.actionPanel.gamePause && !pauseScreenOpen) {
                    pauseScreenOpen = true;
                    pause = new PauseScreen();
                }
                if (pauseScreenOpen && Frame.pause.chosen) {
                    pauseScreenOpen = false;
                    Frame.game.actionPanel.gamePause = false;
                    if (Frame.pause.option == 1) {
                        Frame.game.actionPanel.resume();
                    } else if (Frame.pause.option == 2) {
                        game.dispose();
                        verify = false;
                        mapSelectOpen = false;
                        charSelectOpen = false;
                        actionPanelOpen = false;
                        done = false;
                    } else if (Frame.pause.option == 3) {
                        Frame.pause.chosen = false;
                        pauseScreenOpen = true;
                        help = new Help();
                    } else if (Frame.pause.option == 4) {
                        Frame.pause.chosen = false;
                        pauseScreenOpen = true;
                        control = new Controls();
                    } else if (Frame.pause.option == 5) {
                        System.exit(0);
                    }
                }
            }
        }
    }
}

//Champ Select
class Frame1
implements Runnable {
    Frame1() {
    }

    @Override
    public void run() {
        Frame.stageSelect = new StageSelect();
        Frame.stageSelect.setIconImage(Frame.logo.getImage());
        try {
            Frame.selectScreen = new ChampSelect();
            Frame.selectScreen.setIconImage(Frame.logo.getImage());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//Stage Select
class Frame2
implements Runnable {
    Frame2() {
    }

    @Override
    public void run() {
        try {
            Frame.game = new gameFrame(Frame.stageSelect.iconWidth, Frame.stageSelect.iconHeight, Frame.selectScreen.p1Choice, Frame.selectScreen.p2Choice, Frame.stageSelect.mapImage[Frame.stageSelect.choice]);
            Frame.game.setIconImage(Frame.logo.getImage());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

