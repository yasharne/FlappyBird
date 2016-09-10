
import java.awt.Graphics;

/**
 *
 * @author Yashar Nesabian
 */
public class Bird extends ScreenObject implements Runnable {

    private final int turn;
    private int i = 0;
    private boolean upToDown = true;
    public static boolean hitByGround = false;

    public Bird(int x, int y, int w, int h, ScreenManager sm) {
        super(x, y, w, h, sm);
        turn = (int) (3 * Math.random());

    }

    @Override
    public void draw(Graphics g) {

        if (FlappyBird.firstScreen) {
            g.drawImage(sm.fb.birdPosition[turn][i % 3], x + 95, y - 50, w, h, sm.fb);
        } else {
            g.drawImage(sm.fb.birdPosition[turn][i % 3], x, y, w, h, sm.fb);
        }
    }

    @Override
    public void run() {
        while (y + h <= sm.fb.getHeight() - 100) {
            if (FlappyBird.mainGameBeginned) {
                y += 15;
            } else {

                if (upToDown) {
                    y += 10;
                    upToDown = false;
                } else {
                    y -= 10;
                    upToDown = true;
                }
            }

            if (!ScreenManager.isHit) {
                i++;
                if (i == 3) {
                    i = 0;
                }
            } else {
                y += 20;
            }

            try {
                Thread.sleep(150);
            } catch (InterruptedException ex) {
            }
        }

        sm.fb.removeKeyListener(sm.fb);
        sm.fb.removeMouseListener(sm.fb);
        hitByGround = true;
        if (hitByGround && !ScreenManager.isHit) {
            sm.fb.smack.play();
        }
       
    }

    public void fly() {
        if (y + 20 >= 0) {
            y -= 28;
        }
    }

}
