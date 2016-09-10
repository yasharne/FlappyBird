
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author Yashar Nesabian
 */
public abstract class ScreenObject {

    int x;
    int y;
    int w;
    int h;
    Color cl;
    ScreenManager sm;

    public ScreenObject(int x, int y, int w, int h, Color cl, ScreenManager sm) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.cl = cl;
        this.sm = sm;
    }

    public ScreenObject(int x, int y, int w, int h, ScreenManager sm) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.sm = sm;
    }

    public abstract void draw(Graphics g);

}
