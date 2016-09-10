
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import javax.swing.JApplet;

/**
 *
 * @author Yashar Nesabian yasharnesabian@yahoo.com
 */
public class FlappyBird extends JApplet implements KeyListener, MouseListener, Runnable {

    Thread refresher;
    Thread birdThread;
    Thread footerRefresher;
    StoneGenerator sg;
    FooterGenerator fg;
    ScreenManager sm;
    Bird bird;
    Footer footer;
    Score score;
    Image backGround[];
    Image birdPosition[][];
    Image stone[];
    Image footerImage;
    Image flappyBird;
    Image play;
    Image tap;
    Image getReady;
    Image gameOver;
    Image board;
    Image medals[];
    Image newHighScore;
    AudioClip flap;
    AudioClip bading;
    AudioClip smack;

    boolean firstTime = true;
    boolean allowToJump = true;
    public static boolean mainGameBeginned = false;
    public static boolean firstScreen = true;
    public static boolean secondScreen = false;
    public static int highScore = 0;

    Writer wr;
    Reader re;

    @Override
    public void init() {
        setSize(400, 600);
        this.addKeyListener(this);
        this.addMouseListener(this);
        setFocusable(true);
        requestFocus();

        refresher = new Thread(this);
        sm = new ScreenManager(this);
        sg = new StoneGenerator(sm);
        score = new Score(getWidth() / 2 - 10, 50, 40, 40, Color.white, sm);
        sm.addScreenObject(score);
        bird = new Bird(80, getHeight() / 2 - 50, 40, 40, sm);
        sm.addScreenObject(bird);
        birdThread = new Thread(bird);
        fg = new FooterGenerator(sm);

        backGround = new Image[2];
        backGround[0] = getImage(getCodeBase(), "Data/Images/BackGround/BackGround1.png");
        backGround[1] = getImage(getCodeBase(), "Data/Images/BackGround/BackGround2.png");

        birdPosition = new Image[3][3];
        birdPosition[0][0] = getImage(getCodeBase(), "Data/Images/Birds/1/Bird1.png");
        birdPosition[0][1] = getImage(getCodeBase(), "Data/Images/Birds/1/Bird2.png");
        birdPosition[0][2] = getImage(getCodeBase(), "Data/Images/Birds/1/Bird3.png");
        //---------------------------------
        birdPosition[1][0] = getImage(getCodeBase(), "Data/Images/Birds/2/Bird1.png");
        birdPosition[1][1] = getImage(getCodeBase(), "Data/Images/Birds/2/Bird2.png");
        birdPosition[1][2] = getImage(getCodeBase(), "Data/Images/Birds/2/Bird3.png");
        //---------------------------------
        birdPosition[2][0] = getImage(getCodeBase(), "Data/Images/Birds/2/Bird1.png");
        birdPosition[2][1] = getImage(getCodeBase(), "Data/Images/Birds/2/Bird2.png");
        birdPosition[2][2] = getImage(getCodeBase(), "Data/Images/Birds/2/Bird3.png");

        stone = new Image[2];
        stone[0] = getImage(getCodeBase(), "Data/Images/Stone/Stone1.png");
        stone[1] = getImage(getCodeBase(), "Data/Images/Stone/Stone2.png");

        medals = new Image[3];
        medals[0] = getImage(getCodeBase(), "Data/Images/Medals/Bronze.png");
        medals[1] = getImage(getCodeBase(), "Data/Images/Medals/Silver.png");
        medals[2] = getImage(getCodeBase(), "Data/Images/Medals/Gold.png");

        footerImage = getImage(getCodeBase(), "Data/Images/Footer.png");
        flappyBird = getImage(getCodeBase(), "Data/Images/FlappyBird.png");
        play = getImage(getCodeBase(), "Data/Images/Play.png");
        tap = getImage(getCodeBase(), "Data/Images/Tap.png");
        getReady = getImage(getCodeBase(), "Data/Images/GetReady.png");
        gameOver = getImage(getCodeBase(), "Data/Images/GameOver.png");
        board = getImage(getCodeBase(), "Data/Images/Board.png");
        newHighScore = getImage(getCodeBase(), "Data/Images/HighScore.png");

        flap = getAudioClip(getCodeBase(), "Data/Sounds/flap.wav");
        bading = getAudioClip(getCodeBase(), "Data/Sounds/bading.wav");
        smack = getAudioClip(getCodeBase(), "Data/Sounds/smack.wav");

        try {

            re = new FileReader("build/classes/Data/HighScore.txt");
            BufferedReader br = new BufferedReader(re);
            String data;
            while ((data = br.readLine()) != null) {
                highScore = Integer.parseInt(data);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void start() {
        refresher.start();
        fg.firstTimeGenerate();
        birdThread.start();

    }

    @Override
    public void paint(Graphics g) {
        sm.draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                if (firstScreen) {
                    firstScreen = false;
                    secondScreen = true;
                } else if (secondScreen) {
                    secondScreen = false;
                    mainGameBeginned = true;
                    bird.fly();
                    bird.fly();
                } else if (mainGameBeginned) {
                    if (firstTime) {
                        sg.start();
                        firstTime = false;
                    }
                    bird.fly();
                }
                flap.play();
                break;
            case KeyEvent.VK_H:
                try {
                    wr = new FileWriter("build/classes/Data/HighScore.txt");
                    wr.write("0");
                    wr.flush();
                    wr.close();
                    highScore = 0;
                } catch (IOException ex) {
                    System.out.println(ex);
                }
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (firstScreen) {
            int x = e.getX();
            int y = e.getY();
            if ((x >= 150 && x <= 240) && (y >= 320 && y <= 395)) {
                firstScreen = false;
                secondScreen = true;
            }
        } else if (secondScreen) {
            secondScreen = false;
            mainGameBeginned = true;
            bird.fly();
            bird.fly();
        } else if (mainGameBeginned) {
            if (firstTime) {
                sg.start();
                firstTime = false;
            }
            bird.fly();
        }
        flap.play();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(4);
            } catch (InterruptedException ex) {
            }
        }
    }

}
