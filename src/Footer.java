
import java.awt.Graphics;

/**
 *
 * @author yashar nesabian
 */
public class Footer extends ScreenObject implements Runnable {

    FooterGenerator fg;

    public Footer(int x, int y, int w, int h, ScreenManager sm, FooterGenerator fg) {
        super(x, y, w, h, sm);
        this.fg = fg;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(sm.fb.footerImage, x, y, w, h, sm.fb);
    }

    @Override
    public void run() {
        while (x + w > 0) {
            x -= (ScreenManager.isHit || Bird.hitByGround) ? 0 : 1;
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
            }
        }
        fg.generate();
        sm.removeScreenObject(this);

    }

}
