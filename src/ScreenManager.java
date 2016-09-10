
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

/**
 *
 * @author Yashar Nesabian
 */
public class ScreenManager {

    ArrayList ol;
    FlappyBird fb;
    private int t;
    private int time = 0;
    public static boolean isHit = false;
    private boolean highScore = false;

    public ScreenManager(FlappyBird fb) {
        this.fb = fb;
        ol = new ArrayList();
        t = (int) (2 * Math.random());
    }

    public void addScreenObject(ScreenObject so) {
        ol.add(so);
    }

    public void removeScreenObject(ScreenObject so) {
        ol.remove(so);
    }

    public void draw(Graphics g) {
        ScreenObject score = null;
        ScreenObject bird = null;
        checkConflict();
        Image offscreen = fb.createImage(fb.getWidth(), fb.getHeight());
        Graphics offg = offscreen.getGraphics();
        offg.drawImage(fb.backGround[t], 0, 0, fb.getWidth(), fb.getHeight(), fb);

        for (int i = 0; i < ol.size(); i++) {

            if (ol.get(i) instanceof Score) {
                score = (ScreenObject) ol.get(i);
            } else if (ol.get(i) instanceof Bird) {
                bird = (ScreenObject) ol.get(i);
            } else {
                ScreenObject so = (ScreenObject) ol.get(i);
                so.draw(offg);
            }
        }

        score.draw(offg);
        bird.draw(offg);

        if (FlappyBird.firstScreen) {
            offg.drawImage(fb.flappyBird, 120, 90, 160, 75, fb);
            offg.drawImage(fb.play, 150, 320, 90, 75, fb);
        } else if (FlappyBird.secondScreen) {
            offg.drawImage(fb.getReady, 130, 90, 150, 80, fb);
            offg.drawImage(fb.tap, 130, 200, 150, 150, fb);
        } else if (isHit || Bird.hitByGround) {

            if (fb.score.getScore() > FlappyBird.highScore) {

                FlappyBird.highScore = fb.score.getScore();
                highScore = true;
                Writer wr;
                try {
                    wr = new FileWriter("build/classes/Data/HighScore.txt");
                    wr.write(String.valueOf(fb.score.getScore()));
                    wr.flush();
                    wr.close();
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }

            offg.drawImage(fb.gameOver, 120, 90, 160, 80, fb);
            offg.drawImage(fb.board, 100, 250, 210, 127, fb);
            offg.setFont(new Font("Arial", Font.BOLD, 20));
            offg.drawString(fb.score.getScore() + "", 258, 306);
            offg.drawString(FlappyBird.highScore + "", 258, 340);

            if (fb.score.getScore() >= 30) {
                offg.drawImage(fb.medals[2], 122, 298, fb);
            } else if (fb.score.getScore() >= 20) {
                offg.drawImage(fb.medals[1], 122, 298, fb);
            } else if (fb.score.getScore() >= 10) {
                offg.drawImage(fb.medals[1], 122, 298, fb);
            }
            if (highScore) {
                offg.drawImage(fb.newHighScore, 210, 285, fb);
            }

        }

        g.drawImage(offscreen, 0, 0, fb);
    }

    private void checkConflict() {
        Bird bird = null;
        ArrayList<Stone> StoneL = new ArrayList();
        Score score = null;

        for (int i = 0; i < ol.size(); i++) {
            if (ol.get(i) instanceof Bird) {
                bird = (Bird) ol.get(i);
            } else if (ol.get(i) instanceof Stone) {
                StoneL.add((Stone) ol.get(i));
            } else if (ol.get(i) instanceof Score) {
                score = (Score) ol.get(i);
            }
        }

        for (Stone stone : StoneL) {

            if (!isHit) {
                if (stone.up) {

                    if (!stone.passed) {
                        if (bird.x > stone.x + stone.w) {
                            fb.bading.play();
                            score.addScore();
                            stone.passed = true;
                            break;
                        }
                    }

                    if (((bird.x >= stone.x && bird.x <= stone.x + stone.w) || (bird.x + bird.w >= stone.x && bird.x + bird.w <= stone.x + stone.w))
                            && bird.y < stone.y + stone.h) {
                        fb.smack.play();
                        fb.removeKeyListener(fb);
                        fb.removeMouseListener(fb);
                        isHit = true;
                        break;
                    }
                } else if (((bird.x >= stone.x && bird.x <= stone.x + stone.w) || (bird.x + bird.w >= stone.x && bird.x + bird.w <= stone.x + stone.w))
                        && bird.y + bird.h > stone.y) {
                    fb.smack.play();
                    fb.removeKeyListener(fb);
                    fb.removeMouseListener(fb);
                    isHit = true;
                    break;
                }
            }
        }
    }

}
