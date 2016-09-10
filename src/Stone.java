
import java.awt.Graphics;

/**
 *
 * @author Yashar Nesabian
 */
public class Stone extends ScreenObject implements Runnable {

    boolean up;
    boolean passed;

    public Stone(int x, int y, int w, int h, ScreenManager sm, boolean up, boolean passed) {
        super(x, y, w, h, sm);
        this.up = up;
        this.passed = passed;
    }

    @Override
    public void draw(Graphics g) {

        if (up) {
            g.drawImage(sm.fb.stone[0], x, y, w, h, sm.fb);
        } else {
            g.drawImage(sm.fb.stone[1], x, y, w, h, sm.fb);
        }
    }

    @Override
    public void run() {
        while (x + w >= 0) {
            x -= (ScreenManager.isHit || Bird.hitByGround) ? 0 : 1;
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
            }
        }
        sm.removeScreenObject(this);

    }

}
